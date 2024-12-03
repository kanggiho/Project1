package org.example.project1._common.Model.DAO;

import org.example.project1.inout.VO.WarehouseInfoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class WarehouseInfoDAO {
    Connection con; //전역변수

    public WarehouseInfoDAO() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public WarehouseInfoVO one(String warehouse_name) throws Exception {

        String sqlForFind = "select * from warehouse_info where warehouse_name = ?";
        PreparedStatement psForFind = con.prepareStatement(sqlForFind);
        psForFind.setString(1, warehouse_name);
        ResultSet table = psForFind.executeQuery();
        WarehouseInfoVO vo = new WarehouseInfoVO();

        if (table.next()) {
           vo.setWarehouse_id(table.getInt("warehouse_id"));
           vo.setWarehouse_name(table.getString("warehouse_name"));
           vo.setWarehouse_location(table.getString("warehouse_location"));
           vo.setWarehouse_temperature(table.getString("warehouse_temperature"));
        }
        return vo;
    }

    public ArrayList<WarehouseInfoVO> getAll() throws Exception {
        ArrayList<WarehouseInfoVO> list = new ArrayList<>();
        String sql = "select * from warehouse_info";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            WarehouseInfoVO vo = new WarehouseInfoVO();
            vo.setWarehouse_id(rs.getInt("warehouse_id"));
            vo.setWarehouse_name(rs.getString("warehouse_name"));
            vo.setWarehouse_location(rs.getString("warehouse_location"));
            vo.setWarehouse_temperature(rs.getString("warehouse_temperature"));
            list.add(vo);
        }

        rs.close();
        ps.close();
        return list;
    }

//    public void insert(WarehouseInfoVO vo) throws Exception {
//        //3. sql문 준비
//        String sqlForInsert = "insert into warehouse_info values (?, ?, ?)";
//        PreparedStatement psForInsert = con.prepareStatement(sqlForInsert);
//
//        psForInsert.setInt(1, vo.getWarehouse_id());
//        psForInsert.setString(2, vo.getWarehouse_location());
//        psForInsert.setString(3, vo.getWarehouse_temperature());
//
//        //4. sql 전송
//        psForInsert.executeUpdate();
//        System.out.println("Insert Success");
//
//        psForInsert.close();
//        con.close();
//    }
//
//    public void delete(int warehouse_id) throws Exception {
//        //3. sql문 준비
//        String sqlForDelete = "delete from warehouse_info where warehouse_id = ?";
//        PreparedStatement psForDelete = con.prepareStatement(sqlForDelete);
//        psForDelete.setInt(1, warehouse_id);
//
//        //4.sql 전송
//        psForDelete.executeUpdate();
//        System.out.println("Delete Success");
//
//        psForDelete.close();
//        con.close();
//    }
//
//    public void update(int warehouse_id, String warehouse_temperature) throws Exception {
//
//        String sql = "update warehouse_info set warehouse_temperature = ? where warehouse_id = ?";
//
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, warehouse_temperature);
//        ps.setInt(2, warehouse_id);
//        ps.executeUpdate();
//
//        ps.close();
//        con.close();
//    }
}
