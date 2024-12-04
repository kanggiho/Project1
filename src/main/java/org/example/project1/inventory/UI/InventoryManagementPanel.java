package org.example.project1.inventory.UI;

import org.example.project1._common.utility.ColorSet;
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
    private JButton lowStockButton; // LowStockAlertPanel버튼 사용하기위해
    private JButton refreshButton; // LowStockAlertPanel버튼 사용하기위해

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
        // 폰트 설정
        Font tossFont = new Font(toss_font, Font.PLAIN, 12);

        lowStockButton = new JButton("재고 부족 조회");
        lowStockButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        lowStockButton.setForeground(Color.BLACK);
        lowStockButton.setSize(110, 30);
        lowStockButton.setBackground(ColorSet.color_button[1]);

        refreshButton = new JButton("새로고침");
        refreshButton.setFont(tossFont);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(ColorSet.color_button[0]);
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
        stockEditPanel.setBounds(150,400,120,100);
        stockUpdatePanel.setBounds(450,400,100,100);
        stockSearchPanel.setBounds(300,10,500,30);

        // 버튼 위치 및 크기 설정
        lowStockButton.setBounds(750, 400, 150, 30);
        refreshButton.setBounds(800, 10, 100, 30);




        // 메인 패널에 추가
        add(stockStatusPanel);
        add(stockEditPanel);
        add(stockUpdatePanel);
        add(stockSearchPanel);
        // 버튼을 패널에 추가
        add(lowStockButton);
        add(refreshButton);


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

        //lowStockAlertPanel 기능
        lowStockButton.addActionListener(e -> lowStockAlertPanel.showLowStockItems());
        refreshButton.addActionListener(e -> stockStatusPanel.loadStockData());

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
    // 패널이 더 이상 필요하지 않을 때 타이머를 정지하는 메서드 추가
    public void cleanup() {
        if (lowStockAlertPanel != null) {
            lowStockAlertPanel.stopAlertTimer();
        }
    }
    // 기타 필요한 메서드들...
}