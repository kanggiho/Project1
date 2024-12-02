package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * 재고 정보 수정을 위한 패널 클래스
 */
public class StockEditPanel extends JPanel {
    private final ProductInfoDAO dao;
    private final JButton editButton;
    private ActionListener editButtonListener;

    /**
     * StockEditPanel 생성자
     * 패널 초기화 및 컴포넌트 설정
     */
    public StockEditPanel() {
        dao = new ProductInfoDAO();
        setLayout(new BorderLayout());
        editButton = createEditButton();
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    /**
     * 수정 버튼 생성 메서드
     * @return 생성된 JButton
     */
    private JButton createEditButton() {
        JButton button = new JButton("선택 항목 수정");
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(120, 30));
        button.setEnabled(false);
        return button;
    }

    /**
     * 버튼 패널 생성 메서드
     * @return 생성된 JPanel
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        return buttonPanel;
    }

    /**
     * 수정 버튼에 대한 액션 리스너 설정 메서드
     * @param listener 설정할 ActionListener 객체
     */
    public void setEditButtonListener(ActionListener listener) {
        if (editButtonListener != null) {
            editButton.removeActionListener(editButtonListener);
        }
        editButtonListener = listener;
        editButton.addActionListener(listener);
    }

    /**
     * 수정 버튼의 활성화/비활성화 설정 메서드
     * @param enabled true면 버튼 활성화, false면 비활성화
     */
    public void setEditButtonEnabled(boolean enabled) {
        editButton.setEnabled(enabled);
    }

    /**
     * 제품 정보 업데이트 메서드
     * @param product 수정할 제품 정보 객체
     */
    public void updateProductInfo(ProductInfoProductVO product) {
        JPanel inputPanel = createInputPanel();
        JTextField priceField = (JTextField) inputPanel.getComponent(1);
        JTextField stockDateField = (JTextField) inputPanel.getComponent(3);
        JTextField warehouseIdField = (JTextField) inputPanel.getComponent(5);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "제품 정보 수정", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            updateProductInfoInDatabase(product, priceField.getText(), stockDateField.getText(), warehouseIdField.getText());
        }
    }

    /**
     * 입력 패널 생성 메서드
     * @return 생성된 JPanel
     */
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

    /**
     * 데이터베이스에 제품 정보 업데이트 메서드
     * @param product 수정할 제품 정보 객체
     * @param priceText 새 가격
     * @param stockDate 새 입고예정일
     * @param warehouseIdText 새 창고 ID
     */
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

    /**
     * 가격 업데이트 메서드
     * @param product 수정할 제품 정보 객체
     * @param priceText 새 가격
     * @return 업데이트 성공 여부
     * @throws SQLException SQL 예외 발생 시
     */
    private boolean updatePrice(ProductInfoProductVO product, String priceText) throws SQLException {
        if (!priceText.isEmpty()) {
            int price = Integer.parseInt(priceText);
            dao.updatePrice(product.getCode(), product.getProduct_code(), price);
            return true;
        }
        return false;
    }

    /**
     * 입고예정일 업데이트 메서드
     * @param product 수정할 제품 정보 객체
     * @param stockDate 새 입고예정일
     * @return 업데이트 성공 여부
     * @throws SQLException SQL 예외 발생 시
     */
    private boolean updateStockDate(ProductInfoProductVO product, String stockDate) throws SQLException {
        if (!stockDate.isEmpty()) {
            dao.updateStockDate(product.getCode(), product.getProduct_code(), stockDate);
            return true;
        }
        return false;
    }

    /**
     * 창고 ID 업데이트 메서드
     * @param product 수정할 제품 정보 객체
     * @param warehouseIdText 새 창고 ID
     * @return 업데이트 성공 여부
     * @throws SQLException SQL 예외 발생 시
     */
    private boolean updateWarehouseId(ProductInfoProductVO product, String warehouseIdText) throws SQLException {
        if (!warehouseIdText.isEmpty()) {
            int warehouseId = Integer.parseInt(warehouseIdText);
            dao.updateWarehouseId(product.getCode(), product.getProduct_code(), warehouseId);
            return true;
        }
        return false;
    }
}