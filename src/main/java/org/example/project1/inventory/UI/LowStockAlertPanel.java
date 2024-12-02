package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.Timer;

public class LowStockAlertPanel extends JPanel {
    private static final int LOW_STOCK_THRESHOLD = 30;
    private JTable alertTable;
    private DefaultTableModel tableModel;
    private ProductInfoDAO productInfoDAO;

    public LowStockAlertPanel() {
        this.productInfoDAO = new ProductInfoDAO();
        initUI();
        startAlertCheck();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("재고 부족 알림", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"제품 코드", "제품명", "현재 재고", "창고 ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        alertTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(alertTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> checkLowStock());
        add(refreshButton, BorderLayout.SOUTH);
    }

    public void refreshLowStockAlert() {
        checkLowStock();
    }

    private void startAlertCheck() {
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> refreshLowStockAlert());
            }
        });
        timer.start();
    }

    private void checkLowStock() {
        tableModel.setRowCount(0);
        List<ProductInfoProductVO> inventoryList = productInfoDAO.getInventoryStatus();
        List<String> lowStockMessages = new ArrayList<>();

        for (ProductInfoProductVO item : inventoryList) {
            if (item.getStock() < LOW_STOCK_THRESHOLD) {
                Object[] row = {
                        item.getProduct_code(),
                        item.getProduct_name(),
                        item.getStock(),
                        item.getWarehouse_id()
                };
                tableModel.addRow(row);
                lowStockMessages.add(String.format("제품 '%s'(코드: %s)의 재고가 %d개로 부족합니다.",
                        item.getProduct_name(), item.getProduct_code(), item.getStock()));
            }
        }

        if (!lowStockMessages.isEmpty()) {
            StringBuilder message = new StringBuilder("다음 제품의 재고가 부족합니다:\n\n");
            for (String msg : lowStockMessages) {
                message.append(msg).append("\n");
            }
            JOptionPane.showMessageDialog(this,
                    message.toString(),
                    "재고 부족 경고",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}