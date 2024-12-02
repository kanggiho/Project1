package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 재고 상태를 표시하는 패널 클래스
 */
public class StockStatusPanel extends JPanel {
    // 테이블 열 이름 상수
    private static final String[] COLUMN_NAMES = {
            "코드", "제품 코드", "제품명", "제조업체 코드", "제조업체명",
            "창고 ID", "창고 위치", "가격", "재고", "입고 예정일"
    };
    // 폰트 관련 상수
    private static final String FONT_NAME = "Arial";
    private static final int TITLE_FONT_SIZE = 18;

    private final String title; // 패널 제목
    private final JTable stockTable; // 재고 정보를 표시할 테이블
    private final DefaultTableModel tableModel; // 테이블 모델
    private final ProductInfoDAO productInfoDAO; // 데이터 접근 객체

    /**
     * StockStatusPanel 생성자
     * @param title 패널의 제목
     */
    public StockStatusPanel(String title) {
        this.title = title;
        this.productInfoDAO = new ProductInfoDAO();
        this.tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
        this.stockTable = new JTable(tableModel);

        initializePanel();
        loadStockData();
    }

    /**
     * 패널 초기화 메소드
     */
    private void initializePanel() {
        setLayout(new BorderLayout());
        add(createTitleLabel(), BorderLayout.NORTH);
        add(new JScrollPane(stockTable), BorderLayout.CENTER);
        setupTableSelectionListener();
    }

    /**
     * 제목 라벨 생성 메소드
     * @return 생성된 JLabel 객체
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        return titleLabel;
    }

    /**
     * 테이블 선택 리스너 설정 메소드
     */
    private void setupTableSelectionListener() {
        stockTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = stockTable.getSelectedRow() != -1;
            firePropertyChange("rowSelected", !rowSelected, rowSelected);
        });
    }

    /**
     * 재고 데이터를 로드하고 테이블에 표시하는 메소드
     */
    public void loadStockData() {
        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> inventoryList =
                    productInfoDAO.getInventoryStatusWithManufacturer();
            updateTable(inventoryList);
        } catch (SQLException e) {
            showErrorMessage("데이터 로딩 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * ProductInfoProductWarehouseInfoManufacturingVO 리스트로 테이블을 업데이트하는 메소드
     * @param results ProductInfoProductWarehouseInfoManufacturingVO 객체 리스트
     */
    public void updateTable(List<ProductInfoProductWarehouseInfoManufacturingVO> results) {
        tableModel.setRowCount(0);
        for (ProductInfoProductWarehouseInfoManufacturingVO vo : results) {
            tableModel.addRow(createRowData(vo));
        }
        stockTable.repaint();
    }

    /**
     * VO 객체에서 테이블 행 데이터를 생성하는 메소드
     * @param vo ProductInfoProductWarehouseInfoManufacturingVO 객체
     * @return 테이블 행 데이터 배열
     */
    private Object[] createRowData(ProductInfoProductWarehouseInfoManufacturingVO vo) {
        return new Object[]{
                vo.getCode(), vo.getProduct_code(), vo.getProduct_name(),
                vo.getManufacturer_code(), vo.getManufacturer_name(),
                vo.getWarehouse_id(), vo.getWarehouse_location(),
                vo.getPrice(), vo.getStock(), vo.getStock_date()
        };
    }

    /**
     * 현재 선택된 제품의 정보를 반환하는 메소드
     * @return 선택된 제품의 ProductInfoProductVO 객체, 선택된 제품이 없으면 null
     */
    public ProductInfoProductVO getSelectedProduct() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow != -1) {
            return new ProductInfoProductVO(
                    (String) tableModel.getValueAt(selectedRow, 0),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()),
                    (String) tableModel.getValueAt(selectedRow, 2),
                    (String) tableModel.getValueAt(selectedRow, 3),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString()),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 7).toString()),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 8).toString()),
                    (String) tableModel.getValueAt(selectedRow, 9)
            );
        }
        return null;
    }

    /**
     * 오류 메시지를 표시하는 메소드
     * @param message 표시할 오류 메시지
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }
}