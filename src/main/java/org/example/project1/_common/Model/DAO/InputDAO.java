package org.example.project1._common.Model.DAO;

import org.example.project1._common.Model.VO.InputVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class InputDAO {
    Connection con; //전역변수

    ArrayList<InputVO> vo_list = new ArrayList<>();

    public InputDAO() throws Exception {
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

    public InputVO one(String warehousedDate) throws Exception {
        String sql = "select * from Input where warehoused_date = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, warehousedDate);
        ResultSet table = ps.executeQuery();
        InputVO vo = new InputVO();
        if (table.next()) {
            vo.setInputNum(table.getInt("input_num"));
            vo.setManufacturerCode(table.getString("manufacturer_code"));
            vo.setProductCode(table.getInt("product_code"));
            vo.setAskingDate(table.getString("asking_date"));
            vo.setWarehousedQuantity(table.getString("warehoused_quantity"));
            vo.setWarehousedDate(table.getString("warehoused_date"));
        }
        return vo;
    }

    public ArrayList<InputVO> list(String warehousedDate) throws Exception {
        String sql = "select * from input where warehoused_date = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, warehousedDate);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){
            if(table.next()){
                InputVO vo = new InputVO();
                vo.setInputNum(table.getInt("input_num"));
                vo.setManufacturerCode(table.getString("manufacturer_code"));
                vo.setProductCode(table.getInt("product_code"));
                vo.setAskingDate(table.getString("asking_date"));
                vo.setWarehousedQuantity(table.getString("warehoused_quantity"));
                vo.setWarehousedDate(table.getString("warehoused_date"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }


    public ArrayList<InputVO> getAll() throws Exception {
        String sql = "select * from input";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                InputVO vo = new InputVO();
                vo.setInputNum(table.getInt("input_num"));
                vo.setManufacturerCode(table.getString("manufacturer_code"));
                vo.setProductCode(table.getInt("product_code"));
                vo.setAskingDate(table.getString("asking_date"));
                vo.setWarehousedQuantity(table.getString("warehoused_quantity"));
                vo.setWarehousedDate(table.getString("warehoused_date"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }

    public void insert(int inputNum, String manufacturerCode, int productCode, String askingDate,
                       String warehousedQuantity, String warehousedDate) throws Exception {
        //3. sql문 준비
        String sql = "insert into product values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, inputNum);
        ps.setString(2, manufacturerCode);
        ps.setInt(3, productCode);
        ps.setString(4, askingDate);
        ps.setString(5, warehousedQuantity);
        ps.setString(6, warehousedDate);

        //4. sql 전송
        ps.executeUpdate();
        System.out.println("Insert Success");

        ps.close();
        con.close();
    }
}

