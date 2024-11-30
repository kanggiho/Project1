package org.example.project1.order.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.order.VO.OutputInfoVO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OutputInfoDAO {
    private final DataSource dataSource;

    public OutputInfoDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    ArrayList<OutputInfoVO> vo_list = new ArrayList<>();

    public void insert(OutputInfoVO vo) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into output_info values (?,?,?,?,?,?,?,?,?)")) {
            ps.setInt(1, vo.getProduct_code());
            ps.setInt(2, vo.getWarehouse_id());
            ps.setInt(3, vo.getUser_id());
            ps.setInt(4, vo.getConfirm_num());
            ps.setInt(5, vo.getConfirm_id());
            ps.setString(6, vo.getStatus());
            ps.setInt(7, vo.getUnit_price());
            ps.setInt(8, vo.getRelease_quantity());
            ps.setString(10, vo.getRelease_date());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public OutputInfoVO one(int product_code) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from output_info where product_code = ?")) {
            ps.setInt(1, product_code);
            ResultSet table = ps.executeQuery();
            OutputInfoVO vo = new OutputInfoVO();
            if (table.next()) {
                vo.setProduct_code(table.getInt("product_code"));
                vo.setWarehouse_id(table.getInt("warehouse_id"));
                vo.setUser_id(table.getInt("user_id"));
                vo.setConfirm_num(table.getInt("confirm_num"));
                vo.setConfirm_id(table.getInt("confirm_id"));
                vo.setStatus(table.getString("status"));
                vo.setUnit_price(table.getInt("unit_price"));
                vo.setRelease_quantity(table.getInt("release_quantity"));
                vo.setRelease_date(table.getString("release date"));
            }
            return vo;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public ArrayList<OutputInfoVO> list(int product_code) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from output_info where product_code = ?")) {
            ps.setInt(1, product_code);
            ResultSet table = ps.executeQuery();
            if (!vo_list.isEmpty()) {
                vo_list.clear();
            }
            while (true) {
                if (table.next()) {
                    OutputInfoVO vo = new OutputInfoVO();
                    vo.setProduct_code(table.getInt("product_code"));
                    vo.setWarehouse_id(table.getInt("warehouse_id"));
                    vo.setUser_id(table.getInt("user_id"));
                    vo.setConfirm_num(table.getInt("confirm_num"));
                    vo.setConfirm_id(table.getInt("confirm_id"));
                    vo.setStatus(table.getString("status"));
                    vo.setUnit_price(table.getInt("unit_price"));
                    vo.setRelease_quantity(table.getInt("release_quantity"));
                    vo.setRelease_date(table.getString("release date"));
                    vo_list.add(vo);
                } else {
                    break;
                }
            }
            return vo_list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public ArrayList<OutputInfoVO> getAll() {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from output_info")) {
            ResultSet table = ps.executeQuery();
            if (!vo_list.isEmpty()) {
                vo_list.clear();
            }
            while (true) {

                if (table.next()) {
                    OutputInfoVO vo = new OutputInfoVO();
                    vo.setProduct_code(table.getInt("product_code"));
                    vo.setWarehouse_id(table.getInt("warehouse_id"));
                    vo.setUser_id(table.getInt("user_id"));
                    vo.setConfirm_num(table.getInt("confirm_num"));
                    vo.setConfirm_id(table.getInt("confirm_id"));
                    vo.setStatus(table.getString("status"));
                    vo.setUnit_price(table.getInt("unit_price"));
                    vo.setRelease_quantity(table.getInt("release_quantity"));
                    vo.setRelease_date(table.getString("release date"));
                    vo_list.add(vo);
                } else {
                    break;
                }
            }
            return vo_list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void update(int confirm_num, int release_quantity) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("update confirm set release_quantity = ? where confirm_num = ?")) {
            ps.setInt(1, release_quantity);
            ps.setInt(2, confirm_num);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int product_code) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("delete from output_info where product_code = ?")) {
            ps.setInt(1, product_code); //1은 ?번호
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
