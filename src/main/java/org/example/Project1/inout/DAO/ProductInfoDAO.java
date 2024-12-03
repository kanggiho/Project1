package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.ProductInfoVO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoDAO {
    private final DataSource dataSource;

    // 생성자: HikariCP를 통해 DataSource를 초기화합니다.
    public ProductInfoDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    // ProductInfoVO 객체를 데이터베이스에 삽입하는 메서드
    public void insert(ProductInfoVO vo) throws SQLException {
        String sql = "INSERT INTO product_info(code, product_code, manufacturer_code, warehouse_id, price, stock, stock_date) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vo.getCode());
            ps.setInt(2, vo.getProduct_code());
            ps.setString(3, vo.getManufacturer_code());
            ps.setInt(4, vo.getWarehouse_id());
            ps.setInt(5, vo.getPrice());
            ps.setInt(6, vo.getStock());
            ps.setString(7, vo.getStock_date());
            ps.executeUpdate();
            System.out.println("Insert Success");
        }
    }
}