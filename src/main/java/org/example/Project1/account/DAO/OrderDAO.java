package org.example.project1.account.DAO;

import org.example.project1.account.VO.OrderVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderDAO {
    Connection con;

    ArrayList<OrderVO> vo_list = new ArrayList<>();

    public OrderDAO() throws Exception {
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


    public void updatebyId(String id, OrderVO vo) throws Exception {

        String sql1 = "update orderer set password = ? where id = ?";

        PreparedStatement ps1 = con.prepareStatement(sql1);
        ps1.setString(1, vo.getPassword());
        ps1.setString(2, id);
        ps1.executeUpdate();
        ps1.close();


        String sql2 = "update orderer set name = ? where id = ?";

        PreparedStatement ps2 = con.prepareStatement(sql2);
        ps2.setString(1, vo.getName());
        ps2.setString(2, id);
        ps2.executeUpdate();
        ps2.close();


        String sql3 = "update orderer set tel = ? where id = ?";

        PreparedStatement ps3 = con.prepareStatement(sql3);
        ps3.setString(1, vo.getTel());
        ps3.setString(2, id);
        ps3.executeUpdate();
        ps3.close();


        String sql4 = "update orderer set license = ? where id = ?";

        PreparedStatement ps4 = con.prepareStatement(sql4);
        ps4.setString(1, vo.getLicense());
        ps4.setString(2, id);
        ps4.executeUpdate();
        ps4.close();


        String sql5 = "update orderer set loc = ? where id = ?";

        PreparedStatement ps5 = con.prepareStatement(sql5);
        ps5.setString(1, vo.getLoc());
        ps5.setString(2, id);
        ps5.executeUpdate();
        ps5.close();

    }



    public void delete(String id) throws Exception {

        String sql = "delete from orderer where id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id); //1은 ?번호
        ps.executeUpdate();
        ps.close();
    }

    public void insert(OrderVO vo) throws Exception {

        String sql = "insert into orderer(id,password,name,tel,license,loc,email,grade) values (?,?,?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getId());
        ps.setString(2, vo.getPassword());
        ps.setString(3, vo.getName());
        ps.setString(4, vo.getTel());
        ps.setString(5, vo.getLicense());
        ps.setString(6, vo.getLoc());
        ps.setString(7, vo.getEmail());
        ps.setString(8, vo.getGrade());
        ps.executeUpdate();
        ps.close();
    }

    public OrderVO emailSelect(String email) throws Exception {
        String sql = "select * from orderer where email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet table = ps.executeQuery();
        OrderVO vo = new OrderVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setLicense(table.getString("license"));
            vo.setLoc(table.getString("loc"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
        }
        return vo;
    }

    public OrderVO idemailSelect(String id, String email) throws Exception {
        String sql = "select * from orderer where id = ? and email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, email);
        ResultSet table = ps.executeQuery();
        OrderVO vo = new OrderVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setLicense(table.getString("license"));
            vo.setLoc(table.getString("loc"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
        }
        return vo;
    }

    public OrderVO idSelect(String id) throws Exception {
        String sql = "select * from orderer where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        OrderVO vo = new OrderVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setLicense(table.getString("license"));
            vo.setLoc(table.getString("loc"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
        }
        return vo;
    }

    public OrderVO nameSelect(String name) throws Exception {
        String sql = "select * from orderer where name = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet table = ps.executeQuery();
        OrderVO vo = new OrderVO();
        if (table.next()) {
            vo.setId(table.getString("id"));
            vo.setPassword(table.getString("password"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setLicense(table.getString("license"));
            vo.setLoc(table.getString("loc"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
        }
        return vo;
    }



    public ArrayList<OrderVO> list(String id) throws Exception {
        String sql = "select * from orderer where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet table = ps.executeQuery();
        while(true){
            if(table.next()){
                OrderVO vo = new OrderVO();
                vo.setId(table.getString("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo.setLicense(table.getString("license"));
                vo.setLoc(table.getString("loc"));
                vo.setEmail(table.getString("email"));
                vo.setGrade(table.getString("grade"));
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

    public boolean confirmPw(String password) throws Exception {
        boolean result = false;
        String sql = "select count(password) from orderer where password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, password);
        ResultSet table = ps.executeQuery();
        if(table.next()){
            int count = table.getInt(1);
            if(count == 1){
                result = true;
            }
        }
        return result;
    }

    public boolean confirmPhone(String tel) throws Exception {
        boolean result = false;
        String sql = "select count(tel) from orderer where tel = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tel);
        ResultSet table = ps.executeQuery();
        if(table.next()){
            int count = table.getInt(1);
            if(count == 1){
                result = true;
            }
        }
        return result;
    }

    public boolean confirmRegnum(String license) throws Exception {
        boolean result = false;
        String sql = "select count(license) from orderer where license = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, license);
        ResultSet table = ps.executeQuery();
        if(table.next()){
            int count = table.getInt(1);
            if(count == 1){
                result = true;
            }
        }
        return result;
    }




    public ArrayList<OrderVO> getAll() throws Exception {
        String sql = "select * from orderer";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        if(!vo_list.isEmpty()){
            vo_list.clear();
        }
        while(true){

            if(table.next()){
                OrderVO vo = new OrderVO();
                vo.setId(table.getString("id"));
                vo.setPassword(table.getString("password"));
                vo.setName(table.getString("name"));
                vo.setTel(table.getString("tel"));
                vo.setLicense(table.getString("license"));
                vo.setLoc(table.getString("loc"));
                vo.setEmail(table.getString("email"));
                vo.setGrade(table.getString("grade"));
                vo_list.add(vo);
            }else{
                break;
            }
        }
        return vo_list;
    }
    // jino생성 메서드

    /**
     * 특정 등급의 주문자 정보를 조회합니다.
     * @param grade 조회할 회원 등급
     * @return 주문자 정보 리스트
     */
    public ArrayList<OrderVO> selectByGrade(String grade) throws Exception {
        String sql = "SELECT id, license, name, tel, email, grade FROM orderer WHERE grade = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, grade);
        ResultSet table = ps.executeQuery();
        ArrayList<OrderVO> result = new ArrayList<>();

        while (table.next()) {
            OrderVO vo = new OrderVO();
            vo.setId(table.getString("id"));
            vo.setLicense(table.getString("license"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
            result.add(vo);
        }

        ps.close();
        return result;
    }
    /**
     * 모든 주문자 정보를 조회합니다.
     * @return 모든 주문자 정보 리스트
     */
    public ArrayList<OrderVO> getIdLicenseNameTelEmailGrade() throws Exception {
        String sql = "SELECT id, license, name, tel, email, grade FROM orderer";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet table = ps.executeQuery();
        vo_list.clear();

        while (table.next()) {
            OrderVO vo = new OrderVO();
            vo.setId(table.getString("id"));
            vo.setLicense(table.getString("license"));
            vo.setName(table.getString("name"));
            vo.setTel(table.getString("tel"));
            vo.setEmail(table.getString("email"));
            vo.setGrade(table.getString("grade"));
            vo_list.add(vo);
        }

        ps.close();
        return vo_list;
    }
    /**
     * 주문자의 등급을 업데이트합니다.
     * @param name 주문자 이름
     * @param id 주문자 아이디
     * @return 업데이트된 행의 수
     */
    public void updateGrade(String grade, String name, String id) throws Exception {
        String sql = "UPDATE orderer SET grade = ? WHERE name = ? AND id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, grade);
        ps.setString(2, name);
        ps.setString(3, id);
        ps.executeUpdate();
        ps.close();
    }

}
