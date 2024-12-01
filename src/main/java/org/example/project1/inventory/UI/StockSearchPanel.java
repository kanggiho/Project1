package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

//재고 검색 자재명,창고번호,제조업체명 이용해서 재고 검색 기능및 필터링 검색 기능
public class StockSearchPanel extends JPanel {
    private JComboBox<String> searchTypeComboBox;
    private JTextField searchField;
    private ProductInfoDAO productInfoDAO;
    private StockStatusPanel stockStatusPanel;

    public StockSearchPanel(StockStatusPanel stockStatusPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.stockStatusPanel = stockStatusPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] searchTypes = {"자재명", "창고번호", "제조업체명"};
        searchTypeComboBox = new JComboBox<>(searchTypes);
        searchField = new JTextField(15);
        JButton searchButton = new JButton("검색");

        add(new JLabel("검색 유형:"));
        add(searchTypeComboBox);
        add(new JLabel("검색어:"));
        add(searchField);
        add(searchButton);

        searchButton.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        String searchValue = searchField.getText().trim();

        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<ProductInfoProductWarehouseInfoManufacturingVO> results;
            switch (searchType) {
                case "자재명":
                    results = productInfoDAO.searchInventory(searchValue, null, null);
                    break;
                case "창고ID":
                    int warehouseId = Integer.parseInt(searchValue);
                    results = productInfoDAO.searchInventory(null, warehouseId, null);
                    break;
                case "제조업체명":
                    results = productInfoDAO.searchInventory(null, null, searchValue);
                    break;
                default:
                    return;
            }
            stockStatusPanel.updateTable(results);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "검색 중 오류가 발생했습니다: " + e.getMessage(), "검색 오류", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "창고번호는 숫자여야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}
