package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.OutputAdminVO;
import org.example.project1.inout.VO.OutputOrdererVO;
import org.example.project1.order.VO.OutputInfoVO;

import javax.sql.DataSource;
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
                     ("select oi.user_id, o.license, o.name, o.tel, o.loc, oi.confirm_num, oi.confirm_id, oi.status, oi.release_date" +
                            "from orderer o" +
                            "left join output_info oi" +
                             "on o.uid = oi.user_id" +
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
                     ("select oi.confirm_id, oi.confirm_num, a.name, a.tel, oi.user_id, oi.status, oi.release_date\n" +
                             "from output_info oi\n" +
                             "left join admin a\n" +
                             "on oi.confirm_id = a.id;\n" +
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
                     ("select status, confirm_num, product_code, user_id, release_date")) {
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
                    vo.setUser_id(table.getInt("user_id"));
                    vo.setRelease_date(table.getDate("release_date").toLocalDate());
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
}
