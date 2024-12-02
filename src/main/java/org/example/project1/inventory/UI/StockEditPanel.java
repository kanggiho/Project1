package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * 재고 정보 수정을 위한 패널 클래스
 * 가격, 입고예정일, 창고번호를 수정할 수 있음
 */
public class StockEditPanel extends JPanel {
    // ProductInfoDAO 객체 선언
    private ProductInfoDAO dao;
    // 수정 버튼 선언
    private JButton editButton;
    // 수정 버튼에 대한 ActionListener 선언
    private ActionListener editButtonListener;

    /**
     * StockEditPanel 생성자
     * 패널 초기화 및 컴포넌트 설정
     */
    public StockEditPanel() {
        // ProductInfoDAO 객체 초기화
        dao = new ProductInfoDAO();
        // 패널의 레이아웃을 BorderLayout으로 설정
        setLayout(new BorderLayout());
        // UI 컴포넌트 초기화 메서드 호출
        initComponents();
    }

    /**
     * UI 컴포넌트 초기화 메서드
     */
    private void initComponents() {
        // 수정 버튼 생성 및 설정
        editButton = new JButton("선택 항목 수정");
        editButton.setFont(new Font("Arial", Font.PLAIN, 14));
        editButton.setPreferredSize(new Dimension(120, 30));
        editButton.setEnabled(false);

        // 버튼을 담을 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        // 버튼 패널을 메인 패널의 중앙에 추가
        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * 수정 버튼에 대한 액션 리스너 설정 메서드
     * @param listener 설정할 ActionListener 객체
     */
    public void setEditButtonListener(ActionListener listener) {
        // 기존 리스너가 있다면 제거
        if (editButtonListener != null) {
            editButton.removeActionListener(editButtonListener);
        }
        // 새 리스너 설정
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
     * 다이얼로그를 통해 사용자로부터 새로운 정보를 입력받아 데이터베이스 업데이트
     * @param product 수정할 제품 정보 객체
     */
    public void updateProductInfo(ProductInfoProductVO product) {
        // 사용자 입력을 위한 텍스트 필드 생성
        JTextField priceField = new JTextField(10);
        JTextField stockDateField = new JTextField(10);
        JTextField warehouseIdField = new JTextField(10);

        // 입력 필드를 담을 패널 생성
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("새 가격:"));
        panel.add(priceField);
        panel.add(new JLabel("새 입고예정일 (YYYY-MM-DD):"));
        panel.add(stockDateField);
        panel.add(new JLabel("새 창고 ID:"));
        panel.add(warehouseIdField);

        // 다이얼로그 표시
        int result = JOptionPane.showConfirmDialog(null, panel,
                "제품 정보 수정", JOptionPane.OK_CANCEL_OPTION);

        // 사용자가 OK를 선택한 경우
        if (result == JOptionPane.OK_OPTION) {
            try {
                // 사용자 입력 값 가져오기
                String priceText = priceField.getText();
                String stockDate = stockDateField.getText();
                String warehouseIdText = warehouseIdField.getText();

                boolean updated = false;

                // 가격 업데이트
                if (!priceText.isEmpty()) {
                    int price = Integer.parseInt(priceText);
                    dao.updatePrice(product.getCode(), product.getProduct_code(), price);
                    updated = true;
                }
                // 입고예정일 업데이트
                if (!stockDate.isEmpty()) {
                    dao.updateStockDate(product.getCode(), product.getProduct_code(), stockDate);
                    updated = true;
                }
                // 창고 ID 업데이트
                if (!warehouseIdText.isEmpty()) {
                    int warehouseId = Integer.parseInt(warehouseIdText);
                    dao.updateWarehouseId(product.getCode(), product.getProduct_code(), warehouseId);
                    updated = true;
                }

                // 업데이트 결과 메시지 표시
                if (updated) {
                    JOptionPane.showMessageDialog(null, "제품 정보가 성공적으로 업데이트되었습니다.");
                    firePropertyChange("editCompleted", false, true);
                } else {
                    JOptionPane.showMessageDialog(null, "업데이트할 정보를 입력해주세요.");
                }
            } catch (SQLException ex) {
                // 데이터베이스 오류 처리
                JOptionPane.showMessageDialog(null, "데이터베이스 오류: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                // 숫자 형식 오류 처리
                JOptionPane.showMessageDialog(null, "잘못된 숫자 형식입니다.");
            }
        }
    }
}