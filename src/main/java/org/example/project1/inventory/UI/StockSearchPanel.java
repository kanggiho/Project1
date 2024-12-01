package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

//재고 검색 자재명,창고번호,제조업체명 이용해서 재고 검색 기능및 필터링 검색 기능
public class StockSearchPanel extends JPanel {
    private JTextField productNameField;
    private JTextField warehouseIdField;
    private JTextField manufacturerNameField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private ProductInfoDAO productInfoDAO;

    // 생성자: 패널 초기화
    public StockSearchPanel() {
        this.productInfoDAO = new ProductInfoDAO();
        initUI();
    }

    // UI 구성요소 초기화 및 배치
    private void initUI() {
        setLayout(new BorderLayout());

        // 검색 필드 패널 생성
        JPanel searchPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 제품명 검색 필드
        searchPanel.add(new JLabel("제품명:"));
        productNameField = new JTextField();
        searchPanel.add(productNameField);

        // 창고 ID 검색 필드
        searchPanel.add(new JLabel("창고 ID:"));
        warehouseIdField = new JTextField();
        searchPanel.add(warehouseIdField);

        // 제조업체명 검색 필드
        searchPanel.add(new JLabel("제조업체명:"));
        manufacturerNameField = new JTextField();
        searchPanel.add(manufacturerNameField);

        // 검색 버튼
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // 결과 테이블 초기화
        String[] columnNames = {"제품 코드", "제품명", "제조업체", "창고 위치", "가격", "재고", "재고 날짜"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    // 검색 수행 메서드
    private void performSearch() {
        String productName = productNameField.getText().trim();
        String warehouseIdStr = warehouseIdField.getText().trim();
        String manufacturerName = manufacturerNameField.getText().trim();

        Integer warehouseId = null;
        if (!warehouseIdStr.isEmpty()) {
            try {
                warehouseId = Integer.parseInt(warehouseIdStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "창고 ID는 숫자여야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> results = productInfoDAO.searchInventory(
                    productName.isEmpty() ? null : productName,
                    warehouseId,
                    manufacturerName.isEmpty() ? null : manufacturerName
            );

            updateTable(results);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "검색 중 오류가 발생했습니다: " + e.getMessage(), "검색 오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 테이블 업데이트 메서드
    private void updateTable(List<ProductInfoProductWarehouseInfoManufacturingVO> results) {
        tableModel.setRowCount(0); // 기존 데이터 초기화

        for (ProductInfoProductWarehouseInfoManufacturingVO vo : results) {
            Object[] row = {
                    vo.getProduct_code(),
                    vo.getProduct_name(),
                    vo.getManufacturer_name(),
                    vo.getWarehouse_location(),
                    vo.getPrice(),
                    vo.getStock(),
                    vo.getStock_date()
            };
            tableModel.addRow(row);
        }
    }
}
