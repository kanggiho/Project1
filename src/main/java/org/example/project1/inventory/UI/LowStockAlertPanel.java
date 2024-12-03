package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Timer;
import java.util.TimerTask;

public class LowStockAlertPanel extends JPanel {
    private static final int LOW_STOCK_THRESHOLD = 30;
    private ProductInfoDAO productInfoDAO;
    private StockStatusPanel stockStatusPanel;
    private Timer alertTimer;

    public LowStockAlertPanel(StockStatusPanel stockStatusPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.stockStatusPanel = stockStatusPanel;
        setPanel();
        initUI();
        startAlertTimer();
    }

    private void setPanel() {
        setSize(300, 100);
        setBackground(Color.WHITE);
        setLayout(null);
    }

    private void initUI() {
        // UI 요소를 추가할 필요가 없어졌으므로 비워둡니다.
    }

    public void showLowStockItems() {
        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> inventoryList = productInfoDAO.getInventoryStatusWithManufacturer();
            List<ProductInfoProductWarehouseInfoManufacturingVO> lowStockItems = inventoryList.stream()
                    .filter(item -> item.getStock() < LOW_STOCK_THRESHOLD)
                    .collect(Collectors.toList());

            if (!lowStockItems.isEmpty()) {
                stockStatusPanel.updateTable(lowStockItems);
                JOptionPane.showMessageDialog(this, "재고 부족 상품이 표시됩니다.", "재고 부족 알림", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "재고 부족 상품이 없습니다.", "재고 상태", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터베이스 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void startAlertTimer() {
        alertTimer = new Timer();
        alertTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> checkAndNotifyLowStock());
            }
        }, 0, 60000); // 60000밀리초(1분)마다 실행
    }

    private void checkAndNotifyLowStock() {
        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> inventoryList = productInfoDAO.getInventoryStatusWithManufacturer();
            List<ProductInfoProductWarehouseInfoManufacturingVO> lowStockItems = inventoryList.stream()
                    .filter(item -> item.getStock() < LOW_STOCK_THRESHOLD)
                    .collect(Collectors.toList());

            if (!lowStockItems.isEmpty()) {
                StringBuilder message = new StringBuilder("다음 상품의 재고가 부족합니다:\n\n");
                for (ProductInfoProductWarehouseInfoManufacturingVO item : lowStockItems) {
                    message.append(String.format("- %s (현재 재고: %d)\n", item.getProduct_name(), item.getStock()));
                }
                message.append(String.format("\n총 %d개의 상품의 재고가 부족합니다.", lowStockItems.size()));

                JOptionPane.showMessageDialog(this, message.toString(), "재고 부족 알림", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void stopAlertTimer() {
        if (alertTimer != null) {
            alertTimer.cancel();
            alertTimer = null;
        }
    }
}