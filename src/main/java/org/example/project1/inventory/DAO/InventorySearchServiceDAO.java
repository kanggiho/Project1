package org.example.project1.inventory.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inventory.VO.ProductInfoVO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class InventorySearchServiceDAO {
    private final DataSource dataSource;

    public InventorySearchServiceDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    public List<ProductInfoVO> searchInventory(String productName, Integer warehouseId, String manufacturerName) {
        List<ProductInfoVO> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, m.manufacturer_name, " +
                        "pi.warehouse_id, w.warehouse_location, pi.price, pi.stock, pi.stock_date " +
                        "FROM product_info pi " +
                        "JOIN product p ON pi.product_code = p.product_code " +
                        "JOIN warehouse_info w ON pi.warehouse_id = w.warehouse_id " +
                        "JOIN manufacturing m ON pi.manufacturer_code = m.manufacturer_code " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (productName != null && !productName.isEmpty()) {
            sql.append("AND p.product_name LIKE ? ");
            params.add("%" + productName + "%");
        }

        if (warehouseId != null) {
            sql.append("AND pi.warehouse_id = ? ");
            params.add(warehouseId);
        }

        if (manufacturerName != null && !manufacturerName.isEmpty()) {
            sql.append("AND m.manufacturer_name LIKE ? ");
            params.add("%" + manufacturerName + "%");
        }

        sql.append("ORDER BY pi.product_code, pi.warehouse_id, m.manufacturer_name");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductInfoVO vo = new ProductInfoVO();
                    vo.setCode(rs.getString("code"));
                    vo.setProduct_code(rs.getInt("product_code"));
                    vo.setManufacturer_code(rs.getString("manufacturer_code"));
                    vo.setWarehouse_id(rs.getInt("warehouse_id"));
                    vo.setPrice(rs.getInt("price"));
                    vo.setStock(rs.getInt("stock"));
                    java.sql.Date sqlDate = rs.getDate("stock_date");
                    if (sqlDate != null) {
                        vo.setStock_date(sqlDate.toString());
                    } else {
                        vo.setStock_date(null);
                    }
                    results.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}