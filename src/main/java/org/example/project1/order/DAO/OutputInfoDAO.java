package org.example.project1.order.DAO;

import java.sql.*;
import org.example.project1.order.VO.OutputInfoVO;

public class OutputInfoDAO {

    private Connection connection;

    public OutputInfoDAO(Connection connection) {
        this.connection = connection;
    }

    // 트랜잭션을 사용하는 메서드
    public void insertOutputInfo(OutputInfoVO outputInfo) throws SQLException {
        String sql = "INSERT INTO output_info (product_code, warehouse_id, user_id, confirm_num, confirm_id, status, unit_price, release_quantity, release_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
}
