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
    private String toss_font = "머니그라피TTF Rounded";



    public RandomProductRecommendationPanel() {
        dashboardDAO = new DashboardDAO();
        setBackground(Color.WHITE);
        initComponents();
        loadRandomRecommendations();
    }

    private void initComponents() {
        // 폰트 설정
        Font tossFont = new Font(toss_font, Font.PLAIN, 12);


        // 테이블 모델 생성
        String[] columnNames = {"상품명", "제조사", "가격"};
        tableModel = new DefaultTableModel(columnNames, 0);
        recommendationTable = new JTable(tableModel);
        recommendationTable.setFont(tossFont);

        // 새로고침 버튼 생성
        refreshButton = new JButton("새로운 추천");
        refreshButton.setFont(tossFont);
        refreshButton.addActionListener(e -> loadRandomRecommendations());
    }

    public void loadRandomRecommendations() {
        tableModel.setRowCount(0);
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

    public JTable getRecommendationTable() {
        return recommendationTable;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }
}