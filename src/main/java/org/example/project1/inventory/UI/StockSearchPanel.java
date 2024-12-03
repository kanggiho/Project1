package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.plaf.FontUIResource;

public class StockSearchPanel extends JPanel {
    private static final String[] SEARCH_TYPES = {"자재명", "창고ID", "제조업체명"};
    private JComboBox<String> searchTypeComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private ProductInfoDAO productInfoDAO;
    private StockStatusPanel stockStatusPanel;
    private String toss_font = "머니그라피TTF Rounded";


    public StockSearchPanel(StockStatusPanel stockStatusPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.stockStatusPanel = stockStatusPanel;
        setUIFont();  // 폰트 설정
        setPanel();
        initUI();
        addAction();
    }

    private void setUIFont() {
        FontUIResource fontUIResource = new FontUIResource(new Font(toss_font, Font.PLAIN, 12));
        java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontUIResource);
            }
        }
    }

    private void setPanel() {
        setBackground(Color.WHITE);
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    private void initUI() {
        searchTypeComboBox = new JComboBox<>(SEARCH_TYPES);
        searchField = new JTextField(15);
        searchButton = new JButton("검색");

        add(new JLabel("검색 유형:"));
        add(searchTypeComboBox);
        add(new JLabel("검색어:"));
        add(searchField);
        add(searchButton);
    }

    private void addAction() {
        searchButton.addActionListener(e -> performSearch());
    }

    public void performSearch() {
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            showErrorMessage("검색어를 입력해주세요.", "입력 오류");
            return;
        }

        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> results = searchInventory(searchType, searchValue);
            stockStatusPanel.updateTable(results);
        } catch (SQLException e) {
            showErrorMessage("검색 중 오류가 발생했습니다: " + e.getMessage(), "검색 오류");
        } catch (NumberFormatException e) {
            showErrorMessage("창고번호는 숫자여야 합니다.", "입력 오류");
        }
    }

    public List<ProductInfoProductWarehouseInfoManufacturingVO> searchInventory(String searchType, String searchValue) throws SQLException {
        switch (searchType) {
            case "자재명":
                return productInfoDAO.searchInventory(searchValue, null, null);
            case "창고ID":
                int warehouseId = Integer.parseInt(searchValue);
                return productInfoDAO.searchInventory(null, warehouseId, null);
            case "제조업체명":
                return productInfoDAO.searchInventory(null, null, searchValue);
            default:
                throw new IllegalArgumentException("잘못된 검색 유형입니다.");
        }
    }

    public void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}