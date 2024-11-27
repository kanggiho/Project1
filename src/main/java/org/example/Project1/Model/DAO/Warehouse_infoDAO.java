package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.Warehouse_infoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Warehouse_infoDAO {
    Connection con; //전역변수

    public Warehouse_infoDAO() throws Exception {
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

    public Warehouse_infoVO one(int warehouse_id) throws Exception {
        //3. sql문 준비, 4. sql문 전송
        String sqlForFind = "select * from warehouse_info where warehouse_id = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setInt(1, warehouse_id);

        ResultSet table = psForFind.executeQuery();
        Warehouse_infoVO vo = new Warehouse_infoVO();

        if (table.next()) {
           vo.setWarehouse_id(table.getInt("warehouse_id"));
           vo.setWarehouse_location(table.getString("warehouse_location"));
           vo.setWarehouse_temperature(table.getString("warehouse_temperature"));
        }
        return vo;
    }

    public ArrayList<Warehouse_infoVO> getAll() throws Exception {
        ArrayList<Warehouse_infoVO> list = new ArrayList<>();
        String sql = "select * from warehouse_info";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Warehouse_infoVO vo = new Warehouse_infoVO();
            vo.setWarehouse_id(rs.getInt("warehouse_id"));
            vo.setWarehouse_location(rs.getString("warehouse_location"));
            vo.setWarehouse_temperature(rs.getString("warehouse_temperature"));
            list.add(vo);
        }

        rs.close();
        ps.close();
        return list;
    }

    public void insert(Warehouse_infoVO vo) throws Exception {
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
