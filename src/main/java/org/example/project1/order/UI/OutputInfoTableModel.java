package org.example.project1.order.UI;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import org.example.project1.order.VO.OutputInfoVO;

/**
 * OutputInfoVO 데이터를 표시하기 위한 테이블 모델 클래스입니다.
 */
public class OutputInfoTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "자재코드", "창고번호", "주문자명", "승인번호", "결재자", "승인 여부", "단가", "출고량", "출고 날짜"
    };
    private final List<OutputInfoVO> data;

    public OutputInfoTableModel(List<OutputInfoVO> data) {
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
        OutputInfoVO vo = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return vo.getProduct_code();
            case 1: return vo.getWarehouse_id();
            case 2: return vo.getUser_id();
            case 3: return vo.getConfirm_num();
            case 4: return vo.getConfirm_id();
            case 5: return vo.getStatus();
            case 6: return vo.getUnit_price();
            case 7: return vo.getRelease_quantity();
            case 8: return vo.getRelease_date();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
