package org.example.project1.dashboard.UI;

import org.example.project1.dashboard.DAO.DashboardDAO;
import org.example.project1.dashboard.VO.ProductProductInfoManufacturingInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RandomProductRecommendationPanel extends JPanel {

    private JTable recommendationTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private DashboardDAO dashboardDAO;
    private JScrollPane scrollPane;
    private JLabel titleLabel;

    private String toss_font = "머니그라피TTF Rounded";


    public RandomProductRecommendationPanel() {
        dashboardDAO = new DashboardDAO();
        initUI();
        setBackground(Color.WHITE);
        initComponents();
        loadRandomRecommendations();
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
        titleLabel.setText("추천 상품");
        titleLabel.setBounds(50,7,100,15);
        titleLabel.setFont(tossFont2);
        add(titleLabel);

        // 테이블 모델 생성
        String[] columnNames = {"상품명", "제조사", "가격"};
        tableModel = new DefaultTableModel(columnNames, 0);
        recommendationTable = new JTable(tableModel);
        recommendationTable.setFont(tossFont);
        recommendationTable.setRowHeight(20);
        recommendationTable.setFillsViewportHeight(true); // 스크롤 시 전체 높이 채우기

        // 스크롤페인 설정 및 크기 지정
        scrollPane = new JScrollPane(recommendationTable);
        scrollPane.setBounds(50, 27, 350, 123); // 너비 350, 높이 100
        add(scrollPane);

        // 새로고침 버튼 생성
        refreshButton = new JButton("새로운 추천");
        refreshButton.setFont(tossFont);
        refreshButton.addActionListener(e -> loadRandomRecommendations());
        refreshButton.setBackground(Color.BLACK);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBounds(420, 100, 100, 30); // 버튼 위치 및 크기 조절
        add(refreshButton);
    }

    public void loadRandomRecommendations() {
        tableModel.setRowCount(0); // 기존 데이터 제거
        List<ProductProductInfoManufacturingInfoVO> recommendedProducts = dashboardDAO.getRandomRecommendedProducts();
        for (ProductProductInfoManufacturingInfoVO product : recommendedProducts) {
            Object[] row = {
                    product.getProductName(),
                    product.getManufacturerName(),
                    product.getPrice()
            };
            tableModel.addRow(row);
        }
    }
}
