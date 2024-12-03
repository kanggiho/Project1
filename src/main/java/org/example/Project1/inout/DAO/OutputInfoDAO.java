package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.OutputAdminVO;
import org.example.project1.inout.VO.OutputOrdererVO;
import org.example.project1.inventory.VO.ProductInfoVO;
import org.example.project1.order.VO.OutputInfoVO;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class OutputInfoDAO {
    private final DataSource dataSource;

    public OutputInfoDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    ArrayList<OutputOrdererVO> vo_listForOrderer = new ArrayList<>();
    ArrayList<OutputAdminVO> vo_listForAdmin = new ArrayList<>();
    ArrayList<OutputInfoVO> vo_listForStatus = new ArrayList<>();

    //주문자명 기준 검색 결과
    public ArrayList<OutputOrdererVO> listForOrderer(int user_id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement
                     ("select oi.user_id, o.license, o.name, o.tel, o.loc, oi.confirm_num, oi.confirm_id, oi.status, oi.release_date " +
                            "from orderer o " +
                            "left join output_info oi " +
                             "on o.uid = oi.user_id " +
                             "where o.uid = ?")) {
            ps.setInt(1, user_id);
            ResultSet table = ps.executeQuery();
            if (!vo_listForOrderer.isEmpty()) {
                vo_listForOrderer.clear();
            }
            while (true) {
                if (table.next()) {
                    OutputOrdererVO vo = new OutputOrdererVO();
                    vo.setUser_id(table.getInt("user_id"));
                    vo.setLicense(table.getString("license"));
                    vo.setName(table.getString("name"));
                    vo.setTel(table.getString("tel"));
                    vo.setConfirm_num(table.getInt("confirm_num"));
                    vo.setConfirm_id(table.getInt("confirm_id"));
                    vo.setStatus(table.getString("status"));
                    vo.setRelease_date(table.getString("release_date"));
                    vo_listForOrderer.add(vo);
                } else {
                    break;
                }
            }
            return vo_listForOrderer;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //결재자 기준 검색 결과
    public ArrayList<OutputAdminVO> listForConfirm(int confirm_id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement
                     ("select oi.confirm_id, oi.confirm_num, a.name, a.tel, oi.user_id, oi.status, oi.release_date " +
                             "from output_info oi " +
                             "left join admin a " +
                             "on oi.confirm_id = a.id " +
                             "where oi.confirm_id = ?")) {
            ps.setInt(1, confirm_id);
            ResultSet table = ps.executeQuery();
            if (!vo_listForAdmin.isEmpty()) {
                vo_listForAdmin.clear();
            }
            while (true) {
                if (table.next()) {
                    OutputAdminVO vo = new OutputAdminVO();
                    vo.setConfirm_id(table.getInt("confirm_id"));
                    vo.setConfirm_num(table.getInt("confirm_num"));
                    vo.setName(table.getString("name"));
                    vo.setTel(table.getString("tel"));
                    vo.setUser_id(table.getInt("user_id"));
                    vo.setStatus(table.getString("status"));
                    vo.setRelease_date(table.getString("release_date"));
                    vo_listForAdmin.add(vo);
                } else {
                    break;
                }
            }
            return vo_listForAdmin;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //승인 여부 기준 검색 결과
    public ArrayList<OutputInfoVO> listForStatus(String status) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement
                     ("select status, confirm_num, product_code, release_quantity, user_id " +
                             "from output_info " +
                             "where status = ?")) {
            ps.setString(1, status);
            ResultSet table = ps.executeQuery();
            if (!vo_listForStatus.isEmpty()) {
                vo_listForStatus.clear();
            }
            while (true) {
                if (table.next()) {
                    OutputInfoVO vo = new OutputInfoVO();
                    vo.setStatus(table.getString("status"));
                    vo.setConfirm_num(table.getInt("confirm_num"));
                    vo.setProduct_code(table.getInt("product_code"));
                    vo.setRelease_quantity(table.getInt("release_quantity"));
                    vo.setUser_id(table.getInt("user_id"));
                    vo_listForStatus.add(vo);
                } else {
                    break;
                }
            }
            return vo_listForStatus;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // 승인 여부 - '승인'으로 업데이트
    public void updateStatusForApprove(OutputInfoVO vo) {
        String updateQuery = "update output_info set status = ?, confirm_id = ? where product_code = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
            updatePs.setString(1, vo.getStatus());
            updatePs.setInt(2, vo.getConfirm_id());
            updatePs.setInt(3, vo.getProduct_code());
            updatePs.executeUpdate();
            System.out.println("승인으로 업데이트 완료");
        } catch (Exception e) {
            System.out.println("승인으로 업데이트 실패" + e.getMessage());
        }
    }

    // 승인에 따른 재고 수정 (재고 -)
    public void updateInventory(ProductInfoVO vo) {
        String selectProductInfoQuery = "SELECT stock FROM product_info WHERE product_code = ?";
        String selectOutputInfoQuery = "SELECT release_quantity FROM output_info WHERE product_code = ?";
        String updateQuery = "UPDATE product_info SET stock = ? WHERE product_code = ?";

        try (Connection con = dataSource.getConnection()) {
            int currentStock = 0;  // 현재 재고 수량
            int releaseQuantity = 0;  // 출고 수량

            // 1. product_info에서 현재 재고 조회
            try (PreparedStatement selectProductStmt = con.prepareStatement(selectProductInfoQuery)) {
                selectProductStmt.setInt(1, vo.getProduct_code());
                ResultSet rs = selectProductStmt.executeQuery();
                if (rs.next()) {
                    currentStock = rs.getInt("stock");
                } else {
                    JOptionPane.showMessageDialog(null, "해당 제품 코드가 product_info 테이블에 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                rs.close();
            }

            // 2. output_info에서 출고 수량 조회
            try (PreparedStatement selectOutputStmt = con.prepareStatement(selectOutputInfoQuery)) {
                selectOutputStmt.setInt(1, vo.getProduct_code());
                ResultSet rs = selectOutputStmt.executeQuery();
                if (rs.next()) {
                    releaseQuantity = rs.getInt("release_quantity");
                } else {
                    JOptionPane.showMessageDialog(null, "해당 제품 코드가 output_info 테이블에 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                rs.close();
            }

            // 3. 재고 업데이트 계산
            int updatedStock = currentStock - releaseQuantity;
            if (updatedStock < 0) {
                JOptionPane.showMessageDialog(null, "재고가 부족합니다. 출고 수량을 확인하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 4. product_info 테이블에 재고 업데이트
            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, updatedStock);
                updateStmt.setInt(2, vo.getProduct_code());

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

    // 승인 여부 - '거절'로 업데이트
    public void updateStatusForReject(OutputInfoVO vo) {
        String updateQuery = "update output_info set status = ?, confirm_id = ? where product_code = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement updatePs = con.prepareStatement(updateQuery)) {
            updatePs.setString(1, vo.getStatus());
            updatePs.setInt(2, vo.getConfirm_id());
            updatePs.setInt(3, vo.getProduct_code());
            updatePs.executeUpdate();
            System.out.println("승인 거절로 업데이트 완료");
        } catch (Exception e) {
            System.out.println("승인 거절로 업데이트 실패" + e.getMessage());

        }
    }
}
