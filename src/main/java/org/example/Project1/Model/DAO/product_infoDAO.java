package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.product_infoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class product_infoDAO {
    Connection con;

    public product_infoDAO() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver Connected");

        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
        System.out.println("Connected to Database");
    }

    public void insert(product_infoVO vo) throws Exception {
        String sql = "insert into product_info(code, product_code, manufacturer_code, warehouse_id, price, stock, stock_date) values (?,?,?,?,?,?,?)";
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
        ps.close();
    }

    public void update(product_infoVO vo) throws Exception {
        String sql = "update product_info set manufacturer_code=?, warehouse_id=?, price=?, stock=?, stock_date=? where code=? and product_code=?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, vo.getManufacturer_code());
        ps.setInt(2, vo.getWarehouse_id());
        ps.setInt(3, vo.getPrice());
        ps.setInt(4, vo.getStock());
        ps.setString(5, vo.getStock_date());
        ps.setString(6, vo.getCode());
        ps.setInt(7, vo.getProduct_code());

        ps.executeUpdate();
        System.out.println("Update Success");
        ps.close();
    }

    public void delete(String code, int product_code) throws Exception {
        String sql = "delete from product_info where code = ? and product_code = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, code);
        ps.setInt(2, product_code);

        ps.executeUpdate();
        System.out.println("Delete Success");
        ps.close();
    }

    public product_infoVO find(String code, int product_code) throws Exception {
        String sql = "select * from product_info where code = ? and product_code = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, code);
        ps.setInt(2, product_code);

        ResultSet rs = ps.executeQuery();
        product_infoVO vo = new product_infoVO();

        if (rs.next()) {
            vo.setCode(rs.getString("code"));
            vo.setProduct_code(rs.getInt("product_code"));
            vo.setManufacturer_code(rs.getString("manufacturer_code"));
            vo.setWarehouse_id(rs.getInt("warehouse_id"));
            vo.setPrice(rs.getInt("price"));
            vo.setStock(rs.getInt("stock"));
            vo.setStock_date(rs.getString("stock_date"));
        }

        rs.close();
        ps.close();
        return vo;
    }

    public ArrayList<product_infoVO> getAll() throws Exception {
        ArrayList<product_infoVO> list = new ArrayList<>();
        String sql = "select * from product_info";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            product_infoVO vo = new product_infoVO();
            vo.setCode(rs.getString("code"));
            vo.setProduct_code(rs.getInt("product_code"));
            vo.setManufacturer_code(rs.getString("manufacturer_code"));
            vo.setWarehouse_id(rs.getInt("warehouse_id"));
            vo.setPrice(rs.getInt("price"));
            vo.setStock(rs.getInt("stock"));
            vo.setStock_date(rs.getString("stock_date"));
            list.add(vo);
        }

        rs.close();
        ps.close();
        return list;
    }
}