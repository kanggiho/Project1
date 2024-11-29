package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.InputVO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class InputDAO {
    private final DataSource dataSource;

    public InputDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    ArrayList<InputVO> vo_list = new ArrayList<>();

    public void insertToInput(InputVO vo) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into product values (?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, vo.getInputNum());
            ps.setString(2, vo.getManufacturerCode());
            ps.setInt(3, vo.getProductCode());
            ps.setString(4, vo.getAskingDate());
            ps.setInt(5, vo.getWarehousedQuantity());
            ps.setString(6, vo.getWarehousedDate());

            ps.executeUpdate();
            System.out.println("Insert Success");

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertToInventory(InputVO vo) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into product_info values (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, "code");
            ps.setInt(2, vo.getProductCode());
            ps.setString(3, vo.getManufacturerCode());
            ps.setString(4, "warehouse_id");
            ps.setString(5, "price");
            ps.setInt(6, vo.getWarehousedQuantity());
            ps.setString(7, vo.getWarehousedDate());

            ps.executeUpdate();
            System.out.println("Insert Success");

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public ArrayList<InputVO> list(String warehousedDate) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from input where warehoused_date = ? and product_code = ?")) {
            ps.setString(1, warehousedDate);
            ResultSet table = ps.executeQuery();
            if (!vo_list.isEmpty()) {
                vo_list.clear();
            }
            while (true) {
                if (table.next()) {
                    InputVO vo = new InputVO();
                    vo.setInputNum(table.getInt("input_num"));
                    vo.setManufacturerCode(table.getString("manufacturer_code"));
                    vo.setProductCode(table.getInt("product_code"));
                    vo.setAskingDate(table.getString("asking_date"));
                    vo.setWarehousedQuantity(table.getInt("warehoused_quantity"));
                    vo.setWarehousedDate(table.getString("warehoused_date"));
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


    public ArrayList<InputVO> getAll() {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from input")) {
            ResultSet table = ps.executeQuery();
            if (!vo_list.isEmpty()) {
                vo_list.clear();
            }
            while (true) {

                if (table.next()) {
                    InputVO vo = new InputVO();
                    vo.setInputNum(table.getInt("input_num"));
                    vo.setManufacturerCode(table.getString("manufacturer_code"));
                    vo.setProductCode(table.getInt("product_code"));
                    vo.setAskingDate(table.getString("asking_date"));
                    vo.setWarehousedQuantity(table.getInt("warehoused_quantity"));
                    vo.setWarehousedDate(table.getString("warehoused_date"));
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
}

