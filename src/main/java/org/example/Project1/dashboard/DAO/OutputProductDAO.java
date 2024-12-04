package org.example.project1.dashboard.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.dashboard.VO.OutputProductVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutputProductDAO {
    // output_info와 product를 조인하여 데이터를 조회하는 메서드
    public List<OutputProductVO> getOutputProductInfo() {
        List<OutputProductVO> outputProductList = new ArrayList<>();

        String sql = "SELECT o.product_code, o.release_quantity, o.status, " +
                "p.product_name " +
                "FROM output_info o " +
                "JOIN product p ON o.product_code = p.product_code" +
                "WHERE o.user_id = 5001";

        try (Connection conn = HikariCPDataSource.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                OutputProductVO vo = new OutputProductVO();
                vo.setProductCode(rs.getString("product_code"));
                vo.setReleaseQuantity(rs.getInt("release_quantity"));
                vo.setStatus(rs.getString("status"));
                vo.setProductName(rs.getString("product_name"));
                outputProductList.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return outputProductList;
    }

    // product_code가 같은 데이터끼리 release_quantity를 합산하고 정렬하여 반환하는 메서드
// 특정 user_id에 대한 결과만 반환
    public List<OutputProductVO> getAggregatedOutputProductsByUserId(int userId) {
        List<OutputProductVO> aggregatedList = new ArrayList<>();

        String sql = "SELECT p.product_name, SUM(o.release_quantity) AS total_quantity " +
                "FROM output_info o " +
                "JOIN product p ON o.product_code = p.product_code " +
                "WHERE o.user_id = ? and o.status = ?" + // user_id 조건 추가
                "GROUP BY p.product_name " +
                "ORDER BY total_quantity DESC";

        try (Connection conn = HikariCPDataSource.getInstance().getDataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // user_id 값을 설정
            pstmt.setInt(1, userId);
            pstmt.setString(2,"승인");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    OutputProductVO vo = new OutputProductVO();
                    vo.setProductName(rs.getString("product_name"));
                    vo.setReleaseQuantity(rs.getInt("total_quantity"));
                    aggregatedList.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aggregatedList;
    }


}
