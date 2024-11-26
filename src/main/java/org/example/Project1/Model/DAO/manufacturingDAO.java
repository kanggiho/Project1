package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.manufacturingVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class manufacturingDAO {
    Connection con; //전역변수

    public manufacturingDAO() throws Exception {
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

    public manufacturingVO find(String manufacturer_code) throws Exception {
        //3. sql문 준비, 4. sql문 전송
        String sqlForFind = "select * from manufacturing where manufacturer_code = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setString(1, manufacturer_code);

        ResultSet table = psForFind.executeQuery();
        manufacturingVO vo = new manufacturingVO();

        if (table.next()) {
           vo.setManufacturer_code(table.getString("manufacturer_code"));
           vo.setManufacturer_name(table.getString("manufacturer_name"));
           vo.setSorting(table.getString("sorting"));
           vo.setLicense_number(table.getInt("license_number"));
        }
        return vo;
    }

    public void insert(manufacturingVO vo) throws Exception {
        //3. sql문 준비
        String sqlForInsert = "insert into manufacturing values (?, ?, ?, ?)";
        PreparedStatement psForInsert = con.prepareStatement(sqlForInsert);

        psForInsert.setString(1, vo.getManufacturer_code());
        psForInsert.setString(2, vo.getManufacturer_name());
        psForInsert.setString(3, vo.getSorting());
        psForInsert.setInt(4, vo.getLicense_number());

        //4. sql 전송
        psForInsert.executeUpdate();
        System.out.println("Insert Success");

        psForInsert.close();
        con.close();
    }

    public void delete(int manufacturer_code) throws Exception {
        //3. sql문 준비
        String sqlForDelete = "delete from manufacturing where manufacturer_code = ?";
        PreparedStatement psForDelete = con.prepareStatement(sqlForDelete);
        psForDelete.setInt(1, manufacturer_code);

        //4.sql 전송
        psForDelete.executeUpdate();
        System.out.println("Delete Success");

        psForDelete.close();
        con.close();
    }

    public void update(String manufacturer_code, String manufacturer_name) throws Exception {

        String sql = "update manufacturing set manufacturer_name = ? where manufacturer_code = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, manufacturer_name);
        ps.setString(2, manufacturer_code);
        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
