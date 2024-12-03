package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.OutputInfo2DAO;
import org.example.project1.inout.DAO.OutputInfoDAO;
import org.example.project1.inout.VO.OutputAdminVO;
import org.example.project1.inout.VO.OutputOrdererVO;
import org.example.project1.inout.DAO.ProductInfoDAO;
import org.example.project1.inout.VO.ProductInfoVO;
import org.example.project1.inout.VO.OutputInfoProductWarehouseInfoOrdererVO;
import org.example.project1.inout.VO.OutputInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class OutgoingConfirmPanel extends JPanel {
    private Connection conn;

    // Filter components
    private JComboBox<String> searchFilterComboBox;
    private JRadioButton rejectedRadioBtn;
    private JRadioButton approvedRadioBtn;
    private JRadioButton pendingRadioBtn;
    private JRadioButton allRadioBtn; // New "전체" radio button
    private ButtonGroup statusButtonGroup;

    // Table and its model
    private JTable outputTable;
    private DefaultTableModel tableModel;

    ProductInfoDAO productInfoDAO = new ProductInfoDAO(conn);
    OutputInfo2DAO outputInfo2DAO = new OutputInfo2DAO(conn);

    public OutgoingConfirmPanel() throws Exception {
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
            System.out.println(e.getMessage());
        }
        loadTableData(); // Initial data load
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setLayout(null);
        setVisible(true);
    }

    public void loadData() {
        loadTableData();
    }

    // UI 초기화
    private void initUI() throws Exception {
        // ------------------ 출고 승인 내역 조회 ---------------------
        // 기존 UI 제거
        removeAll();

        // --------------------- 제목 라벨 ---------------------
        JLabel outputRequestLabel = new JLabel("발주 요청 내역 확인");
        outputRequestLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        outputRequestLabel.setBounds(20, 20, 150, 40);
        add(outputRequestLabel);

        // --------------------- 검색 조건 콤보박스 ---------------------
        String[] userOptions = {"승인 여부", "결재자"};
        searchFilterComboBox = new JComboBox<>(userOptions);
        searchFilterComboBox.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        searchFilterComboBox.setBounds(180, 25, 100, 30);

        JTextField searchField = new JTextField();
        searchField = new JTextField();
        searchField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        searchField.setBounds(220, 25, 120, 30);
        searchField.setVisible(false); // 초기에는 숨김 상태
        add(searchField);

        searchFilterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) searchFilterComboBox.getSelectedItem();
                if ("승인 여부".equals(selectedOption)) {
                    // --------------------- 승인 상태 필터 라디오 버튼 ---------------------
                    rejectedRadioBtn = new JRadioButton("거절");
                    approvedRadioBtn = new JRadioButton("승인");
                    pendingRadioBtn = new JRadioButton("대기중");
                    allRadioBtn = new JRadioButton("전체");
                    allRadioBtn.setSelected(true); // Default selection

                    rejectedRadioBtn.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
                    approvedRadioBtn.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
                    pendingRadioBtn.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
                    allRadioBtn.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));

                    // Group the radio buttons
                    statusButtonGroup = new ButtonGroup();
                    statusButtonGroup.add(rejectedRadioBtn);
                    statusButtonGroup.add(approvedRadioBtn);
                    statusButtonGroup.add(pendingRadioBtn);
                    statusButtonGroup.add(allRadioBtn);

                    // Position the radio buttons
                    allRadioBtn.setBounds(300, 25, 60, 30);
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
                }
                if ("결재자".equals(selectedOption)) {
                    searchField.setVisible(true); // "결재자" 선택 시 검색창 표시
                } else {
                    searchField.setVisible(false); // 다른 옵션 선택 시 검색창 숨김
                }
                revalidate();
                repaint();
            }
        });

        add(searchFilterComboBox);


        // --------------------- 필터 변경 시 테이블 데이터 로드 ---------------------
        ActionListener filterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableData();
            }
        };

        searchFilterComboBox.addActionListener(filterListener);
        rejectedRadioBtn.addActionListener(filterListener);
        approvedRadioBtn.addActionListener(filterListener);
        pendingRadioBtn.addActionListener(filterListener);
        allRadioBtn.addActionListener(filterListener);


        // --------------------- 출고 요청 테이블 추가 ---------------------
        outputTable = createOutputTable();  // 테이블 생성
        outputTable.setRowHeight(20);
        outputTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane outputScrollPane = new JScrollPane();
        outputScrollPane.setBounds(20, 60, 1060, 200);
        add(outputScrollPane);

        // UI 갱신
        revalidate();
        repaint();

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

                OutputInfo2DAO outputInfoDAO = new OutputInfo2DAO(conn);

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
                        outputInfo2DAO.getFilteredOutputInfo(statusFilter, userFilter, userName);

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


        // ------------------ 출고 승인, 거절 ---------------------
        // 자재 코드 입력창
        JLabel productCodelabel = new JLabel();

        // 결재자 사번 입력창
         confirmId = new JTextField(10);

        confirmId.setText("결재자 사번 입력");
        confirmId.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
        confirmId.setForeground(Color.GRAY);

        confirmId.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmId.getForeground().equals(Color.GRAY) && confirmId.getText().equals("결재자 사번 입력")) {
                    confirmId.setText("");
                    confirmId.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    confirmId.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (confirmId.getText().isEmpty()) {
                    confirmId.setText("결재자 사번 입력");
                    confirmId.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    confirmId.setForeground(Color.GRAY);
                }
            }
        });

        // 승인, 거절, 초기화 버튼 생성
        JButton confirmButton = new JButton("승인");
        confirmButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
        confirmButton.addActionListener(e -> handleConfirmButton());

        JButton rejectButton = new JButton("거절");
        rejectButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
        rejectButton.addActionListener(e -> handleRejectButton());

        JButton clearButton = new JButton("초기화");
        clearButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // 출고 요청 승인용 패널 생성
        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setLayout(new BorderLayout());
        bottomPanel1.setBounds(550, 200, 550, 250);

        // 1. 라벨 패널 (왼쪽 위 정렬)
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setBackground(Color.white);
        JLabel labelForConfirm = new JLabel("출고 요청 승인/거절");
        labelForConfirm.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 20));
        labelForConfirm.setForeground(Color.BLACK);
        labelPanel.add(labelForConfirm);

        // 2. 텍스트 필드 패널 (세로 가운데 정렬)
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setBackground(Color.WHITE);

        productCode.setMaximumSize(new Dimension(400, 30));
        confirmId.setMaximumSize(new Dimension(400, 30));

        fieldPanel.add(Box.createVerticalStrut(50));
        fieldPanel.add(productCode);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(confirmId);

        // 3. 버튼 패널 (가로 가운데 정렬)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.white);
        buttonPanel.add(confirmButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(clearButton);

        // 4. bottomPanel1에 각 패널 추가
        bottomPanel1.add(labelPanel, BorderLayout.NORTH);
        bottomPanel1.add(fieldPanel, BorderLayout.CENTER);
        bottomPanel1.add(buttonPanel, BorderLayout.SOUTH);

        // 5. 프레임에 bottomPanel1 추가
        add(bottomPanel1);
    }


    // 모드 선택시 입력 필드 초기화
    private void handleModeSelectorAction() {
        String selectMode = (String) modeSelector.getSelectedItem();
        if ("주문자명".equals(selectMode)) {
            inputField.setText("주문자 아이디");
            inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        } else if ("승인 여부".equals(selectMode)) {
            inputField.setText("승인 여부(승인/거절/대기중)");
            inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        } else if ("결재자".equals(selectMode)) {
            inputField.setText("결재자 사번");
            inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        }
        inputField.setForeground(Color.GRAY);
    }

    // 검색 버튼 클릭시 처리
    private void handleSearchButtonClick() {
        System.out.println("버튼 클릭");

        String selectMode = (String) modeSelector.getSelectedItem();
        try {
            DefaultTableModel model = new DefaultTableModel();
            if ("주문자명".equals(selectMode)) {
                handleOrdererSearch(model);
            } else if ("승인 여부".equals(selectMode)) {
                handleStatusSearch(model);
            } else if ("결재자".equals(selectMode)) {
                handleConfirmSearch(model);
            }

            // 테이블 모델 업데이트
            resultTableForSearch.setModel(model);
            revalidate();  // 레이아웃 갱신
            repaint();     // 화면 갱신

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "데이터 검색 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // 결재자 기준 검색
    private void handleConfirmSearch(DefaultTableModel model) throws Exception {
        String confirmInput = inputField.getText().trim();
        if (confirmInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "결재자 사번을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmID = Integer.parseInt(confirmInput);
        ArrayList<OutputAdminVO> resultAdmin = outputInfoDAO.listForConfirm(confirmID);

        if (resultAdmin == null || resultAdmin.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"결재자 사번", "승인 번호", "결재자명", "결재자 전화번호", "주문자명", "승인 여부", "출고일"};
        model.setColumnIdentifiers(columnNames);
        for (OutputAdminVO vo : resultAdmin) {
            Object[] row = {vo.getConfirm_id(), vo.getConfirm_num(), vo.getName(), vo.getTel(),
                    vo.getUser_id(), vo.getStatus(), vo.getRelease_date()};
            model.addRow(row);
        }
    }


    // ------------------------ 승인 및 거절 버튼 기능 ------------------------------
    // 승인 버튼 기능 (output 테이블 update, product_info 테이블 update)
    OutputInfoVO vo = new OutputInfoVO();
    ProductInfoVO vo2 = new ProductInfoVO()
            ;
    private void handleConfirmButton() {
        // output 테이블 update
        String productCodeInput = productCode.getText();
        String confirmIdInput = confirmId.getText();
        System.out.println("텍스트 추출 완료");

        // '승인' 으로 상태 업데이트
        vo.setStatus("승인");
        vo.setProduct_code(Integer.parseInt(productCodeInput));
        vo.setConfirm_id(Integer.parseInt(confirmIdInput));

        outputInfoDAO.updateStatusForApprove(vo);

        // 재고 수량 업데이트
        vo2.setProduct_code(Integer.parseInt(productCodeInput));
        outputInfoDAO.updateInventory(vo2);
    }


    // 거절 버튼 기능 (output 테이블 update)
    private void handleRejectButton() {
        String productCodeInput = productCode.getText();
        String confirmIdInput = confirmId.getText();
        System.out.println("텍스트 추출 완료");

        vo.setStatus("거절");
        vo.setProduct_code(Integer.parseInt(productCodeInput));
        vo.setConfirm_id(Integer.parseInt(confirmIdInput));

        outputInfoDAO.updateStatusForReject(vo);
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
}