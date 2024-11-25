package org.example.Project1.DAO;

import org.example.Project1.VO.confirmVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class confirmDAO {
    Connection con;

    ArrayList<confirmVO> vo_list = new ArrayList<>();

    public confirmDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public void update(int approval_number, int admin_id) throws Exception {

        String sql = "update confirm set admin_id = ? where approval_number = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, admin_id);
        ps.setInt(2, approval_number);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(int approval_number) throws Exception {

        String sql = "delete from confirm where approval_number = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, approval_number); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }

    public void insert(confirmVO vo) throws Exception {

        String sql = "insert into confirm values (?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vo.getApproval_number());
        ps.setInt(2, vo.getAdmin_id());

        ps.executeUpdate();
        ps.close();
    }

    public confirmVO one(int approval_number) throws Exception {
        String sql = "select * from confirm where approval_number = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, approval_number);
        ResultSet table = ps.executeQuery();
        confirmVO vo = new confirmVO();
        if (table.next()) {
            vo.setApproval_number(table.getInt("approval_number"));
            vo.setAdmin_id(table.getInt("admin_id"));
        }
        return vo;
    }


    public ArrayList<confirmVO> list(int approval_number) throws Exception {
        String sql = "select * from confirm where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, approval_number);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){
            if(table.next()){
                confirmVO vo = new confirmVO();
                vo.setApproval_number(table.getInt("approval_number"));
                vo.setAdmin_id(table.getInt("admin_id"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }


    public ArrayList<confirmVO> getAll() throws Exception {
        String sql = "select * from confirm";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                confirmVO vo = new confirmVO();
                vo.setApproval_number(table.getInt("approval_number"));
                vo.setAdmin_id(table.getInt("admin_id"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }

}
