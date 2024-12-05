package org.example.project1.dashboard.UI;

import org.example.project1.dashboard.DAO.DashboardDAO;
import org.example.project1.dashboard.VO.InputProductProductInfoVO;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TodayInventoryPanel extends JPanel {

    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private DashboardDAO dashboardDAO;
    private JScrollPane scrollPane;
    private JLabel titleLabel;

    private String toss_font = "머니그라피TTF Rounded";

    public TodayInventoryPanel() {
        dashboardDAO = new DashboardDAO();
        initUI();
        setBackground(Color.WHITE);
        initComponents();
        loadTodayInventory();
        setVisible(true);
    }

    private void initUI() {
        setSize(550, 150);
        setLayout(null); // 절대 레이아웃 사용
    }

    private void initComponents() {
        // 폰트 설정
        Font tossFont = new Font(toss_font, Font.PLAIN, 12);
        Font tossFont2 = new Font(toss_font, Font.PLAIN, 15);

        titleLabel = new JLabel();
        titleLabel.setText("오늘의 입고 상품");
        titleLabel.setBounds(50, 7, 150, 15);
        titleLabel.setFont(tossFont2);
        add(titleLabel);

        // 테이블 모델 생성
        String[] columnNames = {"상품명", "입고 수량", "창고명"};
        tableModel = new DefaultTableModel(columnNames, 0);
        inventoryTable = new JTable(tableModel);
        inventoryTable.setFont(tossFont);
        inventoryTable.setRowHeight(20);
        inventoryTable.setFillsViewportHeight(true);

        // 스크롤페인 설정 및 크기 지정
        scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBounds(50, 27, 350, 123);
        add(scrollPane);


    }

    public void loadTodayInventory() {
        tableModel.setRowCount(0); // 기존 데이터 제거
        List<InputProductProductInfoVO> todayInputProducts = dashboardDAO.getTodayInputProducts();
        for (InputProductProductInfoVO product : todayInputProducts) {
            Object[] row = {
                    product.getProductName(),
                    product.getWarehousingQuantity(),
                    product.getPrice()
            };
            tableModel.addRow(row);
        }
    }
}