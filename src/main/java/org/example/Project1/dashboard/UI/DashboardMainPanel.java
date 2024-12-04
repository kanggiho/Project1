package org.example.project1.dashboard.UI;

import javax.swing.*;
import java.awt.*;

public class DashboardMainPanel extends JPanel {

    private RandomProductRecommendationPanel recommendationPanel;

    private PieChartPanel pieChartPanel;

    private JLabel chartTitleLabel;

    private Font font;

    public DashboardMainPanel() {
        setLayout(null);  // 절대 위치 지정을 위해 null 레이아웃 사용
        setBackground(Color.WHITE);

        init();
        initComponents();
        initChart();
        layoutComponents();
        setVisible(true);
    }
    private void init() {
        font = new Font("머니그라피TTF Rounded", Font.PLAIN, 20);
    }

    private void initComponents() {
        recommendationPanel = new RandomProductRecommendationPanel();


    }

    public void initChart(){
        pieChartPanel = new PieChartPanel();
        pieChartPanel.setBounds(570,50,500,500);
        chartTitleLabel = new JLabel();
        chartTitleLabel.setText("나의 발주 보기");
        chartTitleLabel.setFont(font);
        chartTitleLabel.setBounds(610,30,200,30);
        add(chartTitleLabel);
        add(pieChartPanel);
    }

    private void layoutComponents() {
        // RandomProductRecommendationPanel 배치
        recommendationPanel.setBounds(0, 0, 550, 150);  // x, y, width, height 조절
        add(recommendationPanel);
    }

    public void updateChart(){
        pieChartPanel.getNew();
    }
}