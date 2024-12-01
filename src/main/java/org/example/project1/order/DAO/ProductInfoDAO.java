package org.example.project1.order.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.project1.order.UI.ProductInfoTableModel;
import org.example.project1.order.VO.ProductInfoProductVO;

import javax.swing.*;

public class ProductInfoDAO {

    private Connection connection;

    public ProductInfoDAO() throws SQLException {
        // 데이터베이스 연결 설정
        String url = "jdbc:mysql://localhost:3306/project1"; // 실제 DB URL로 변경
        String user = "root"; // 실제 DB 사용자명으로 변경
        String password = "1234"; // 실제 DB 비밀번호로 변경
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public List<ProductInfoProductVO> getInventoryStatus() throws SQLException {
        List<ProductInfoProductVO> list = new ArrayList<>();
        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code " +
                "ORDER BY pi.product_code";

        try (Statement stmt = connection.createStatement();
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



    // 제품 코드와 창고 ID를 기준으로 제품 정보 가져오기
    public ProductInfoProductVO getProductByProductCodeAndWarehouseId(int productCode) throws SQLException {
        String sql = "select * from product_info where product_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProductInfoProductVO vo = new ProductInfoProductVO();
                    vo.setCode(rs.getString("code"));
                    vo.setProduct_code(rs.getInt("product_code"));
                    vo.setManufacturer_code(rs.getString("manufacturer_code"));
                    vo.setWarehouse_id(rs.getInt("warehouse_id"));
                    vo.setPrice(rs.getInt("price"));
                    vo.setStock(rs.getInt("stock"));
                    vo.setStock_date(rs.getString("stock_date"));
                    return vo;
                }
            }
        }
        return null;
    }

    // 제품의 재고 수량 업데이트
    public void updateProductStock(int productCode, int warehouseId, int newStock) throws SQLException {
        String sql = "UPDATE product_info SET stock = ? WHERE product_code = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productCode);
            pstmt.executeUpdate();
        }
    }

    // 재고 확인 테이블 갱신 메서드
    public void refreshInventoryStatus(JTable stockTable) throws SQLException {
        ProductInfoTableModel model = (ProductInfoTableModel) stockTable.getModel();
        List<ProductInfoProductVO> productList = getInventoryStatus();
        model.setData(productList);
        model.fireTableDataChanged();
    }



    // 트랜잭션 시작
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    // 트랜잭션 커밋
    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    // 트랜잭션 롤백
    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}
