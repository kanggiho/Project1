package org.example.project1.dashboard.UI;

import org.example.project1.dashboard.DAO.DashboardDAO;
import org.example.project1.dashboard.VO.ProductOutputInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
//인기품목 top5를 표시하는 페널
public class TopSellingProductsPanel extends JPanel {

    private JTable topSellingTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private DashboardDAO dashboardDAO;
    private JScrollPane scrollPane;
    private JLabel titleLabel;

    private String toss_font = "머니그라피TTF Rounded";

    public TopSellingProductsPanel() {
        dashboardDAO = new DashboardDAO();
        initUI();
        setBackground(Color.WHITE);
        initComponents();
        loadTopSellingProducts();
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
        titleLabel.setText("인기 품목 Top 5");
        titleLabel.setBounds(50, 10, 120, 15);
        titleLabel.setFont(tossFont2);
        add(titleLabel);

        // 테이블 모델 생성
        String[] columnNames = {"상품명", "판매량"};
        tableModel = new DefaultTableModel(columnNames, 0);
        topSellingTable = new JTable(tableModel);
        topSellingTable.setFont(tossFont);
        topSellingTable.setRowHeight(20);
        topSellingTable.setFillsViewportHeight(true);

        // 스크롤페인 설정 및 크기 지정
        scrollPane = new JScrollPane(topSellingTable);
        scrollPane.setBounds(50, 30, 350, 123);  // y좌표 30, 높이 80으로 수정
        add(scrollPane);

        // 새로고침 버튼 생성
        refreshButton = new JButton("인기 순위");
        refreshButton.setFont(tossFont);
        refreshButton.addActionListener(e -> loadTopSellingProducts());
        refreshButton.setBackground(Color.BLACK);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBounds(420, 80, 100, 30);  // y좌표를 80으로 수정
        add(refreshButton);
    }

    public void loadTopSellingProducts() {
        tableModel.setRowCount(0); // 기존 데이터 제거
        List<ProductOutputInfoVO> topSellingProducts = dashboardDAO.getTopSellingProducts();
        for (ProductOutputInfoVO product : topSellingProducts) {
            Object[] row = {
                    product.getProductName(),
                    product.getReleaseQuantity()
            };
            tableModel.addRow(row);
        }
    }
}