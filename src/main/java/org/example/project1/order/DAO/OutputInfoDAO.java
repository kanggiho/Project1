package org.example.project1.order.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.project1.order.VO.OutputInfoProductWarehouseInfoOrdererVO;
import org.example.project1.order.VO.OutputInfoVO;

public class OutputInfoDAO {

    private Connection conn;

    public OutputInfoDAO(Connection connection) {
        this.conn = connection;
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

    // 데이터 삽입 메서드
    public void insertOutputInfo(OutputInfoVO outputInfo) throws SQLException {
        String sql = "INSERT INTO output_info (product_code, warehouse_id, user_id, confirm_num, confirm_id, status, unit_price, release_quantity, release_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, outputInfo.getProduct_code());
            pstmt.setInt(2, outputInfo.getWarehouse_id());
            pstmt.setInt(3, outputInfo.getUser_id());
            pstmt.setInt(4, outputInfo.getConfirm_num());
            pstmt.setInt(5, outputInfo.getConfirm_id());
            pstmt.setString(6, outputInfo.getStatus());
            pstmt.setInt(7, outputInfo.getUnit_price());
            pstmt.setInt(8, outputInfo.getRelease_quantity());
            pstmt.setDate(9, Date.valueOf(outputInfo.getRelease_date()));
            pstmt.executeUpdate();
        }
    }

    // 모든 데이터 조회 메서드
    public List<OutputInfoProductWarehouseInfoOrdererVO> getAllOutputInfoProductWareHouseInfoOrderer() throws SQLException {
        List<OutputInfoProductWarehouseInfoOrdererVO> list = new ArrayList<>();
        String sql = joinSQL +
                " ORDER BY CASE status " +
                " WHEN '거절' THEN 1 " +
                " WHEN '승인' THEN 2 " +
                " WHEN '대기중' THEN 3 " +
                " ELSE 4 END, release_date";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
                list.add(vo);
            }
        }
        return list;
    }

    // 조건부 필터링 데이터 조회 메서드
    public List<OutputInfoProductWarehouseInfoOrdererVO> getFilteredOutputInfo(String statusFilter, String userFilter, String userName) throws SQLException {
        List<OutputInfoProductWarehouseInfoOrdererVO> list = new ArrayList<>();
        StringBuilder query = new StringBuilder(joinSQL);
        query.append(" WHERE 1=1"); // 기본 조건 추가

        // 필터 조건 추가
        if (statusFilter != null && !statusFilter.isEmpty()) {
            query.append(" AND oi.status = ?");
        }
        if ("user".equals(userFilter)) {
            query.append(" AND o.name = ?");
        }

        // 정렬 조건 추가
        query.append(" ORDER BY CASE status " +
                "WHEN '거절' THEN 1 " +
                "WHEN '승인' THEN 2 " +
                "WHEN '대기중' THEN 3 " +
                "ELSE 4 END, release_date");

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (statusFilter != null && !statusFilter.isEmpty()) {
                pstmt.setString(paramIndex++, statusFilter);
            }
            if ("user".equals(userFilter)) {
                pstmt.setString(paramIndex++, userName);
            }

            int iter = 0;
            ResultSet rs = pstmt.executeQuery();
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
                list.add(vo);
                iter++;
            }
        }
        return list;
    }

    // 특정 데이터 삭제
    public boolean deleteOutputInfo(int confirmNum) throws SQLException {
        String query = "DELETE FROM output_info WHERE confirm_num = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, confirmNum);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // '대기중' 상태 데이터 모두 삭제
    public boolean deleteAllPendingOutputInfo() throws SQLException {
        String query = "DELETE FROM output_info WHERE status = '대기중'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }


}
