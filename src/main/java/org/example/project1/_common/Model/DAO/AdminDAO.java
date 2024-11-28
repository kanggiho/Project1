package org.example.project1._common.Model.DAO;

import org.example.project1._common.Model.VO.AdminVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminDAO {
    Connection con;

    ArrayList<AdminVO> vo_list = new ArrayList<>();

    public AdminDAO() throws Exception {
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

    public void insert(AdminVO vo) throws Exception {

        String sql = "insert into admin values (?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vo.getId());
        ps.setString(2, vo.getPassword());
        ps.setString(3, vo.getName());
        ps.setString(4, vo.getTel());
        ps.executeUpdate();
        ps.close();
    }

    public AdminVO one(String id) throws Exception {
        String sql = "select * from admin where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        AdminVO vo = new AdminVO();
        if (table.next()) {
            vo.setId(table.getInt("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
        }
        return vo;
    }


    public ArrayList<AdminVO> list(String id) throws Exception {
        String sql = "select * from admin where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){
            if(table.next()){
                AdminVO vo = new AdminVO();
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


    public ArrayList<AdminVO> getAll() throws Exception {
        String sql = "select * from admin";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                AdminVO vo = new AdminVO();
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


    public boolean isValid(String id, String pw) throws Exception {
        boolean result = false;
        String sql = "select count(id) from admin where id = ? and password = ?";
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

    public AdminVO idSelect(String id) throws Exception {
        String sql = "select * from admin where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        AdminVO vo = new AdminVO();
        if (table.next()) {
            vo.setId(table.getInt("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
        }
        return vo;
    }



}
