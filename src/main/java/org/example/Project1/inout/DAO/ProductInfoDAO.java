package org.example.project1.inout.DAO;


import org.example.project1.inout.VO.ProductInfoProductVO;
import org.example.project1.inout.VO.ProductInfoVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoDAO {


    private Connection con;

    public ProductInfoDAO() {
        connection();
    }


    public void connection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/project1";
            String id = "root";
            String pw = "1234";
            con = DriverManager.getConnection(url, id, pw);
        } catch (Exception e) {

        }
    }




    public List<ProductInfoProductVO> getInventoryStatus() throws SQLException {
        List<ProductInfoProductVO> list = new ArrayList<>();
        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code " +
                "ORDER BY pi.product_code";

        try (Statement stmt = con.createStatement();
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



        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productCode);
            pstmt.setInt(3, warehouseId);
            pstmt.executeUpdate();
        }
    }


    // 제품의 재고 수량 업데이트 (warehouse_id 포함)
    public void updateProductStock(int productCode, int newStock) throws SQLException {
        String sql = "UPDATE product_info SET stock = ? WHERE product_code = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productCode);
            pstmt.executeUpdate();
        }
    }


    public ProductInfoVO one(int product_code){
        String sql = "select * from product_info where product_code = ?";
        try{
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,product_code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ProductInfoVO vo = new ProductInfoVO();
                vo.setCode(rs.getString("code"));
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setManufacturer_code(rs.getString("manufacturer_code"));
                vo.setWarehouse_id(rs.getInt("warehouse_id"));
                vo.setPrice(rs.getInt("price"));
                vo.setStock(rs.getInt("stock"));
                vo.setStock_date(rs.getString("stock_date"));
                return vo;
            }


        } catch (SQLException e) {

        }
        return null;
    }


    // ProductInfoVO 객체를 데이터베이스에 삽입하는 메서드
    public void insert(ProductInfoVO vo) throws SQLException {
        String sql = "INSERT INTO product_info(code, product_code, manufacturer_code, warehouse_id, price, stock, stock_date) VALUES (?,?,?,?,?,?,?)";
        try {
            Connection con = this.con;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, vo.getCode());
            ps.setInt(2, vo.getProduct_code());
            ps.setString(3, vo.getManufacturer_code());
            ps.setInt(4, vo.getWarehouse_id());
            ps.setInt(5, vo.getPrice());
            ps.setInt(6, vo.getStock());
            ps.setString(7, vo.getStock_date());
            ps.executeUpdate();
            System.out.println("Insert Success");
        } catch (Exception e) {

        }
    }

}

//     private final DataSource dataSource;

//     // 생성자: HikariCP를 통해 DataSource를 초기화합니다.
//     public ProductInfoDAO() {
//         this.dataSource = HikariCPDataSource.getInstance().getDataSource();
//     }



