package org.example.project1.dashboard.UI;

import javax.swing.*;
import java.awt.*;

public class DashboardMainPanel extends JPanel {

    private RandomProductRecommendationPanel recommendationPanel;


    public DashboardMainPanel() {
        setLayout(null);  // 절대 위치 지정을 위해 null 레이아웃 사용
        setBackground(Color.WHITE);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        recommendationPanel = new RandomProductRecommendationPanel();
    }

    private void layoutComponents() {
        // RandomProductRecommendationPanel 배치
        recommendationPanel.setBounds(50, 50, 400, 200);  // x, y, width, height 조절
        add(recommendationPanel);


        // 테이블 배치
        JScrollPane scrollPane = new JScrollPane(recommendationPanel.getRecommendationTable());
        scrollPane.setBounds(50, 50, 350, 150);  // x, y, width, height 조절
        add(scrollPane);

        // 새로고침 버튼 배치
        JButton refreshButton = recommendationPanel.getRefreshButton();
        refreshButton.setBounds(200, 210, 100, 30);  // x, y, width, height 조절
        add(refreshButton);
    }
}