package org.example.project1.inventory.UI;


import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

//재고 정보 수정 가격,입고예정일,창고번호, 수정
public class StockEditPanel extends JPanel {
    private ProductInfoDAO dao;
    private JTextField productNameField;
    private JTextField priceField;
    private JTextField stockDateField;
    private JTextField warehouseIdField;
    private JTextArea resultArea;
    public StockEditPanel() {
        dao = new ProductInfoDAO();
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        // 입력 필드 패널
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new JLabel("자재명:"));
        productNameField = new JTextField(20);
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("새 가격:"));
        priceField = new JTextField(20);
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("새 입고예정일 (YYYY-MM-DD):"));
        stockDateField = new JTextField(20);
        inputPanel.add(stockDateField);
        inputPanel.add(new JLabel("새 창고 ID:"));
        warehouseIdField = new JTextField(20);
        inputPanel.add(warehouseIdField);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("수정");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductInfo();
            }
        });
        buttonPanel.add(updateButton);

        // 결과 표시 영역
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // 패널에 컴포넌트 추가
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * 제품 정보를 업데이트하는 메서드
     */
    private void updateProductInfo() {
        String productName = productNameField.getText();
        String priceText = priceField.getText();
        String stockDate = stockDateField.getText();
        String warehouseIdText = warehouseIdField.getText();

        try {
            // 자재명으로 제품 찾기
            ProductInfoProductVO product = findProductByName(productName);

            if (product == null) {
                resultArea.setText("해당 자재명의 제품을 찾을 수 없습니다.");
                return;
            }

            // 입력된 정보에 따라 업데이트 수행
            if (!priceText.isEmpty() && !stockDate.isEmpty() && !warehouseIdText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                int warehouseId = Integer.parseInt(warehouseIdText);
                dao.updateAll(product.getCode(), product.getProduct_code(), price, stockDate, warehouseId);
                resultArea.setText("가격, 입고예정일, 창고 ID가 모두 업데이트되었습니다.");
            } else if (!priceText.isEmpty() && !stockDate.isEmpty()) {
                int price = Integer.parseInt(priceText);
                dao.updatePriceAndStockDate(product.getCode(), product.getProduct_code(), price, stockDate);
                resultArea.setText("가격과 입고예정일이 업데이트되었습니다.");
            } else if (!priceText.isEmpty() && !warehouseIdText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                int warehouseId = Integer.parseInt(warehouseIdText);
                dao.updatePriceAndWarehouseId(product.getCode(), product.getProduct_code(), price, warehouseId);
                resultArea.setText("가격과 창고 ID가 업데이트되었습니다.");
            } else if (!stockDate.isEmpty() && !warehouseIdText.isEmpty()) {
                int warehouseId = Integer.parseInt(warehouseIdText);
                dao.updateStockDateAndWarehouseId(product.getCode(), product.getProduct_code(), stockDate, warehouseId);
                resultArea.setText("입고예정일과 창고 ID가 업데이트되었습니다.");
            } else if (!priceText.isEmpty()) {
                int price = Integer.parseInt(priceText);
                dao.updatePrice(product.getCode(), product.getProduct_code(), price);
                resultArea.setText("가격이 업데이트되었습니다.");
            } else if (!stockDate.isEmpty()) {
                dao.updateStockDate(product.getCode(), product.getProduct_code(), stockDate);
                resultArea.setText("입고예정일이 업데이트되었습니다.");
            } else if (!warehouseIdText.isEmpty()) {
                int warehouseId = Integer.parseInt(warehouseIdText);
                dao.updateWarehouseId(product.getCode(), product.getProduct_code(), warehouseId);
                resultArea.setText("창고 ID가 업데이트되었습니다.");
            } else {
                resultArea.setText("업데이트할 정보를 입력해주세요.");
            }
        } catch (SQLException ex) {
            resultArea.setText("데이터베이스 오류: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            resultArea.setText("잘못된 숫자 형식입니다.");
        }
    }

    /**
     * 자재명으로 제품을 찾는 메서드
     * @param productName 찾을 제품의 자재명
     * @return 찾은 ProductInfoProductVO 객체, 없으면 null
     * @throws SQLException SQL 예외 발생 시
     */
    private ProductInfoProductVO findProductByName(String productName) throws SQLException {
        List<ProductInfoProductVO> inventoryList = dao.getInventoryStatus();
        for (ProductInfoProductVO item : inventoryList) {
            if (item.getProduct_name().equalsIgnoreCase(productName)) {
                return item;
            }
        }
        return null;
    }

}
