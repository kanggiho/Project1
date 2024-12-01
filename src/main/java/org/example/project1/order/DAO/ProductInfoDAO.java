package org.example.project1.order.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.project1.order.VO.ProductInfoProductVO;

public class ProductInfoDAO {

    private Connection getConnection() throws SQLException {
        // 데이터베이스 연결 정보 설정
        String url = "jdbc:mysql://localhost:3306/project1"; // 실제 데이터베이스 URL로 변경
        String user = "root"; // 실제 사용자 이름으로 변경
        String password = "1234"; // 실제 비밀번호로 변경
        return DriverManager.getConnection(url, user, password);
    }

    public List<ProductInfoProductVO> getAll() throws SQLException {
        List<ProductInfoProductVO> list = new ArrayList<>();
        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code " +
                "ORDER BY pi.product_code"; // 실제 테이블 이름으로 변경

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ProductInfoProductVO vo = new ProductInfoProductVO();
                vo.setCode(rs.getString("code"));
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setProduct_name(rs.getString("product_name"));
                vo.setManufacturer_code(rs.getString("manufacturer_code"));
                vo.setWarehouse_id(rs.getInt("warehouse_id"));
                vo.setPrice(rs.getInt("price"));
                vo.setStock(rs.getInt("stock"));
                vo.setStock_date(rs.getString("stock_date"));
                list.add(vo);
            }
        }

        return list;
    }

    // 재고 상태를 가져오는 메서드
    public List<ProductInfoProductVO> getInventoryStatus() throws SQLException {
        // 이 메서드는 getAll()과 동일할 수 있지만, 필요에 따라 필터링 로직을 추가할 수 있습니다.
        return getAll();
    }
}
