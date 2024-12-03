package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.InputManuVO;
import org.example.project1.inout.VO.InputProductVO;
import org.example.project1.inout.VO.InputVO;
import org.example.project1.inout.VO.ProductInfoVO;

import javax.sql.DataSource;
import javax.swing.*;
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


    // 입고 신청 내역에 추가
    public void insertToInput(InputVO vo) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into input " +
                     "(manufacturer_code, product_code, asking_date, warehoused_quantity, warehoused_date) values " +
                     "(?, ?, ?, ?, ?)")) {
            ps.setString(1, vo.getManufacturerCode());
            ps.setInt(2, vo.getProductCode());
            ps.setString(3, vo.getAskingDate());
            ps.setInt(4, vo.getWarehousedQuantity());
            ps.setString(5, vo.getWarehousedDate());

            ps.executeUpdate();
            System.out.println("Insert Success");

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // 재고 내역에 물품 있는지 확인
    public boolean checkInventory(ProductInfoVO vo) {
        String query = "SELECT COUNT(*) FROM product_info WHERE product_code = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, vo.getProduct_code());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); // count(*) 값 가져오기
                return count > 0; // 결과가 1개 이상이면 true 반환
            } System.out.println("재고가 존재합니다.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "재고 확인 중 데이터베이스 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "재고 확인 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        System.out.println("재고가 존재하지 않습니다.");
        return false; // 기본적으로 재고 없음으로 처리
    }


    // 재고 내역에 물품 추가 (기존 재고에 물품 없는 경우)
    public void insertToInventory(ProductInfoVO vo) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("insert into product_info values (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, vo.getCode());
            ps.setInt(2, vo.getProduct_code());
            ps.setString(3, vo.getManufacturer_code());
            ps.setInt(4, vo.getWarehouse_id());
            ps.setInt(5, vo.getPrice());
            ps.setInt(6, vo.getStock());
            ps.setDate(7, Date.valueOf(vo.getStock_date()));

            ps.executeUpdate();
            System.out.println("Insert Success");

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // 재고 업데이트 (기존 재고에 물품이 있는 경우)
    public void updateInventory(ProductInfoVO vo) {
        String selectQuery = "SELECT stock FROM product_info WHERE product_code = ?";
        String updateQuery = "UPDATE product_info SET stock = ?, stock_date = ? WHERE product_code = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {

            // 1. 현재 재고 조회
            selectStmt.setInt(1, vo.getProduct_code());
            ResultSet rs = selectStmt.executeQuery();
            int currentStock = 0;

            if (rs.next()) {
                currentStock = rs.getInt("stock");
            } else {
                JOptionPane.showMessageDialog(null, "해당 제품 코드가 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                return; // 제품 코드가 없으면 업데이트 진행하지 않음
            }

            rs.close();

            // 2. 재고 업데이트
            int updatedStock = currentStock + vo.getStock();
            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, updatedStock);
                updateStmt.setString(2, vo.getStock_date());
                updateStmt.setInt(3, vo.getProduct_code());

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("재고 업데이트 완료");
                    JOptionPane.showMessageDialog(null, "재고가 성공적으로 업데이트되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "재고 업데이트에 실패했습니다. 확인 후 다시 시도해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "예기치 못한 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
    public ArrayList<InputManuVO> listForManu(String manufacturer_code) {
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

