package org.example.project1.order.TableModel;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.List;
import org.example.project1.order.VO.OutputInfoVO;

public class OutputInfoTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "제품 코드", "창고 ID", "사용자 ID", "확인 번호", "확인 ID", "상태", "단가", "출고 수량", "출고 날짜"
    };

    private final Class<?>[] columnClasses = {
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, String.class, Integer.class, Integer.class, LocalDate.class
    };

    private List<OutputInfoVO> outputInfos;

    public OutputInfoTableModel(List<OutputInfoVO> outputInfos) {
        this.outputInfos = outputInfos;
    }

    @Override
    public int getRowCount() {
        return outputInfos.size();
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
        OutputInfoVO vo = outputInfos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return vo.getProduct_code();
            case 1:
                return vo.getWarehouse_id();
            case 2:
                return vo.getUser_id();
            case 3:
                return vo.getConfirm_num();
            case 4:
                return vo.getConfirm_id();
            case 5:
                return vo.getStatus();
            case 6:
                return vo.getUnit_price();
            case 7:
                return vo.getRelease_quantity();
            case 8:
                return vo.getRelease_date();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 필요한 경우 편집 가능하도록 설정
        return false;
    }

    // 데이터 추가, 삭제, 갱신 메서드 추가
    public void addOutputInfo(OutputInfoVO vo) {
        outputInfos.add(vo);
        fireTableRowsInserted(outputInfos.size() - 1, outputInfos.size() - 1);
    }

    public void removeOutputInfo(int rowIndex) {
        outputInfos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void setOutputInfos(List<OutputInfoVO> outputInfos) {
        this.outputInfos = outputInfos;
        fireTableDataChanged();
    }

    public OutputInfoVO getOutputInfoAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < outputInfos.size()) {
            return outputInfos.get(rowIndex);
        }
        return null;
    }

    public List<OutputInfoVO> getAllOutputInfos() {
        return outputInfos;
    }
}
