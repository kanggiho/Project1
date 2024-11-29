package org.example.project1.inventory.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inventory.VO.ProductVO;
import org.example.project1.inventory.VO.ProductInfoVO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryStatusDAO {
    private final DataSource dataSource;

    // 생성자: HikariCP 데이터 소스를 초기화
    public InventoryStatusDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    /**
     * 재고 현황을 조회하는 메서드
     * @return 재고 현황 정보를 담은 List<ProductInfoVO>
     */
    public List<ProductInfoVO> getInventoryStatus() {
        List<ProductInfoVO> inventoryList = new ArrayList<>();
        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code " +
                "ORDER BY pi.product_code";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ProductInfoVO productInfo = new ProductInfoVO();
                productInfo.setCode(rs.getString("code"));
                productInfo.setProduct_code(rs.getInt("product_code"));
                productInfo.setManufacturer_code(rs.getString("manufacturer_code"));
                productInfo.setWarehouse_id(rs.getInt("warehouse_id"));
                productInfo.setPrice(rs.getInt("price"));
                productInfo.setStock(rs.getInt("stock"));
                productInfo.setStock_date(rs.getString("stock_date"));

                ProductVO product = new ProductVO();
                product.setProduct_code(rs.getInt("product_code"));
                product.setProduct_name(rs.getString("product_name"));

                inventoryList.add(productInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }
}