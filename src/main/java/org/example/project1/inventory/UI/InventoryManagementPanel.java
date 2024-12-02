package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;

public class InventoryManagementPanel extends JPanel {

    private StockEditPanel stockEditPanel;
    private StockUpdatePanel stockUpdatePanel;
    private LowStockAlertPanel lowStockAlertPanel;
    private StockSearchPanel stockSearchPanel;
    private StockStatusPanel stockStatusPanel;

    public InventoryManagementPanel(String title, Frame frame) {
        setLayout(new BorderLayout());

        // StockStatusPanel 초기화 (제목 없이)
        stockStatusPanel = new StockStatusPanel();

        // StockEditPanel 초기화
        stockEditPanel = new StockEditPanel();

        // 다른 패널들 초기화 (필요에 따라)
        // stockUpdatePanel = new StockUpdatePanel();
        // lowStockAlertPanel = new LowStockAlertPanel();
        // stockSearchPanel = new StockSearchPanel();
        setEditButtonListener(e -> {
            ProductInfoProductVO selectedProduct = stockStatusPanel.getSelectedProduct();
            if (selectedProduct != null) {
                stockEditPanel.updateProductInfo(selectedProduct);
            }
        });

        stockStatusPanel.addRowSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean rowSelected = stockStatusPanel.getSelectedProduct() != null;
                stockEditPanel.setEditButtonEnabled(rowSelected);
            }
        });

        // 수정 완료 리스너 설정
        addEditCompletedListener(evt -> {
            if ((Boolean) evt.getNewValue()) {
                stockStatusPanel.loadStockData();
            }
        });

        // 메인 패널에 StockStatusPanel 추가
        add(stockStatusPanel, BorderLayout.CENTER);

        // StockEditPanel을 하단에 추가
        add(stockEditPanel, BorderLayout.SOUTH);

        // 패널 크기 설정
        setPreferredSize(new Dimension(800, 600));
    }

    // 재고 데이터를 로드하는 메서드
    public void loadStockData() {
        stockStatusPanel.loadStockData();
    }

    // StockEditPanel의 메서드들을 위임
    public void setEditButtonListener(ActionListener listener) {
        stockEditPanel.setEditButtonListener(listener);
    }

    public void setEditButtonEnabled(boolean enabled) {
        stockEditPanel.setEditButtonEnabled(enabled);
    }

    public void updateProductInfo(ProductInfoProductVO product) {
        stockEditPanel.updateProductInfo(product);
    }

    // 편집 완료 이벤트를 전달하는 메서드
    public void addEditCompletedListener(PropertyChangeListener listener) {
        stockEditPanel.addPropertyChangeListener("editCompleted", listener);
    }


    // 기타 필요한 메서드들...
}