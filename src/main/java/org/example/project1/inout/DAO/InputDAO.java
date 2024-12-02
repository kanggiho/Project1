package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.InputManuVO;
import org.example.project1.inout.VO.InputProductVO;
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
    ArrayList<InputProductVO> vo_listForProduct = new ArrayList<>();
    ArrayList<InputManuVO> vo_listForManu = new ArrayList<>();

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

    //자재코드 기준 검색 결과
    public ArrayList<InputProductVO> listForProductCode(int product_code) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement
                     ("select p.product_code, p.product_name, i.input_num, " +
                     "i.manufacturer_code, i.asking_date, " +
                     "i.warehoused_quantity, i.warehoused_date " +
                     "from product p " +
                     "left join input i " +
                     "on i.product_code = p.product_code " +
                     "where i.product_code = ?")) {
            ps.setInt(1, product_code);
            ResultSet table = ps.executeQuery();
            if (!vo_listForProduct.isEmpty()) {
                vo_listForProduct.clear();
            }
            while (true) {
                if (table.next()) {
                    InputProductVO vo = new InputProductVO();
                    vo.setProduct_code(table.getInt("product_code"));
                    vo.setProduct_name(table.getString("product_name"));
                    vo.setInput_num(table.getInt("input_num"));
                    vo.setManufacturer_code(table.getString("manufacturer_code"));
                    vo.setAsking_date(table.getString("asking_date"));
                    vo.setWarehoused_quantity(table.getInt("warehoused_quantity"));
                    vo.setWarehoused_date(table.getString("warehoused_date"));
                    vo_listForProduct.add(vo);
                } else {
                    break;
                }
            }
            return vo_listForProduct;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //제조업체 코드 기준 검색 결과
    public ArrayList<InputManuVO> listForManu (String manufacturer_code) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement
                     ("select m.manufacturer_code, m.manufacturer_name, m.sorting, i.input_num, i.product_code, " +
                             "i.asking_date, i.warehoused_quantity, i.warehoused_date " +
                             "from manufacturing m " +
                             "left join input i " +
                             "on i.manufacturer_code = m.manufacturer_code " +
                             "where input_num is not null and i.manufacturer_code = ?")) {
            ps.setString(1, manufacturer_code);
            ResultSet table = ps.executeQuery();
            if (!vo_listForManu.isEmpty()) {
                vo_listForManu.clear();
            }
            while (true) {
                if (table.next()) {
                    InputManuVO vo = new InputManuVO();
                    vo.setManufacturer_code(table.getString("manufacturer_code"));
                    vo.setManufacturer_name(table.getString("manufacturer_name"));
                    vo.setSorting(table.getString("sorting"));
                    vo.setInput_num(table.getInt("input_num"));
                    vo.setProduct_code(table.getInt("product_code"));
                    vo.setAsking_date(table.getString("asking_date"));
                    vo.setWarehoused_quantity(table.getInt("warehoused_quantity"));
                    vo.setWarehoused_date(table.getString("warehoused_date"));
                    vo_listForManu.add(vo);
                } else {
                    break;
                }
            }
            return vo_listForManu;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //입고일 기준 검색 결과
    public ArrayList<InputVO> list(String warehousedDate) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("select * from input where warehoused_date = ?")) {
            ps.setString(1, warehousedDate);
            ResultSet table = ps.executeQuery();
            if (!vo_list.isEmpty()) {
                vo_list.clear();
            }
            while (true) {
                if (table.next()) {
                    InputVO vo = new InputVO();
                    vo.setAskingDate(table.getString("asking_date"));
                    vo.setInputNum(table.getInt("input_num"));
                    vo.setManufacturerCode(table.getString("manufacturer_code"));
                    vo.setProductCode(table.getInt("product_code"));
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

