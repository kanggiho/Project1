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
import java.util.List;

public class InventoryManagementPanel extends JPanel {

    private StockEditPanel stockEditPanel;
    private StockUpdatePanel stockUpdatePanel;
    private LowStockAlertPanel lowStockAlertPanel;
    private StockSearchPanel stockSearchPanel;
    private StockStatusPanel stockStatusPanel;
    private String toss_font = "머니그라피TTF Rounded";

    public InventoryManagementPanel(String title, Frame frame) {

        setPanel();
        initUI();
        addAction();
        setVisible(true);
    }



    private void setPanel(){
        setSize(1100,450);
        setBackground(Color.WHITE);
        setLayout(null);
    }

    private void initUI(){
        // 패널 초기화
        removeAll();

        // StockStatusPanel 초기화 (제목 없이)
        stockStatusPanel = new StockStatusPanel();

        // StockEditPanel 초기화
        stockEditPanel = new StockEditPanel();

        // StockUpdatePanel 초기화
        stockUpdatePanel = new StockUpdatePanel(this);

        // StockSearchPanel 초기화
        stockSearchPanel = new StockSearchPanel(stockStatusPanel);

        // LowStockAlertPanel 초기화
        lowStockAlertPanel = new LowStockAlertPanel(stockStatusPanel);

        // 각 패널 위치 조절
        stockStatusPanel.setBounds(50,120,1000,210);
        stockEditPanel.setBounds(30,400,100,100);
        stockUpdatePanel.setBounds(330,400,100,100);
        stockSearchPanel.setBounds(300,10,500,30);
        lowStockAlertPanel.setBounds(630,400,300,100);




        // 메인 패널에 추가
        add(stockStatusPanel);
        add(stockEditPanel);
        add(stockUpdatePanel);
        add(stockSearchPanel);
        add(lowStockAlertPanel);

    }

    private void addAction(){

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

        // StockUpdatePanel의 재고 업데이트 이벤트 리스너 설정
        stockUpdatePanel.addPropertyChangeListener(evt -> {
            if ("stockUpdated".equals(evt.getPropertyName())) {
                stockStatusPanel.loadStockData();
            }
        });

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

    // StockSearchPanel의 메서드들을 위임
    public void performSearch() {
        stockSearchPanel.performSearch();
    }

    public List<ProductInfoProductWarehouseInfoManufacturingVO> searchInventory(String searchType, String searchValue) throws SQLException {
        return stockSearchPanel.searchInventory(searchType, searchValue);
    }

    public void showErrorMessage(String message, String title) {
        stockSearchPanel.showErrorMessage(message, title);
    }

    // LowStockAlertPanel 메서드 추가

    public void refreshLowStockAlert() {
        lowStockAlertPanel.showLowStockItems();
    }
    // 기타 필요한 메서드들...
}