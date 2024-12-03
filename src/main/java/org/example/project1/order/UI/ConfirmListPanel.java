package org.example.project1.order.UI;

import org.example.project1.order.DAO.OutputInfoDAO;
import org.example.project1.order.DAO.ProductInfoDAO;
import org.example.project1.order.VO.OutputInfoProductWarehouseInfoOrdererVO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class ConfirmListPanel extends JPanel {
    private String title;
    private String toss_font = "머니그라피TTF Rounded"; // Replace with a default font if necessary

    private Connection conn;

    // Current user name for filtering
    private String userName;

    // Filter components
    private JComboBox<String> userFilterComboBox;
    private JRadioButton rejectedRadioBtn;
    private JRadioButton approvedRadioBtn;
    private JRadioButton pendingRadioBtn;
    private JRadioButton allRadioBtn; // New "전체" radio button
    private ButtonGroup statusButtonGroup;

    ProductInfoDAO productInfoDAO;

    // Table and its model
    private JTable outputTable;
    private DefaultTableModel tableModel;

    public ConfirmListPanel(String title, String userName) {
        this.title = title;
        this.userName = userName;
        setPanel();
        initUI();

        try {
            String url = "jdbc:mysql://localhost:3306/project1";
            String user = "root";
            String password = "1234";
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }
            productInfoDAO = new ProductInfoDAO(conn); // 초기화
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadTableData(); // Initial data load
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.WHITE);
        setLayout(null); // Absolute layout
    }

    public void loadData(){
        loadTableData();
    }

    // UI 초기화
    private void initUI() {
        // 기존 UI 제거
        removeAll();

        // --------------------- 출고 요청 섹션 ---------------------
        JLabel outputRequestLabel = new JLabel("발주 내역 확인");
        outputRequestLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
        outputRequestLabel.setBounds(20, 20, 150, 40);
        add(outputRequestLabel);

        // --------------------- 사용자 필터 콤보박스 ---------------------
        String[] userOptions = {"전체", "본인"};
        userFilterComboBox = new JComboBox<>(userOptions);
        userFilterComboBox.setFont(new Font(toss_font, Font.PLAIN, 14));
        userFilterComboBox.setBounds(180, 25, 100, 30);
        add(userFilterComboBox);

        // --------------------- 승인 상태 필터 라디오 버튼 ---------------------
        rejectedRadioBtn = new JRadioButton("거절");
        approvedRadioBtn = new JRadioButton("승인");
        pendingRadioBtn = new JRadioButton("대기중");
        allRadioBtn = new JRadioButton("전체"); // New radio button
        allRadioBtn.setSelected(true); // Default selection

        rejectedRadioBtn.setFont(new Font(toss_font, Font.PLAIN, 14));
        approvedRadioBtn.setFont(new Font(toss_font, Font.PLAIN, 14));
        pendingRadioBtn.setFont(new Font(toss_font, Font.PLAIN, 14));
        allRadioBtn.setFont(new Font(toss_font, Font.PLAIN, 14));

        // Group the radio buttons
        statusButtonGroup = new ButtonGroup();
        statusButtonGroup.add(rejectedRadioBtn);
        statusButtonGroup.add(approvedRadioBtn);
        statusButtonGroup.add(pendingRadioBtn);
        statusButtonGroup.add(allRadioBtn);

        // Position the radio buttons
        allRadioBtn.setBounds(300, 25, 60, 30); // Position the new button
        approvedRadioBtn.setBounds(370, 25, 60, 30);
        pendingRadioBtn.setBounds(440, 25, 70, 30);
        rejectedRadioBtn.setBounds(530, 25, 60, 30);

        allRadioBtn.setBackground(Color.WHITE);
        approvedRadioBtn.setBackground(Color.WHITE);
        pendingRadioBtn.setBackground(Color.WHITE);
        rejectedRadioBtn.setBackground(Color.WHITE);


        add(allRadioBtn);
        add(approvedRadioBtn);
        add(pendingRadioBtn);
        add(rejectedRadioBtn);

        // --------------------- 필터 변경 시 테이블 데이터 로드 ---------------------
        ActionListener filterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        };

        userFilterComboBox.addActionListener(filterListener);
        rejectedRadioBtn.addActionListener(filterListener);
        approvedRadioBtn.addActionListener(filterListener);
        pendingRadioBtn.addActionListener(filterListener);
        allRadioBtn.addActionListener(filterListener);

        // --------------------- 버튼 추가 ---------------------
        JButton deleteButton = new JButton("취소");
        deleteButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteButton.setBackground(new Color(255, 182, 193));
        //deleteButton.setBorder(new LineBorder(Color.BLACK, 2));
        deleteButton.setBounds(780,400 , 110, 40);
        add(deleteButton);

        JButton deleteAllButton = new JButton("전체취소");
        deleteAllButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteAllButton.setBackground(new Color(255, 182, 193));
        //deleteAllButton.setBorder(new LineBorder(Color.BLACK, 2));
        deleteAllButton.setBounds(900, 400, 110, 40);
        add(deleteAllButton);

        // --------------------- 출고 요청 테이블 추가 ---------------------
        outputTable = createOutputTable();  // 테이블 생성
        outputTable.setRowHeight(20);
        outputTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane outputScrollPane = new JScrollPane(outputTable);
        outputScrollPane.setBounds(20, 60, 1060, 300);
        add(outputScrollPane);

        // --------------------- 버튼 액션 이벤트 ---------------------
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRow();
            }
        });

        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAllPendingRows();
            }
        });

        // UI 갱신
        revalidate();
        repaint();
    }

    // 출고 요청 테이블 생성
    private JTable createOutputTable() {
        // Table column names
        String[] columnNames = {
                "자재코드", "자재명", "창고이름", "아이디", "주문자 이름",
                "단가", "발주량", "승인번호", "발주신청날짜", "결재자", "승인여부"
        };

        // Initialize table model with column names and no data
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };

        return new JTable(tableModel);
    }

    // 테이블 데이터 로드 및 필터 적용
    private void loadTableData() {
        try {
            // DB 연결 정보
            String url = "jdbc:mysql://localhost:3306/project1";
            String user = "root";
            String password = "1234";
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
                //conn.setAutoCommit(false);
            }

            OutputInfoDAO outputInfoDAO = new OutputInfoDAO(conn);

            // Determine user filter
            String userFilter = "";
            String selectedUserOption = (String) userFilterComboBox.getSelectedItem();
            if ("본인".equals(selectedUserOption)) {
                userFilter = "user"; // Indicates to filter by userName
            } else {
                userFilter = "all";
            }

            // Determine status filter
            String statusFilter = "";
            if (allRadioBtn.isSelected()) {
                statusFilter = ""; // 전체 데이터 필터링 없음
            } else if (rejectedRadioBtn.isSelected()) {
                statusFilter = "거절";
            } else if (approvedRadioBtn.isSelected()) {
                statusFilter = "승인";
            } else if (pendingRadioBtn.isSelected()) {
                statusFilter = "대기중";
            }

            // 실제 데이터 로드 with filters
            List<OutputInfoProductWarehouseInfoOrdererVO> outputList =
                    outputInfoDAO.getFilteredOutputInfo(statusFilter, userFilter, userName);

            // Clear existing data
            tableModel.setRowCount(0);

            // Populate table model with filtered data
            for (OutputInfoProductWarehouseInfoOrdererVO vo : outputList) {
                Object[] rowData = {
                        vo.getProduct_code(),
                        vo.getProduct_name(),
                        vo.getWarehouse_name(),
                        vo.getId(),
                        vo.getOrderer_name(),
                        vo.getUnit_price(),
                        vo.getRelease_quantity(),
                        vo.getConfirm_num(),
                        vo.getRelease_date(),
                        vo.getConfirm_id(),
                        vo.getStatus()
                };
                tableModel.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터 로드 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 선택된 행 삭제
    private void deleteSelectedRow() {
        int selectedRow = outputTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String status = (String) tableModel.getValueAt(selectedRow, 10); // 승인여부 컬럼
        if (!"대기중".equals(status)) {
            JOptionPane.showMessageDialog(this, "대기중인 행만 삭제할 수 있습니다.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmNum = (int) tableModel.getValueAt(selectedRow, 7); // 승인번호 컬럼
        int product_code = (int) tableModel.getValueAt(selectedRow, 0); // 승인번호 컬럼
        int stock = (int) tableModel.getValueAt(selectedRow, 6); //  컬럼

        int confirm = JOptionPane.showConfirmDialog(this, "선택한 행을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OutputInfoDAO outputInfoDAO = new OutputInfoDAO(conn);
            boolean success = outputInfoDAO.deleteOutputInfo(confirmNum);
            if (success) {
                int temp_stock = productInfoDAO.one(product_code).getStock();
                System.out.println(stock);

                productInfoDAO.updateProductStock(product_code, temp_stock + stock);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "선택한 행이 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "삭제에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 모든 대기중 행 삭제
    private void deleteAllPendingRows() {
        int confirm = JOptionPane.showConfirmDialog(this, "모든 대기중인 행을 삭제하시겠습니까?", "전체 삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            OutputInfoDAO outputInfoDAO = new OutputInfoDAO(conn);
            boolean success = outputInfoDAO.deleteAllPendingOutputInfo();
            if (success) {
                loadTableData(); // Reload table data after deletion
                JOptionPane.showMessageDialog(this, "모든 대기중인 행이 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "전체 삭제에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "전체 삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

//    // 메모리 정리를 위한 연결 종료
//    @Override
//    public void removeNotify() {
//        super.removeNotify();
//        try {
//            if (conn != null && !conn.isClosed()) {
//                conn.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
