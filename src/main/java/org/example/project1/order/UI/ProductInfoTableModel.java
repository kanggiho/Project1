package org.example.project1.order.UI;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import org.example.project1.order.VO.OutputRequestVO;
import org.example.project1.order.VO.ProductInfoProductVO;

public class ProductInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "코드", "제품 코드", "제품명", "제조사 코드", "창고 ID", "가격", "재고", "재고 날짜"
    };

    private final Class<?>[] columnClasses = {
            String.class, Integer.class, String.class, String.class, Integer.class, Integer.class, Integer.class, String.class
    };

    private List<ProductInfoProductVO> products;

    public ProductInfoTableModel(List<ProductInfoProductVO> products) {
        this.products = products;
    }

    // 데이터 갱신을 위한 메서드
    public void setData(List<ProductInfoProductVO> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products != null ? products.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnNames.length) {
            return columnNames[columnIndex];
        }
        return super.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= 0 && columnIndex < columnClasses.length) {
            return columnClasses[columnIndex];
        }
        return Object.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductInfoProductVO product = products.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return product.getCode();
            case 1:
                return product.getProduct_code();
            case 2:
                return product.getProduct_name();
            case 3:
                return product.getManufacturer_code();
            case 4:
                return product.getWarehouse_id();
            case 5:
                return product.getPrice();
            case 6:
                return product.getStock();
            case 7:
                return product.getStock_date();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 모든 셀은 편집 불가능
        return false;
    }

    // 특정 행의 ProductInfoProductVO 객체를 반환하는 메서드
    public ProductInfoProductVO getProductAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < products.size()) {
            return products.get(rowIndex);
        }
        return null;
    }

    // 제품 코드로 제품 검색 (필요에 따라 사용)
    public ProductInfoProductVO getProductByProductCode(int productCode) {
        for (ProductInfoProductVO product : products) {
            if (product.getProduct_code() == productCode) {
                return product;
            }
        }
        return null;
    }
    // 모든 출고 요청 가져오기
    public List<ProductInfoProductVO> getAllRequests() {
        return new ArrayList<>(products);
    }

}
