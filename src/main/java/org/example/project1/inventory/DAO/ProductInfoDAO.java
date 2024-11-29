package org.example.project1.inventory.DAO;

import org.example.project1._common._Ut.HikariCPDataSource;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;
import org.example.project1.inventory.VO.ProductInfoVO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductInfoDAO {
    private final DataSource dataSource;

    // 생성자: HikariCP를 통해 DataSource를 초기화합니다.
    public ProductInfoDAO() {
        this.dataSource = HikariCPDataSource.getInstance().getDataSource();
    }

    // ProductInfoVO 객체를 데이터베이스에 삽입하는 메서드
    public void insert(ProductInfoVO vo) throws SQLException {
        String sql = "INSERT INTO product_info(code, product_code, manufacturer_code, warehouse_id, price, stock, stock_date) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vo.getCode());
            ps.setInt(2, vo.getProduct_code());
            ps.setString(3, vo.getManufacturer_code());
            ps.setInt(4, vo.getWarehouse_id());
            ps.setInt(5, vo.getPrice());
            ps.setInt(6, vo.getStock());
            ps.setString(7, vo.getStock_date());
            ps.executeUpdate();
            System.out.println("Insert Success");
        }
    }

    // ProductInfoVO 객체를 데이터베이스에서 업데이트하는 메서드
    public void update(ProductInfoVO vo) throws SQLException {
        String sql = "UPDATE product_info SET manufacturer_code=?, warehouse_id=?, price=?, stock=?, stock_date=? WHERE code=? AND product_code=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vo.getManufacturer_code());
            ps.setInt(2, vo.getWarehouse_id());
            ps.setInt(3, vo.getPrice());
            ps.setInt(4, vo.getStock());
            ps.setString(5, vo.getStock_date());
            ps.setString(6, vo.getCode());
            ps.setInt(7, vo.getProduct_code());
            ps.executeUpdate();
            System.out.println("Update Success");
        }
    }

    // 지정된 코드와 제품 코드에 해당하는 레코드를 데이터베이스에서 삭제하는 메서드
    public void delete(String code, int product_code) throws SQLException {
        String sql = "DELETE FROM product_info WHERE code = ? AND product_code = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setInt(2, product_code);
            ps.executeUpdate();
            System.out.println("Delete Success");
        }
    }

    // 지정된 코드와 제품 코드에 해당하는 ProductInfoVO 객체를 데이터베이스에서 찾아 반환하는 메서드
    public ProductInfoVO find(String code, int product_code) throws SQLException {
        String sql = "SELECT * FROM product_info WHERE code = ? AND product_code = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.setInt(2, product_code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createProductInfoVOFromResultSet(rs);
                }
            }
        }
        return null;
    }

    // 모든 ProductInfoVO 객체를 데이터베이스에서 가져와 리스트로 반환하는 메서드
    public ArrayList<ProductInfoVO> getAll() throws SQLException {
        ArrayList<ProductInfoVO> list = new ArrayList<>();
        String sql = "SELECT * FROM product_info";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(createProductInfoVOFromResultSet(rs));
            }
        }
        return list;
    }

    // ResultSet에서 ProductInfoVO 객체를 생성하는 헬퍼 메서드
    private ProductInfoVO createProductInfoVOFromResultSet(ResultSet rs) throws SQLException {
        ProductInfoVO vo = new ProductInfoVO();
        vo.setCode(rs.getString("code"));
        vo.setProduct_code(rs.getInt("product_code"));
        vo.setManufacturer_code(rs.getString("manufacturer_code"));
        vo.setWarehouse_id(rs.getInt("warehouse_id"));
        vo.setPrice(rs.getInt("price"));
        vo.setStock(rs.getInt("stock"));
        vo.setStock_date(rs.getString("stock_date"));
        return vo;
    }

    // 제품명, 창고 ID, 제조업체명으로 재고를 검색하는 메서드
    public List<ProductInfoProductWarehouseInfoManufacturingVO> searchInventory(String productName, Integer warehouseId, String manufacturerName) throws SQLException {
        List<ProductInfoProductWarehouseInfoManufacturingVO> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, m.manufacturer_name, " +
                        "pi.warehouse_id, w.warehouse_location, pi.price, pi.stock, pi.stock_date " +
                        "FROM product_info pi " +
                        "JOIN product p ON pi.product_code = p.product_code " +
                        "JOIN warehouse_info w ON pi.warehouse_id = w.warehouse_id " +
                        "JOIN manufacturing m ON pi.manufacturer_code = m.manufacturer_code " +
                        "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();



        // 동적 쿼리 생성: 제품명, 창고 ID, 제조업체명에 따라 WHERE 절 추가
        if (productName != null && !productName.isEmpty()) {
            sql.append("AND p.product_name LIKE ? ");
            params.add("%" + productName + "%");
        }
        if (warehouseId != null) {
            sql.append("AND pi.warehouse_id = ? ");
            params.add(warehouseId);
        }
        if (manufacturerName != null && !manufacturerName.isEmpty()) {
            sql.append("AND m.manufacturer_name LIKE ? ");
            params.add("%" + manufacturerName + "%");
        }

        sql.append("ORDER BY pi.product_code, pi.warehouse_id, m.manufacturer_name");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            // 준비된 문에 파라미터 설정
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(createProductInfoProductWarehouseInfoManufacturingVOFromResultSet(rs));
                }
            }
        }

        return results;
    }
    /**
     * 재고 현황을 조회하는 메서드
     * @return 재고 현황 정보를 담은 List<ProductInfoProductVO>
     */
    public List<ProductInfoProductVO> getInventoryStatus() {
        List<ProductInfoProductVO> inventoryList = new ArrayList<>();
        String sql = "SELECT pi.code, pi.product_code, p.product_name, pi.manufacturer_code, " +
                "pi.warehouse_id, pi.price, pi.stock, pi.stock_date " +
                "FROM product_info pi " +
                "JOIN product p ON pi.product_code = p.product_code " +
                "ORDER BY pi.product_code";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                inventoryList.add(createProductInfoProductVOFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventoryList;
    }

    /**
     * ProductInfoProductVO 객체를 데이터베이스에 삽입하는 메서드
     * @param vo 삽입할 ProductInfoProductVO 객체
     * @throws SQLException SQL 예외 발생 시
     */
    public void insert(ProductInfoProductVO vo) throws SQLException {
        String sql = "INSERT INTO product_info(code, product_code, manufacturer_code, warehouse_id, price, stock, stock_date) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, vo.getCode());
            ps.setInt(2, vo.getProduct_code());
            ps.setString(3, vo.getManufacturer_code());
            ps.setInt(4, vo.getWarehouse_id());
            ps.setInt(5, vo.getPrice());
            ps.setInt(6, vo.getStock());
            ps.setString(7, vo.getStock_date());
            ps.executeUpdate();
            System.out.println("삽입 성공");
        }
    }

    // 다른 메서드들 (update, delete, find, getAll)도 유사하게 수정합니다.

    // ResultSet에서 ProductInfoProductVO 객체를 생성하는 헬퍼 메서드
    private ProductInfoProductVO createProductInfoProductVOFromResultSet(ResultSet rs) throws SQLException {
        return new ProductInfoProductVO(
                rs.getString("code"),
                rs.getInt("product_code"),
                rs.getString("product_name"),
                rs.getString("manufacturer_code"),
                rs.getInt("warehouse_id"),
                rs.getInt("price"),
                rs.getInt("stock"),
                rs.getString("stock_date")
        );
    }

    // ResultSet에서 ProductInfoProductWarehouseInfoManufacturingVO 객체를 생성하는 헬퍼 메서드
    private ProductInfoProductWarehouseInfoManufacturingVO createProductInfoProductWarehouseInfoManufacturingVOFromResultSet(ResultSet rs) throws SQLException {
        ProductInfoProductWarehouseInfoManufacturingVO vo = new ProductInfoProductWarehouseInfoManufacturingVO();
        vo.setCode(rs.getString("code"));
        vo.setProduct_code(rs.getInt("product_code"));
        vo.setProduct_name(rs.getString("product_name"));
        vo.setManufacturer_code(rs.getString("manufacturer_code"));
        vo.setManufacturer_name(rs.getString("manufacturer_name"));
        vo.setWarehouse_id(rs.getInt("warehouse_id"));
        vo.setWarehouse_location(rs.getString("warehouse_location"));
        vo.setPrice(rs.getInt("price"));
        vo.setStock(rs.getInt("stock"));
        Date sqlDate = rs.getDate("stock_date");
        vo.setStock_date(sqlDate != null ? sqlDate.toString() : null);
        return vo;
    }

}