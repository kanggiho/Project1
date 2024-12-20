package org.example.project1.inout.DAO;

import org.example.project1.inout.VO.ProductVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductDAO {
    Connection con; //전역변수


    public ProductDAO() throws Exception {
        //1. 드라이버 설정
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver Connected");

        //2. db연결
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
        System.out.println("Connected to Database");
    }

    public ProductVO one(int product_code) throws Exception {
        //3. sql문 준비, 4. sql문 전송
        String sqlForFind = "select product_code, product_name from product where product_code = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setInt(1, product_code);

        ResultSet table = psForFind.executeQuery();
        ProductVO vo = new ProductVO();

        if (table.next()) {
           vo.setProduct_code(table.getInt("product_code"));
           vo.setProduct_name(table.getString("product_name"));
        }
        return vo;
    }

    public ArrayList<ProductVO> getAll() throws Exception {
        ArrayList<ProductVO> list = new ArrayList<>();
        String sql = "select * from product";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ProductVO vo = new ProductVO();
            vo.setProduct_code(rs.getInt("product_code"));
            vo.setProduct_name(rs.getString("product_name"));
            list.add(vo);
        }

        rs.close();
        ps.close();
        return list;
    }

    public void insert(ProductVO vo) throws Exception {
        //3. sql문 준비
        String sqlForInsert = "insert into product values (?, ?)";
        PreparedStatement pstmForInsert = con.prepareStatement(sqlForInsert);

        pstmForInsert.setInt(1, vo.getProduct_code());
        pstmForInsert.setString(2, vo.getProduct_name());

        //4. sql 전송
        pstmForInsert.executeUpdate();
        System.out.println("Insert Success");

        pstmForInsert.close();
        con.close();
    }

    public void delete(int product_code) throws Exception {
        //3. sql문 준비
        String sqlForDelete = "delete from product where product_code = ?";
        PreparedStatement pstmForDelete = con.prepareStatement(sqlForDelete);
        pstmForDelete.setInt(1, product_code);

        //4.sql 전송
        pstmForDelete.executeUpdate();
        System.out.println("Delete Success");

        pstmForDelete.close();
        con.close();
    }


}
