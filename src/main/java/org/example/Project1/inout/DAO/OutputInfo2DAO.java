package org.example.project1.inout.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inout.VO.OutputInfoProductWarehouseInfoOrdererVO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutputInfo2DAO {
    private final DataSource dataSource;

    public OutputInfo2DAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    // 기본 조인 SQL
    private String joinSQL = "SELECT \n" +
            "    oi.product_code,\n" +
            "    p.product_name,\n" +
            "    wi.warehouse_name,\n" +
            "    o.id,\n" +
            "    o.name AS orderer_name,\n" +
            "    oi.unit_price,\n" +
            "    oi.release_quantity,\n" +
            "    oi.confirm_num,\n" +
            "    oi.confirm_id,\n" +
            "    oi.release_date,\n" +
            "    oi.status\n" +
            "FROM \n" +
            "    output_info oi\n" +
            "JOIN \n" +
            "    product p ON oi.product_code = p.product_code\n" +
            "JOIN \n" +
            "    warehouse_info wi ON oi.warehouse_id = wi.warehouse_id\n" +
            "JOIN \n" +
            "    orderer o ON oi.user_id = o.uid\n";


    // 모든 데이터 조회 메서드
    public ArrayList<OutputInfoProductWarehouseInfoOrdererVO> getAllData() throws SQLException {
        ArrayList<OutputInfoProductWarehouseInfoOrdererVO> listForAll = new ArrayList<>();
        String sql = joinSQL +
                " ORDER BY CASE status " +
                " WHEN '거절' THEN 1 " +
                " WHEN '승인' THEN 2 " +
                " WHEN '대기중' THEN 3 " +
                " ELSE 4 END, release_date";

        try (Connection con = dataSource.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OutputInfoProductWarehouseInfoOrdererVO vo = new OutputInfoProductWarehouseInfoOrdererVO();
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setProduct_name(rs.getString("product_name"));
                vo.setWarehouse_name(rs.getString("warehouse_name"));
                vo.setId(rs.getString("id"));
                vo.setOrderer_name(rs.getString("orderer_name"));
                vo.setUnit_price(rs.getInt("unit_price"));
                vo.setRelease_quantity(rs.getInt("release_quantity"));
                vo.setConfirm_num(rs.getInt("confirm_num"));
                vo.setRelease_date(rs.getString("release_date"));
                vo.setConfirm_id(rs.getInt("confirm_id"));
                vo.setStatus(rs.getString("status"));
                listForAll.add(vo);
            }
        }
        return listForAll;
    }

    // 조건부 필터링 데이터 조회 메서드 - 승인
    public ArrayList<OutputInfoProductWarehouseInfoOrdererVO> getApprove () {
        ArrayList<OutputInfoProductWarehouseInfoOrdererVO> listForApprove = new ArrayList<>();
        String sqlForApprove = joinSQL + "WHERE status = '승인'";

        try (Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sqlForApprove)) {
        ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OutputInfoProductWarehouseInfoOrdererVO vo = new OutputInfoProductWarehouseInfoOrdererVO();
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setProduct_name(rs.getString("product_name"));
                vo.setWarehouse_name(rs.getString("warehouse_name"));
                vo.setId(rs.getString("id"));
                vo.setOrderer_name(rs.getString("orderer_name"));
                vo.setUnit_price(rs.getInt("unit_price"));
                vo.setRelease_quantity(rs.getInt("release_quantity"));
                vo.setConfirm_num(rs.getInt("confirm_num"));
                vo.setRelease_date(rs.getString("release_date"));
                vo.setConfirm_id(rs.getInt("confirm_id"));
                vo.setStatus(rs.getString("status"));
                listForApprove.add(vo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listForApprove;
    }


    // 조건부 필터링 데이터 조회 메서드 - 거절
    public ArrayList<OutputInfoProductWarehouseInfoOrdererVO> getReject () {
        ArrayList<OutputInfoProductWarehouseInfoOrdererVO> listForReject = new ArrayList<>();
        String sqlForReject = joinSQL + "WHERE status = '거절'";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForReject)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OutputInfoProductWarehouseInfoOrdererVO vo = new OutputInfoProductWarehouseInfoOrdererVO();
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setProduct_name(rs.getString("product_name"));
                vo.setWarehouse_name(rs.getString("warehouse_name"));
                vo.setId(rs.getString("id"));
                vo.setOrderer_name(rs.getString("orderer_name"));
                vo.setUnit_price(rs.getInt("unit_price"));
                vo.setRelease_quantity(rs.getInt("release_quantity"));
                vo.setConfirm_num(rs.getInt("confirm_num"));
                vo.setRelease_date(rs.getString("release_date"));
                vo.setConfirm_id(rs.getInt("confirm_id"));
                vo.setStatus(rs.getString("status"));
                listForReject.add(vo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listForReject;
    }


    // 조건부 필터링 데이터 조회 메서드 - 대기중
    public ArrayList<OutputInfoProductWarehouseInfoOrdererVO> getPending () {
        ArrayList<OutputInfoProductWarehouseInfoOrdererVO> listFoPending = new ArrayList<>();
        String sqlForPending = joinSQL + "WHERE status = '대기중'";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlForPending)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OutputInfoProductWarehouseInfoOrdererVO vo = new OutputInfoProductWarehouseInfoOrdererVO();
                vo.setProduct_code(rs.getInt("product_code"));
                vo.setProduct_name(rs.getString("product_name"));
                vo.setWarehouse_name(rs.getString("warehouse_name"));
                vo.setId(rs.getString("id"));
                vo.setOrderer_name(rs.getString("orderer_name"));
                vo.setUnit_price(rs.getInt("unit_price"));
                vo.setRelease_quantity(rs.getInt("release_quantity"));
                vo.setConfirm_num(rs.getInt("confirm_num"));
                vo.setRelease_date(rs.getString("release_date"));
                vo.setConfirm_id(rs.getInt("confirm_id"));
                vo.setStatus(rs.getString("status"));
                listFoPending.add(vo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFoPending;
    }
}
