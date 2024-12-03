package org.example.project1._common.Model.DAO;

import org.example.project1._common.Model.VO.ItemClassVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ItemClassDAO {
    Connection con;
    ArrayList<ItemClassVO> vo_list = new ArrayList<>();

    public ItemClassDAO() throws Exception {
        connection();
    }

    public void connection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/project1";
        String id = "root";
        String pw = "1234";
        con = DriverManager.getConnection(url, id, pw);
    }

    public void update(String code, String item_classification) throws Exception {
        String sql = "update item_classification set item_classification = ? where code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, item_classification);
        ps.setString(2, code);
        ps.executeUpdate();
        ps.close();
    }

    public void delete(String code) throws Exception {
        String sql = "delete from item_classification where code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, code);
        ps.executeUpdate();
        ps.close();
    }

    public void insert(ItemClassVO vo) throws Exception {
        String sql = "insert into item_classification(code, item_classification) values (?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getCode());
        ps.setString(2, vo.getItem_classification());
        ps.executeUpdate();
        ps.close();
    }

    public ItemClassVO one(String ic) throws Exception {
        String sql = "select * from item_classification where item_classification = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, ic);
        ResultSet table = ps.executeQuery();
        ItemClassVO vo = new ItemClassVO();
        if (table.next()) {
            vo.setCode(table.getString("code"));
            vo.setItem_classification(table.getString("item_classification"));
        }
        return vo;
    }

    public ArrayList<ItemClassVO> list(String code) throws Exception {
        String sql = "select * from item_classification where code = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, code);
        ResultSet table = ps.executeQuery();
        while(table.next()) {
            ItemClassVO vo = new ItemClassVO();
            vo.setCode(table.getString("code"));
            vo.setItem_classification(table.getString("item_classification"));
            vo_list.add(vo);
        }
        return vo_list;
    }

    public ArrayList<ItemClassVO> getAll() throws Exception {
        String sql = "select * from item_classification";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()) {
            vo_list.clear();
        }
        while(table.next()) {
            ItemClassVO vo = new ItemClassVO();
            vo.setCode(table.getString("code"));
            vo.setItem_classification(table.getString("item_classification"));
            vo_list.add(vo);
        }
        return vo_list;
    }
}