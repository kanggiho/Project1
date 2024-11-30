package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

//재고 현황 조회
public class StockStatusPanel extends JPanel {
    private String title;
    private JTable stockTable;
    private DefaultTableModel tableModel;
    private ProductInfoDAO productInfoDAO;

    // 생성자: 패널 초기화 및 데이터 로드
    public StockStatusPanel(String title) {
        this.title = title;
        this.productInfoDAO = new ProductInfoDAO();
        setPanel();
        initUI();
        loadStockData();
    }

    // 패널 레이아웃 설정
    private void setPanel() {
        setLayout(new BorderLayout());
    }

    // UI 구성요소 초기화 및 배치
    private void initUI() {
        // 제목 라벨 생성 및 추가
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 테이블 컬럼 이름 설정
        String[] columnNames = {"코드", "제품 코드", "제품명", "제조업체 코드", "창고 ID", "가격", "재고", "입고 예정일"};
        tableModel = new DefaultTableModel(columnNames, 0);
        stockTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane, BorderLayout.CENTER);

        // 새로고침 버튼 추가
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> loadStockData());
        add(refreshButton, BorderLayout.SOUTH);
    }

    // 재고 데이터 로드 및 테이블에 표시
    private void loadStockData() {
        tableModel.setRowCount(0); // 기존 데이터 초기화
        List<ProductInfoProductVO> inventoryList = productInfoDAO.getInventoryStatus();

        for (ProductInfoProductVO item : inventoryList) {
            Object[] row = {
                    item.getCode(),
                    item.getProduct_code(),
                    item.getProduct_name(),
                    item.getManufacturer_code(),
                    item.getWarehouse_id(),
                    item.getPrice(),
                    item.getStock(),
                    item.getStock_date()
            };
            tableModel.addRow(row);
        }
    }
}