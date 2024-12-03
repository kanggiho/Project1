package org.example.project1.inventory.UI;
import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
/**
 * 재고 상태를 표시하는 패널 클래스
 */
public class StockStatusPanel extends JPanel {
    // 테이블 열 이름 상수
    private static final String[] COLUMN_NAMES = {"코드", "제품 코드", "제품명", "제조업체 코드", "제조업체명", "창고 ID", "창고 위치", "가격", "재고", "입고 예정일"};
    // 컴포넌트
    private final JTable stockTable;
    private final DefaultTableModel tableModel;
    // 데이터 접근 객체
    private final ProductInfoDAO productInfoDAO;
    private Font tossFont = new Font("머니그라피TTF Rounded", Font.PLAIN, 12);
    /**
     * StockStatusPanel 생성자
     */
    public StockStatusPanel() {
        this.productInfoDAO = new ProductInfoDAO();
        this.tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
        this.stockTable = new JTable(tableModel);
        initUI();
        loadStockData();

    }

    // 폰트 적용
    private void applyTossFont() {
        stockTable.setFont(tossFont);
        stockTable.getTableHeader().setFont(tossFont);
        // 다른 컴포넌트들에도 폰트 적용
        for (Component comp : this.getComponents()) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).setFont(tossFont);
            }
        }
    }
    @Override
    public Component add(Component comp) {
        if (comp instanceof JComponent) {
            ((JComponent) comp).setFont(tossFont);
        }
        return super.add(comp);
    }



    /**
     * UI 초기화 메소드
     */
    private void initUI() {
        setLayout(new BorderLayout());
        add(createStockTablePanel(), BorderLayout.CENTER);
        setupTableSelectionListener();
    }
    /**
     * 재고 테이블 패널 생성 메소드
     */
    private JPanel createStockTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        return panel;
    }
    /**
     * 테이블 선택 리스너 설정 메소드
     */
    private void setupTableSelectionListener() {
        stockTable.getSelectionModel().addListSelectionListener(e -> {
            boolean rowSelected = stockTable.getSelectedRow() != -1;
            firePropertyChange("rowSelected", !rowSelected, rowSelected);
        });
    }
    /**
     * 재고 데이터를 로드하고 테이블에 표시하는 메소드
     */
    public void loadStockData() {
        SwingUtilities.invokeLater(() -> {
            try {
                List<ProductInfoProductWarehouseInfoManufacturingVO> inventoryList = productInfoDAO.getInventoryStatusWithManufacturer();
                updateTable(inventoryList);
            } catch (SQLException e) {
                showErrorMessage("데이터 로딩 중 오류 발생: " + e.getMessage());
            }
        });
    }
    /**
     * ProductInfoProductWarehouseInfoManufacturingVO 리스트로 테이블을 업데이트하는 메소드
     */
    public void updateTable(List<ProductInfoProductWarehouseInfoManufacturingVO> results) {
        tableModel.setRowCount(0);
        for (ProductInfoProductWarehouseInfoManufacturingVO vo : results) {
            tableModel.addRow(createRowData(vo));
        }
        stockTable.repaint();
    }
    /**
     * VO 객체에서 테이블 행 데이터를 생성하는 메소드
     */
    private Object[] createRowData(ProductInfoProductWarehouseInfoManufacturingVO vo) {
        return new Object[]{vo.getCode(), vo.getProduct_code(), vo.getProduct_name(), vo.getManufacturer_code(), vo.getManufacturer_name(), vo.getWarehouse_id(), vo.getWarehouse_location(), vo.getPrice(), vo.getStock(), vo.getStock_date()};
    }
    /**
     * 현재 선택된 제품의 정보를 반환하는 메소드
     */
    public ProductInfoProductVO getSelectedProduct() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow != -1) {
            return new ProductInfoProductVO(
                    (String) tableModel.getValueAt(selectedRow, 0),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 1).toString()),
                    (String) tableModel.getValueAt(selectedRow, 2),
                    (String) tableModel.getValueAt(selectedRow, 3),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 5).toString()),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 7).toString()),
                    Integer.parseInt(tableModel.getValueAt(selectedRow, 8).toString()),
                    (String) tableModel.getValueAt(selectedRow, 9)
            );
        }
        return null;
    }
    /**
     * 행 선택 리스너 추가 메소드
     */
    public void addRowSelectionListener(ListSelectionListener listener) {
        stockTable.getSelectionModel().addListSelectionListener(listener);
    }
    /**
     * 오류 메시지를 표시하는 메소드
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
        // 에러 메시지 다이얼로그에도 폰트 적용
        UIManager.put("OptionPane.messageFont", tossFont);
        UIManager.put("OptionPane.buttonFont", tossFont);
    }
}