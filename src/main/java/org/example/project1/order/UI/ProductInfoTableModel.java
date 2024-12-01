package org.example.project1.order.UI;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import org.example.project1.order.VO.ProductInfoProductVO;

/**
 * ProductInfoVO 데이터를 표시하기 위한 테이블 모델 클래스입니다.
 */
public class ProductInfoTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "코드", "제품 코드", "제품명", "제조업체 코드", "창고 ID", "가격", "재고", "입고 예정일"
    };
    private final List<ProductInfoProductVO> data;

    public ProductInfoTableModel(List<ProductInfoProductVO> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductInfoProductVO vo = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return vo.getCode();
            case 1: return vo.getProduct_code();
            case 2: return vo.getProduct_name();
            case 3: return vo.getManufacturer_code();
            case 4: return vo.getWarehouse_id();
            case 5: return vo.getPrice();
            case 6: return vo.getStock();
            case 7: return vo.getStock_date();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
