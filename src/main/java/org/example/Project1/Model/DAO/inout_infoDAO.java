package org.example.Project1.Model.DAO;

import org.example.Project1.Model.VO.inout_infoVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class inout_infoDAO {
    Connection con;

    ArrayList<inout_infoVO> vo_list = new ArrayList<>();

    public inout_infoDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }


    public void insert(inout_infoVO vo) throws Exception {

        String sql = "insert into inout_info values (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, vo.getProduct_code());
        ps.setInt(2, vo.getWarehouse_id());
        ps.setInt(3, vo.getUser_id());
        ps.setInt(4, vo.getApproval_number());
        ps.setInt(5, vo.getConfirm_id());
        ps.setInt(6, vo.getUnit_price());
        ps.setInt(7, vo.getCount());
        ps.setInt(8, vo.getIncoming_quantity());
        ps.setInt(9, vo.getRelease_quantity());

        ps.executeUpdate();
        ps.close();
    }

    public void delete(int product_code) throws Exception {

        String sql = "delete from inout_info where product_code = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, product_code); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }



    public inout_infoVO one(int product_code) throws Exception {
        String sql = "select * from inout_info where product_code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, product_code);
        ResultSet table = ps.executeQuery();
        inout_infoVO vo = new inout_infoVO();
        if (table.next()) {
            vo.setProduct_code(table.getInt("approval_number"));
            vo.setWarehouse_id(table.getInt("admin_id"));
            vo.setUser_id(table.getInt("user_id"));
            vo.setApproval_number(table.getInt("approval_number"));
            vo.setConfirm_id(table.getInt("confirm_id"));
            vo.setUnit_price(table.getInt("unit_price"));
            vo.setCount(table.getInt("count"));
            vo.setIncoming_quantity(table.getInt("incoming_quantity"));
            vo.setRelease_quantity(table.getInt("release_quantity"));
        }
        return vo;
    }


    public ArrayList<inout_infoVO> list(int product_code) throws Exception {
        String sql = "select * from inout_info where product_code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, product_code);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){
            if(table.next()){
                inout_infoVO vo = new inout_infoVO();
                vo.setProduct_code(table.getInt("approval_number"));
                vo.setWarehouse_id(table.getInt("admin_id"));
                vo.setUser_id(table.getInt("user_id"));
                vo.setApproval_number(table.getInt("approval_number"));
                vo.setConfirm_id(table.getInt("confirm_id"));
                vo.setUnit_price(table.getInt("unit_price"));
                vo.setCount(table.getInt("count"));
                vo.setIncoming_quantity(table.getInt("incoming_quantity"));
                vo.setRelease_quantity(table.getInt("release_quantity"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }


    public ArrayList<inout_infoVO> getAll() throws Exception {
        String sql = "select * from inout_info";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                inout_infoVO vo = new inout_infoVO();
                vo.setProduct_code(table.getInt("approval_number"));
                vo.setWarehouse_id(table.getInt("admin_id"));
                vo.setUser_id(table.getInt("user_id"));
                vo.setApproval_number(table.getInt("approval_number"));
                vo.setConfirm_id(table.getInt("confirm_id"));
                vo.setUnit_price(table.getInt("unit_price"));
                vo.setCount(table.getInt("count"));
                vo.setIncoming_quantity(table.getInt("incoming_quantity"));
                vo.setRelease_quantity(table.getInt("release_quantity"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }
}
