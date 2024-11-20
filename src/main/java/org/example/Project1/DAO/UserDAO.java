package org.example.Project1.DAO;

import org.example.Project1.VO.UserVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
    Connection con;

    ArrayList<UserVO> vo_list = new ArrayList<>();

    public UserDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/login";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public void update(String id, String tel) throws Exception {

        String sql = "update user set tel = ? where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tel);
        ps.setString(2, id);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(String id) throws Exception {

        String sql = "delete from user where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }

    public void insert(UserVO vo) throws Exception {

        String sql = "insert into user values (?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getId());
        ps.setString(2, vo.getPw());
        ps.setString(3, vo.getName());
        ps.setString(4, vo.getTel());
        ps.executeUpdate();
        ps.close();
    }

    public UserVO one(String id) throws Exception {
        String sql = "select * from user where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        UserVO vo = new UserVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPw(table.getString("pw"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
        }
        return vo;
    }


    public ArrayList<UserVO> list(String id) throws Exception {
        String sql = "select * from user where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        while(true){
            if(table.next()){
                UserVO vo = new UserVO();
                vo.setId(table.getString("id"));
                vo.setPw(table.getString("pw"));
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
        String sql = "select count(id) from user where id = ? and pw = ?";
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
        String sql = "select count(id) from user where id = ?";
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

    public ArrayList<UserVO> getAll() throws Exception {
        String sql = "select * from user";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                UserVO vo = new UserVO();
                vo.setId(table.getString("id"));
                vo.setPw(table.getString("pw"));
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