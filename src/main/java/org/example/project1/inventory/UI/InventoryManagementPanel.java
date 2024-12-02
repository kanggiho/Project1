package org.example.project1.inventory.UI;

import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManagementPanel extends JPanel {
    String title;
    Frame frame;

    private StockStatusPanel stockStatusPanel;
    private StockEditPanel stockEditPanel;
    private StockUpdatePanel stockUpdatePanel;
    private LowStockAlertPanel lowStockAlertPanel;
    private StockSearchPanel stockSearchPanel;

    public InventoryManagementPanel(String title, Frame frame) {
        this.title = title;
        this.frame = frame;
        setPanel();
        initUI(); // UI 초기화
        setVisible(true);
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setLayout(null);
        setBackground(Color.WHITE);
    }

    // UI 초기화
    private void initUI() {

        //재고 관리 관련 패널 추가
        stockStatusPanel = new StockStatusPanel("재고관리 화면");
        stockEditPanel = new StockEditPanel();
        stockUpdatePanel = new StockUpdatePanel();
        stockSearchPanel = new StockSearchPanel(stockStatusPanel);
        lowStockAlertPanel = new LowStockAlertPanel();



        add(stockStatusPanel);
        add(stockEditPanel);
        add(stockUpdatePanel);
        add(stockSearchPanel);
        add(lowStockAlertPanel);



        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.add(stockSearchPanel, BorderLayout.NORTH);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("재고 현황", stockStatusPanel);
        tabbedPane.addTab("재고 부족 알림", lowStockAlertPanel);
        inventoryPanel.add(stockStatusPanel, BorderLayout.CENTER);
            //버튼 패널 생성 및 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> {
            stockStatusPanel.loadStockData();
            lowStockAlertPanel.refreshLowStockAlert(); // 수정된 부분
        });
        buttonPanel.add(refreshButton);

        JButton editButton = new JButton("선택 항목 수정");
        editButton.addActionListener(e -> openEditDialog());
        buttonPanel.add(editButton);

        JButton orderButton = new JButton("발주하기");
        orderButton.addActionListener(e -> openOrderDialog());
        buttonPanel.add(orderButton);

        inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);

        stockStatusPanel.addPropertyChangeListener("rowSelected", evt -> {
            boolean rowSelected = (boolean) evt.getNewValue();
            editButton.setEnabled(rowSelected);
        });

        setVisible(true);

    }

    //발주 다이얼로그 열기 메서드
    private void openOrderDialog() {
        JDialog orderDialog = new JDialog(frame, "발주하기", true);
        orderDialog.setContentPane(stockUpdatePanel);
        orderDialog.pack();
        orderDialog.setLocationRelativeTo(this);

        // 발주 완료 후 새로 고침을 위한 리스너 추가
        stockUpdatePanel.addPropertyChangeListener("stockUpdated", evt -> {
            stockStatusPanel.loadStockData();
            orderDialog.dispose();
        });

        orderDialog.setVisible(true);

        // 다이얼로그가 닫힌 후 재고 목록 새로고침
        stockStatusPanel.loadStockData();
    }

    // 수정 다이얼로그 열기 메서드
    private void openEditDialog() {
        try {
            ProductInfoProductVO selectedProduct = stockStatusPanel.getSelectedProduct();
            if (selectedProduct != null) {
                stockEditPanel.updateProductInfo(selectedProduct);
                stockStatusPanel.loadStockData(); // 데이터 갱신
            } else {
                JOptionPane.showMessageDialog(this, "수정할 항목을 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "데이터 형식 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류 발생: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void startAlertCheck() {
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> lowStockAlertPanel.refreshLowStockAlert());
            }
        });
        timer.start();
    }

}