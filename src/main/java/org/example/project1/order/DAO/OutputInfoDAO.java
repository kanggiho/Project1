package org.example.project1.order.DAO;

import org.example.project1.order.VO.OutputInfoVO;

import java.sql.*;

public class OutputInfoDAO {

    private Connection connection;

    public OutputInfoDAO() throws SQLException {
        // 데이터베이스 연결 설정
        String url = "jdbc:mysql://localhost:3306/project1"; // 실제 DB URL로 변경
        String user = "root"; // 실제 DB 사용자명으로 변경
        String password = "1234"; // 실제 DB 비밀번호로 변경
        this.connection = DriverManager.getConnection(url, user, password);
    }

    // 트랜잭션 시작
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    // 트랜잭션 커밋
    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    // 트랜잭션 롤백
    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }

    // `output_info` 테이블에 데이터 삽입
    public void insertOutputInfo(int productCode, int warehouseId, int userId, int confirmNum,
                                 Integer confirmId, String status, int unitPrice,
                                 int releaseQuantity, String releaseDate) throws SQLException {
        String sql = "INSERT INTO output_info " +
                "(product_code, warehouse_id, user_id, confirm_num, confirm_id, status, unit_price, release_quantity, release_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productCode);
            pstmt.setInt(2, warehouseId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, confirmNum);
            pstmt.setInt(5, confirmId);
            pstmt.setString(6, status);
            pstmt.setInt(7, unitPrice);
            pstmt.setInt(8, releaseQuantity);
            pstmt.setDate(9, Date.valueOf(releaseDate));
            pstmt.executeUpdate();
        }
    }

    // output_info 테이블에 데이터 삽입
    public void insertOutputInfo(OutputInfoVO outputInfo) throws SQLException {
        String sql = "INSERT INTO output_info (product_code, warehouse_id, user_id, confirm_num, confirm_id, status, unit_price, release_quantity, release_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = connection;
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        } catch (Exception e) {

        }
    }

}
