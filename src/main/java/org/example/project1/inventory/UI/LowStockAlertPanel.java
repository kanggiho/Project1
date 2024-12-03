package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class LowStockAlertPanel extends JPanel {
    private static final int LOW_STOCK_THRESHOLD = 30;
    private ProductInfoDAO productInfoDAO;
    private StockStatusPanel stockStatusPanel;
    private ProductInfoProductWarehouseInfoManufacturingVO productInfoProductWarehouseInfoManufacturingVO;

    public LowStockAlertPanel(StockStatusPanel stockStatusPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.stockStatusPanel = stockStatusPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> stockStatusPanel.loadStockData());

        JButton lowStockButton = new JButton("재고 부족 조회");
        lowStockButton.addActionListener(e -> showLowStockItems());

        buttonPanel.add(refreshButton);
        buttonPanel.add(lowStockButton);

        add(buttonPanel, BorderLayout.CENTER);
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
}