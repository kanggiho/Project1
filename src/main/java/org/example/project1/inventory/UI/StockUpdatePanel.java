package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

// 재고 수량 업데이트
public class StockUpdatePanel extends JPanel {
    private ProductInfoDAO productInfoDAO;
    private JTextField productNameField;
    private JTextField quantityField;

    // 생성자: 패널 초기화
    public StockUpdatePanel() {
        this.productInfoDAO = new ProductInfoDAO();
        initUI();
    }

    // UI 구성요소 초기화 및 배치
    private void initUI() {
        setLayout(new BorderLayout());

        // 제목 라벨 생성 및 추가
        JLabel titleLabel = new JLabel("재고 수량 업데이트", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 입력 필드와 버튼을 포함할 패널
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        productNameField = new JTextField(15);
        quantityField = new JTextField(5);
        JButton updateButton = new JButton("재고 감소");

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("제품명:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(productNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("감소 수량:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(quantityField, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(updateButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // 업데이트 버튼 액션 리스너
        updateButton.addActionListener(e -> updateStock());
    }

    // 재고 수량 업데이트 메서드
    private void updateStock() {
        String productName = productNameField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "올바른 수량을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int updatedRows = productInfoDAO.decreaseStockByProductName(productName, quantity);
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "재고가 성공적으로 업데이트되었습니다.", "업데이트 성공", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "재고 업데이트에 실패했습니다. 제품명과 재고를 확인하세요.", "업데이트 실패", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }

        // 입력 필드 초기화
        productNameField.setText("");
        quantityField.setText("");
    }
}