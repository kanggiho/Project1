package org.example.project1.order.TableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import org.example.project1.order.VO.OutputRequestVO;

public class OutputRequestTableModel extends AbstractTableModel {

    private final String[] columnNames = {
            "제품 코드", "제품명", "사용자명", "단가", "출고 수량", "출고 날짜"
    };

    private final Class<?>[] columnClasses = {
            Integer.class, String.class, String.class, Integer.class, Integer.class, String.class
    };

    private List<OutputRequestVO> requests;

    public OutputRequestTableModel() {
        this.requests = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return requests.size();
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
        OutputRequestVO request = requests.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return request.getProduct_code();
            case 1:
                return request.getProduct_name();
            case 2:
                return request.getUser_name();
            case 3:
                return request.getPrice();
            case 4:
                return request.getRelease_quantity();
            case 5:
                return request.getRelease_date();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // '출고 수량' (컬럼 인덱스 4)만 수정 가능하도록 설정
        return columnIndex == 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OutputRequestVO request = requests.get(rowIndex);
        if (columnIndex == 4) { // '출고 수량' 수정
            try {
                int newQuantity = Integer.parseInt(aValue.toString());
                if (newQuantity <= 0) {
                    throw new NumberFormatException("출고 수량은 0보다 커야 합니다.");
                }
                request.setRelease_quantity(newQuantity);
                fireTableCellUpdated(rowIndex, columnIndex);
            } catch (NumberFormatException e) {
                // 잘못된 입력 처리
                JOptionPane.showMessageDialog(null, "유효한 출고 수량을 입력해주세요.");
            }
        }
    }

    // 출고 요청 추가 메서드
    public void addOutputRequest(OutputRequestVO request) {
        requests.add(request);
        int row = requests.size() - 1;
        fireTableRowsInserted(row, row);
    }

    // 특정 행의 출고 요청 가져오기
    public OutputRequestVO getOutputRequestAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < requests.size()) {
            return requests.get(rowIndex);
        }
        return null;
    }

    // 특정 행 삭제 메서드
    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < requests.size()) {
            requests.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    // 모든 행 삭제 메서드
    public void clearAll() {
        int size = requests.size();
        if (size > 0) {
            requests.clear();
            fireTableRowsDeleted(0, size - 1);
        }
    }

    // 모든 출고 요청 가져오기
    public List<OutputRequestVO> getAllRequests() {
        return new ArrayList<>(requests);
    }
}
