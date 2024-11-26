package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.adminVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class adminDAO {
    Connection con;

    ArrayList<adminVO> vo_list = new ArrayList<>();

    public adminDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public void update(String id, String password) throws Exception {

        String sql = "update admin set password = ? where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, password);
        ps.setString(2, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(String id) throws Exception {

        String sql = "delete from admin where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }

    public void insert(adminVO vo) throws Exception {

        String sql = "insert into admin values (?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vo.getId());
        ps.setString(2, vo.getPassword());
        ps.setString(3, vo.getName());
        ps.setString(4, vo.getTel());
        ps.executeUpdate();
        ps.close();
    }

    public adminVO one(String id) throws Exception {
        String sql = "select * from admin where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        adminVO vo = new adminVO();
        if (table.next()) {
            vo.setId(table.getInt("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
        }
        return vo;
    }


    public ArrayList<adminVO> list(String id) throws Exception {
        String sql = "select * from admin where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){
            if(table.next()){
                adminVO vo = new adminVO();
                vo.setId(table.getInt("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }


    public ArrayList<adminVO> getAll() throws Exception {
        String sql = "select * from admin";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                adminVO vo = new adminVO();
                vo.setId(table.getInt("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }
}
