package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.warehouse_infoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class warehouse_infoDAO {
    Connection con; //전역변수

    public warehouse_infoDAO() throws Exception {
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

    public warehouse_infoVO find(int warehouse_id) throws Exception {
        //3. sql문 준비, 4. sql문 전송
        String sqlForFind = "select * from warehouse_info where warehouse_id = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setInt(1, warehouse_id);

        ResultSet table = psForFind.executeQuery();
        warehouse_infoVO vo = new warehouse_infoVO();

        if (table.next()) {
           vo.setWarehouse_id(table.getInt("warehouse_id"));
           vo.setWarehouse_location(table.getString("warehouse_location"));
           vo.setWarehouse_temperature(table.getString("warehouse_temperature"));
        }
        return vo;
    }

    public void insert(warehouse_infoVO vo) throws Exception {
        //3. sql문 준비
        String sqlForInsert = "insert into warehouse_info values (?, ?, ?)";
        PreparedStatement psForInsert = con.prepareStatement(sqlForInsert);

        psForInsert.setInt(1, vo.getWarehouse_id());
        psForInsert.setString(2, vo.getWarehouse_location());
        psForInsert.setString(3, vo.getWarehouse_temperature());

        //4. sql 전송
        psForInsert.executeUpdate();
        System.out.println("Insert Success");

        psForInsert.close();
        con.close();
    }

    public void delete(int warehouse_id) throws Exception {
        //3. sql문 준비
        String sqlForDelete = "delete from warehouse_info where warehouse_id = ?";
        PreparedStatement psForDelete = con.prepareStatement(sqlForDelete);
        psForDelete.setInt(1, warehouse_id);

        //4.sql 전송
        psForDelete.executeUpdate();
        System.out.println("Delete Success");

        psForDelete.close();
        con.close();
    }

    public void update(int warehouse_id, String warehouse_temperature) throws Exception {

        String sql = "update warehouse_info set warehouse_temperature = ? where warehouse_id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, warehouse_temperature);
        ps.setInt(2, warehouse_id);
        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
