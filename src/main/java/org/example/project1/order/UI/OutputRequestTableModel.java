// OutputRequestTableModel.java

package org.example.project1.order.UI;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import org.example.project1.order.VO.OutputRequestVO;

/**
 * OutputRequestVO 데이터를 표시하기 위한 테이블 모델 클래스입니다.
 */
public class OutputRequestTableModel extends AbstractTableModel {
    private final String[] columnNames = {
            "제품 코드", "제품명", "주문자명", "가격", "출고량", "출고 날짜"
    };
    private final List<OutputRequestVO> data;

    public OutputRequestTableModel() {
        this.data = new ArrayList<>();
    }

    public void addOutputRequest(OutputRequestVO request) {
        // 기존에 같은 제품 코드가 있는지 확인
        int existingRow = findRowByProductCode(request.getProduct_code());
        if (existingRow != -1) {
            // 기존 행의 출고량을 증가
            OutputRequestVO existingRequest = data.get(existingRow);
            existingRequest.setRelease_quantity(existingRequest.getRelease_quantity() + request.getRelease_quantity());
            fireTableRowsUpdated(existingRow, existingRow);
        } else {
            // 새로운 행 추가
            data.add(request);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }
    }

    public void removeRow(int row) {
        if (row >= 0 && row < data.size()) {
            data.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

    public void clearAll() {
        int size = data.size();
        if (size > 0) {
            data.clear();
            fireTableRowsDeleted(0, size - 1);
        }
    }

    public OutputRequestVO getOutputRequestAt(int row) {
        if (row >= 0 && row < data.size()) {
            return data.get(row);
        }
        return null;
    }

    // 모든 요청 목록 반환
    public List<OutputRequestVO> getAllRequests() {
        return new ArrayList<>(data);
    }

    // 제품 코드를 기준으로 행 찾기
    public int findRowByProductCode(int productCode) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getProduct_code() == productCode) {
                return i;
            }
        }
        return -1;
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
        OutputRequestVO vo = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return vo.getProduct_code();
            case 1: return vo.getProduct_name();
            case 2: return vo.getUser_name();
            case 3: return vo.getPrice();
            case 4: return vo.getRelease_quantity();
            case 5: return vo.getRelease_date();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
