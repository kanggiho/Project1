package org.example.project1.inventory.UI;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * 재고 검색을 위한 패널 클래스
 */
public class StockSearchPanel extends JPanel {
    /** 검색 유형 상수 배열 */
    private static final String[] SEARCH_TYPES = {"자재명", "창고ID", "제조업체명"};

    /** 검색 유형 선택을 위한 콤보박스 */
    private final JComboBox<String> searchTypeComboBox;
    /** 검색어 입력을 위한 텍스트 필드 */
    private final JTextField searchField;
    /** 데이터베이스 접근을 위한 DAO 객체 */
    private final ProductInfoDAO productInfoDAO;
    /** 검색 결과를 표시할 StockStatusPanel 객체 */
    private final StockStatusPanel stockStatusPanel;

    /**
     * StockSearchPanel 생성자
     * @param stockStatusPanel 검색 결과를 표시할 StockStatusPanel 객체
     */
    public StockSearchPanel(StockStatusPanel stockStatusPanel) {
        this.productInfoDAO = new ProductInfoDAO();
        this.stockStatusPanel = stockStatusPanel;
        this.searchTypeComboBox = new JComboBox<>(SEARCH_TYPES);
        this.searchField = new JTextField(15);

        initUI();
    }

    /**
     * UI 초기화 메소드
     */
    private void initUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(e -> performSearch());

        add(new JLabel("검색 유형:"));
        add(searchTypeComboBox);
        add(new JLabel("검색어:"));
        add(searchField);
        add(searchButton);
    }

    /**
     * 검색 수행 메소드
     */
    private void performSearch() {
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

    /**
     * 검색 유형에 따른 재고 검색 메소드
     * @param searchType 검색 유형
     * @param searchValue 검색어
     * @return 검색 결과 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    private List<ProductInfoProductWarehouseInfoManufacturingVO> searchInventory(String searchType, String searchValue) throws SQLException {
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

    /**
     * 에러 메시지 표시 메소드
     * @param message 에러 메시지
     * @param title 에러 창 제목
     */
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}