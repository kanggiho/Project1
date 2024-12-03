package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class StockEditPanel extends JPanel {
    private final ProductInfoDAO dao;
    private JButton editButton;
    private ActionListener editButtonListener;
    private String toss_font = "머니그라피TTF Rounded";

    public StockEditPanel() {
        this.dao = new ProductInfoDAO();
        setPanel();
        initUI();
        addAction();
    }

    private void setPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
    }

    private void initUI() {
        editButton = createEditButton();
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    private void addAction() {
        // 추가적인 액션 리스너 설정은 이곳에서 수행
    }

    private JButton createEditButton() {
        JButton button = new JButton("선택 항목 수정");
        button.setFont(new Font(toss_font, Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 30));
        button.setEnabled(false);
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        return buttonPanel;
    }

    public void setEditButtonListener(ActionListener listener) {
        if (editButtonListener != null) {
            editButton.removeActionListener(editButtonListener);
        }
        editButtonListener = listener;
        editButton.addActionListener(listener);
    }

    public void setEditButtonEnabled(boolean enabled) {
        editButton.setEnabled(enabled);
    }

    public void updateProductInfo(ProductInfoProductVO product) {
        JPanel inputPanel = createInputPanel();
        JTextField priceField = (JTextField) inputPanel.getComponent(1);
        JTextField stockDateField = (JTextField) inputPanel.getComponent(3);
        JTextField warehouseIdField = (JTextField) inputPanel.getComponent(5);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "제품 정보 수정", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            updateProductInfoInDatabase(product, priceField.getText(), stockDateField.getText(), warehouseIdField.getText());
        }
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("새 가격:"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("새 입고예정일 (YYYY-MM-DD):"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("새 창고 ID:"));
        panel.add(new JTextField(10));
        return panel;
    }

    private void updateProductInfoInDatabase(ProductInfoProductVO product, String priceText, String stockDate, String warehouseIdText) {
        try {
            boolean updated = updatePrice(product, priceText) | updateStockDate(product, stockDate) | updateWarehouseId(product, warehouseIdText);

            if (updated) {
                JOptionPane.showMessageDialog(null, "제품 정보가 성공적으로 업데이트되었습니다.");
                firePropertyChange("editCompleted", false, true);
            } else {
                JOptionPane.showMessageDialog(null, "업데이트할 정보를 입력해주세요.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "데이터베이스 오류: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "잘못된 숫자 형식입니다.");
        }
    }

    private boolean updatePrice(ProductInfoProductVO product, String priceText) throws SQLException {
        if (!priceText.isEmpty()) {
            int price = Integer.parseInt(priceText);
            dao.updatePrice(product.getCode(), product.getProduct_code(), price);
            return true;
        }
        return false;
    }

    private boolean updateStockDate(ProductInfoProductVO product, String stockDate) throws SQLException {
        if (!stockDate.isEmpty()) {
            dao.updateStockDate(product.getCode(), product.getProduct_code(), stockDate);
            return true;
        }
        return false;
    }

    private boolean updateWarehouseId(ProductInfoProductVO product, String warehouseIdText) throws SQLException {
        if (!warehouseIdText.isEmpty()) {
            int warehouseId = Integer.parseInt(warehouseIdText);
            dao.updateWarehouseId(product.getCode(), product.getProduct_code(), warehouseId);
            return true;
        }
        return false;
    }
}