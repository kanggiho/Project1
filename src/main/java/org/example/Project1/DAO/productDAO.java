package org.example.Project1.DAO;

import org.example.Project1.VO.productVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class productDAO {
    Connection con; //전역변수


    public productDAO() throws Exception {
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

    public void find(int product_code) throws Exception {
        //3. sql문 준비, 4. sql문 전송
        String sqlForFind = "select product_code, product_name from product where product_code = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setInt(1, product_code);

        ResultSet rs = psForFind.executeQuery();
        if (rs.next()) {
            System.out.println("<<Product Info>>");
            System.out.println("product code>> " + rs.getString("product_code"));
            System.out.println("product name>> " + rs.getString("product_name"));
        } else {
            System.out.println("상품 정보가 없습니다.");
        }

        psForFind.close();
        con.close();
    }

    public void insert(productVO vo) throws Exception {
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
