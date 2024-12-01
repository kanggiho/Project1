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
    private String title;
    private JTable stockTable;
    private DefaultTableModel tableModel;
    private ProductInfoDAO productInfoDAO;

    /**
     * StockStatusPanel 생성자
     * @param title 패널의 제목
     */
    public StockStatusPanel(String title) {
        this.title = title;
        this.productInfoDAO = new ProductInfoDAO();
        setPanel();
        initUI();
        loadStockData();
    }

    /**
     * 패널의 레이아웃을 설정하는 메소드
     */
    private void setPanel() {
        setLayout(new BorderLayout());
    }

    /**
     * UI 컴포넌트를 초기화하고 배치하는 메소드
     */
    private void initUI() {
        // 제목 라벨 생성 및 추가
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 테이블 모델 및 테이블 생성
        String[] columnNames = {"코드", "제품 코드", "제품명", "제조업체 코드", "제조업체명", "창고 ID", "가격", "재고", "입고 예정일"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane, BorderLayout.CENTER);

        // 버튼 패널 생성 및 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> loadStockData());
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // 테이블 행 선택 리스너 추가
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
            List<ProductInfoProductVO> inventoryList = productInfoDAO.getInventoryStatus();


            // 테이블 모델 데이터를 한 번에 설정
            Object[][] data = new Object[inventoryList.size()][8];
            for (int i = 0; i < inventoryList.size(); i++) {
                ProductInfoProductVO item = inventoryList.get(i);
                data[i] = new Object[]{
                        item.getCode(),
                        item.getProduct_code(),
                        item.getProduct_name(),
                        item.getManufacturer_code(),
                        item.getWarehouse_id(),
                        item.getPrice(),
                        item.getStock(),
                        item.getStock_date()
                };
            }

            // 컬럼 이름 배열 (필요에 따라 수정)
            String[] columnNames = {"코드", "제품 코드", "제품명", "제조사 코드", "창고 ID", "가격", "재고", "입고일"};

            // 테이블 모델 한 번에 업데이트
            tableModel.setDataVector(data, columnNames);

            // 테이블 새로고침
            stockTable.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "데이터 로딩 중 오류 발생: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
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
                    (Integer) tableModel.getValueAt(selectedRow, 1),
                    (String) tableModel.getValueAt(selectedRow, 2),
                    (String) tableModel.getValueAt(selectedRow, 3),
                    (Integer) tableModel.getValueAt(selectedRow, 4),
                    (Integer) tableModel.getValueAt(selectedRow, 5),
                    (Integer) tableModel.getValueAt(selectedRow, 6),
                    (String) tableModel.getValueAt(selectedRow, 7)
            );
        }
        return null;
    }
    public void updateTable(List<ProductInfoProductWarehouseInfoManufacturingVO> results) {
        tableModel.setRowCount(0); // 기존 데이터 초기화

        for (ProductInfoProductWarehouseInfoManufacturingVO vo : results) {
            Object[] row = {
                    vo.getCode(),
                    vo.getProduct_code(),
                    vo.getProduct_name(),
                    vo.getManufacturer_name(),
                    vo.getWarehouse_id(),
                    vo.getPrice(),
                    vo.getStock(),
                    vo.getStock_date()
            };
            tableModel.addRow(row);
        }
    }
}