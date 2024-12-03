package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.SQLException;

public class StockUpdatePanel extends JPanel {
    private ProductInfoDAO productInfoDAO;
    private InventoryManagementPanel parentPanel;
    private PropertyChangeSupport pcs;
    private JButton orderButton;

    public StockUpdatePanel(InventoryManagementPanel parentPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.parentPanel = parentPanel;
        this.pcs = new PropertyChangeSupport(this);

        setPanel();
        initUI();
        addAction();
    }

    private void setPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
    }

    private void initUI() {
        orderButton = new JButton("발주하기");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addAction() {
        orderButton.addActionListener(e -> showOrderDialog());
    }

    private void showOrderDialog() {
        JTextField productNameField = new JTextField(15);
        JTextField quantityField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("제품명:"));
        panel.add(productNameField);
        panel.add(new JLabel("감소 수량:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "발주하기",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            updateStock(productNameField.getText(), quantityField.getText());
        }
    }

    private void updateStock(String productName, String quantityStr) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "올바른 수량을 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int updatedRows = productInfoDAO.decreaseStockByProductName(productName, quantity);
            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "재고가 성공적으로 업데이트되었습니다.", "업데이트 성공", JOptionPane.INFORMATION_MESSAGE);
                pcs.firePropertyChange("stockUpdated", null, null);
                parentPanel.loadStockData();
            } else {
                JOptionPane.showMessageDialog(this, "재고 업데이트에 실패했습니다. 제품명과 재고를 확인하세요.", "업데이트 실패", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
}