package org.example.project1.order.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.project1.order.VO.OutputInfoVO;

public class OutputInfoDAO {

    private Connection getConnection() throws SQLException {
        // 데이터베이스 연결 정보 설정
        String url = "jdbc:mysql://localhost:3306/project1"; // 실제 데이터베이스 URL로 변경
        String user = "root"; // 실제 사용자 이름으로 변경
        String password = "1234"; // 실제 비밀번호로 변경
        return DriverManager.getConnection(url, user, password);
    }

    public List<OutputInfoVO> getAll() throws SQLException {
        List<OutputInfoVO> list = new ArrayList<>();
        String sql = "SELECT * FROM output_info"; // 실제 테이블 이름으로 변경

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OutputInfoVO vo = new OutputInfoVO();
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setWarehouse_id(rs.getInt("warehouse_id"));
                vo.setUser_id(rs.getInt("user_id"));
                vo.setConfirm_num(rs.getInt("confirm_num"));
                vo.setConfirm_id(rs.getInt("confirm_id"));
                vo.setStatus(rs.getString("status"));
                vo.setUnit_price(rs.getInt("unit_price"));
                vo.setRelease_quantity(rs.getInt("release_quantity"));
                vo.setRelease_date(rs.getString("release_date"));
                list.add(vo);
            }
        }

        return list;
    }

    // 특정 출고 요청 삭제 메서드 (confirm_num 기준)
    public void delete(int confirmNum) throws SQLException {
        String sql = "DELETE FROM output_info WHERE confirm_num = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, confirmNum);
            pstmt.executeUpdate();
        }
    }

    // 모든 출고 요청 삭제 메서드
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM output_info";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // 제품 출고 메서드 (예시: 출고량 업데이트)
    public void releaseProduct(int productCode, int quantity) throws SQLException {
        String sql = "UPDATE product_info SET stock = stock - ? WHERE product_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setInt(2, productCode);
            pstmt.executeUpdate();
        }

        // 출고 요청 추가 로직 (필요시 추가)
        // 예를 들어, output_info 테이블에 새로운 출고 요청을 추가할 수 있습니다.
    }
}
