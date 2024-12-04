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
        setVisible(true);
    }

    private void initComponents() {
        recommendationPanel = new RandomProductRecommendationPanel();
    }

    private void layoutComponents() {
        // RandomProductRecommendationPanel 배치
        recommendationPanel.setBounds(0, 0, 550, 150);  // x, y, width, height 조절
        add(recommendationPanel);



    }
}