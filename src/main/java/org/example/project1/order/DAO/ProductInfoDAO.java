package org.example.project1.order.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.project1.order.UI.ProductInfoTableModel;
import org.example.project1.order.VO.ProductInfoProductVO;

import javax.swing.*;

public class ProductInfoDAO {

    private Connection connection;

    public ProductInfoDAO(Connection connection) {
        this.connection = connection;
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
    public ProductInfoProductVO getProductByProductCodeAndWarehouseId(int productCode, int warehouseId) throws SQLException {
        //String sql = "SELECT * FROM product_info WHERE product_code = ? AND warehouse_id = ?";


        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code "+
                "WHERE pi.product_code = ? AND pi.warehouse_id = ?";



        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productCode);
            pstmt.setInt(2, warehouseId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ProductInfoProductVO vo = new ProductInfoProductVO();
                    vo.setCode(rs.getString("code"));
                    vo.setProduct_code(rs.getInt("product_code"));
                    vo.setProduct_name(rs.getString("product_name"));
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

    // 제품의 재고 수량 업데이트 (warehouse_id 포함)
    public void updateProductStock(int productCode, int warehouseId, int newStock) throws SQLException {
        String sql = "UPDATE product_info SET stock = ? WHERE product_code = ? AND warehouse_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productCode);
            pstmt.setInt(3, warehouseId);
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




}
