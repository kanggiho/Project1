package org.example.project1.dashboard.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.dashboard.VO.InputProductProductInfoVO;
import org.example.project1.dashboard.VO.ProductOutputInfoVO;
import org.example.project1.dashboard.VO.ProductProductInfoManufacturingInfoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO implements AutoCloseable {
    private Connection conn;

    // 생성자에서 Connection 초기화
    public DashboardDAO() {
        initConnection();
    }

    // HikariCP를 사용하여 Connection 초기화하는 메서드
    private void initConnection() {
        try {
            this.conn = HikariCPDataSource.getInstance().getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리: 필요한 경우 재throw하거나 커스텀 예외로 변환
        }
    }

    // 오늘 입고된 상품 정보를 조회하는 메서드
    public List<InputProductProductInfoVO> getTodayInputProducts() {
        List<InputProductProductInfoVO> todayInputProducts = new ArrayList<>();

        // SQL 쿼리: 오늘 날짜로 입고된 상품의 이름, 수량, 가격을 조회
        String sql = "SELECT p.product_name, i.warehoused_quantity, pi.price " +
                "FROM input i " +
                "JOIN product p ON i.product_code = p.product_code " +
                "JOIN product_info pi ON i.product_code = pi.product_code " +
                "WHERE i.warehoused_date = CURDATE()";

        // PreparedStatement와 ResultSet을 try-with-resources로 관리
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 집합을 순회하며 VO 객체에 데이터 설정
            while (rs.next()) {
                InputProductProductInfoVO vo = new InputProductProductInfoVO();
                vo.setProductName(rs.getString("product_name"));
                vo.setWarehousingQuantity(rs.getInt("warehoused_quantity"));
                vo.setPrice(rs.getInt("price")); // 가격 정보 추가
                todayInputProducts.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리: 필요한 경우 로깅 또는 재throw
        }

        // 조회된 오늘의 입고 상품 리스트 반환
        return todayInputProducts;
    }
    // 인기 품목 Top 5를 조회하는 메서드
    public List<ProductOutputInfoVO> getTopSellingProducts() {
        List<ProductOutputInfoVO> topSellingProducts = new ArrayList<>();

        // SQL 쿼리: 승인된 출고 정보를 기반으로 상위 5개 인기 품목 조회
        String sql = "SELECT p.product_name, SUM(o.release_quantity) as total_quantity " +
                "FROM output_info o " +
                "JOIN product p ON o.product_code = p.product_code " +
                "WHERE o.status = '승인' " +
                "GROUP BY p.product_name " +
                "ORDER BY total_quantity DESC " +
                "LIMIT 5";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 집합을 순회하며 VO 객체에 데이터 설정
            while (rs.next()) {
                ProductOutputInfoVO vo = new ProductOutputInfoVO();
                vo.setProductName(rs.getString("product_name"));
                vo.setReleaseQuantity(rs.getInt("total_quantity"));
                topSellingProducts.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리
        }

        // 조회된 인기 품목 리스트 반환
        return topSellingProducts;
    }

    // 랜덤으로 5개의 상품을 추천하는 메서드
    public List<ProductProductInfoManufacturingInfoVO> getRandomRecommendedProducts() {
        List<ProductProductInfoManufacturingInfoVO> recommendedProducts = new ArrayList<>();

        // SQL 쿼리: 랜덤으로 5개의 상품을 선택하여 상품명, 제조사명, 가격 정보를 조회
        String sql = "SELECT p.product_name, m.manufacturer_name, pi.price " +
                "FROM product p " +
                "JOIN product_info pi ON p.product_code = pi.product_code " +
                "JOIN manufacturing m ON pi.manufacturer_code = m.manufacturer_code " +
                "ORDER BY RAND() " +
                "LIMIT 5";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // 결과 집합을 순회하며 VO 객체에 데이터 설정
            while (rs.next()) {
                ProductProductInfoManufacturingInfoVO vo = new ProductProductInfoManufacturingInfoVO();
                vo.setProductName(rs.getString("product_name"));
                vo.setManufacturerName(rs.getString("manufacturer_name"));
                vo.setPrice(rs.getInt("price"));
                recommendedProducts.add(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 예외 처리
        }

        // 조회된 랜덤 추천 상품 리스트 반환
        return recommendedProducts;
    }

    // Connection을 닫는 메서드
    @Override
    public void close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // 예외 처리
            }
        }
    }
}
