package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import java.util.TimerTask;

//재고 부족시 알림 기능(재고가 30개 이하)
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

        // 제목 라벨 추가
        JLabel titleLabel = new JLabel("재고 부족 알림", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 테이블 초기화
        String[] columnNames = {"제품 코드", "제품명", "현재 재고", "창고 ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        alertTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(alertTable);
        add(scrollPane, BorderLayout.CENTER);

        // 새로고침 버튼 추가
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
        tableModel.setRowCount(0); // 기존 데이터 초기화
        List<ProductInfoProductVO> inventoryList = productInfoDAO.getInventoryStatus();

        boolean lowStockFound = false;
        for (ProductInfoProductVO item : inventoryList) {
            if (item.getStock() < LOW_STOCK_THRESHOLD) {
                Object[] row = {
                        item.getProduct_code(),
                        item.getProduct_name(),
                        item.getStock(),
                        item.getWarehouse_id()
                };
                tableModel.addRow(row);
                lowStockFound = true;
            }
        }


        if (lowStockFound) {
            JOptionPane.showMessageDialog(this,
                    "일부 제품의 재고가 부족합니다. 확인이 필요합니다.",
                    "재고 부족 경고",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}