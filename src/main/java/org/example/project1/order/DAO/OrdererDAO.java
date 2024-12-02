package org.example.project1.order.DAO;

import java.sql.*;

public class OrdererDAO {

    private Connection connection;

    public OrdererDAO(Connection connection) {
        this.connection = connection;
    }

    // 사용자명으로 사용자 ID 가져오기
    public int getUserIdByUserName(String userName) throws SQLException {
        String sql = "SELECT uid FROM orderer WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("uid");
                }
            }
        }
        throw new SQLException("사용자명에 해당하는 사용자 ID를 찾을 수 없습니다.");
    }
}
