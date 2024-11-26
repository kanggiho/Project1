package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.OrdererVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrdererDAO {
    Connection con;

    ArrayList<OrdererVO> vo_list = new ArrayList<>();

    public OrdererDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public void update(String id, String tel) throws Exception {

        String sql = "update orderer set tel = ? where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tel);
        ps.setString(2, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(String id) throws Exception {

        String sql = "delete from orderer where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }

    public void insert(OrdererVO vo) throws Exception {

        String sql = "insert into orderer(id,password,name,tel,license,loc) values (?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getId());
        ps.setString(2, vo.getPassword());
        ps.setString(3, vo.getName());
        ps.setString(4, vo.getTel());
        ps.setString(5, vo.getLicense());
        ps.setString(6, vo.getLoc());
        ps.executeUpdate();
        ps.close();
    }

    public OrdererVO one(String id) throws Exception {
        String sql = "select * from orderer where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        OrdererVO vo = new OrdererVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setTel(table.getString("license"));
            vo.setTel(table.getString("loc"));
        }
        return vo;
    }


    public ArrayList<OrdererVO> list(String id) throws Exception {
        String sql = "select * from orderer where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        while(true){
            if(table.next()){
                OrdererVO vo = new OrdererVO();
                vo.setId(table.getString("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo.setLicense(table.getString("license"));
                vo.setLoc(table.getString("loc"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }

    public boolean isValid(String id, String pw) throws Exception {
        boolean result = false;
        String sql = "select count(id) from orderer where id = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, pw);
        ResultSet table = ps.executeQuery();
        if(table.next()){
            int count = table.getInt(1);
            if(count == 1){
                result = true;
            }
        }
        return result;
    }

    public boolean confirmID(String id) throws Exception {
        boolean result = false;
        String sql = "select count(id) from orderer where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        if(table.next()){
            int count = table.getInt(1);
            if(count == 1){
                result = true;
            }
        }
        return result;
    }

    public ArrayList<OrdererVO> getAll() throws Exception {
        String sql = "select * from orderer";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                OrdererVO vo = new OrdererVO();
                vo.setId(table.getString("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo.setLicense(table.getString("license"));
                vo.setLoc(table.getString("loc"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }
}
