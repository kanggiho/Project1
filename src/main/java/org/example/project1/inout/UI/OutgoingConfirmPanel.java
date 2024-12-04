package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.OutputInfo2DAO;
import org.example.project1.inout.DAO.OutputInfoDAO;
import org.example.project1.inout.VO.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OutgoingConfirmPanel extends JPanel {
    private Connection conn;

    // Filter components
    private JComboBox<String> searchFilterComboBox;
    private JButton searchButton;
    private JRadioButton rejectedRadioBtn, approvedRadioBtn, pendingRadioBtn, allRadioBtn;
    private ButtonGroup statusButtonGroup;
    private JTextField searchField, productCode, confirmId;

    // Table and model
    private JTable dataTable;
    private DefaultTableModel tableModel;

    private OutputInfo2DAO outputInfo2DAO;
    private OutputInfoDAO outputInfoDAO;


    public OutgoingConfirmPanel() throws Exception {
        this.outputInfo2DAO = new OutputInfo2DAO();
        this.outputInfoDAO = new OutputInfoDAO();
        initializePanel();
        loadTableData("전체");
    }

    // ------------------ JPanel 초기화 ---------------------
    private void initializePanel() throws Exception {
        setSize(1100, 450);
        setLayout(null);
        setBackground(Color.white);
        initComponents();
    }

    // ------------------ UI 초기화 ---------------------
    private void initComponents() throws Exception {
        addHeaderLabel();
        addSearchFilterComponents();
        addDataTable();
        addBottomPanel();
    }

    private void addHeaderLabel() {
        JLabel headerLabel = new JLabel("발주 요청 내역 확인");
        headerLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        headerLabel.setBounds(20, 10, 200, 40);
        headerLabel.setBackground(Color.WHITE);
        add(headerLabel);
    }

    private JPanel filterPanel; // UI 전환을 위한 패널
    private CardLayout cardLayout; // 라디오 버튼과 텍스트 필드/버튼 전환

    private void addSearchFilterComponents() {
        // 콤보 박스 추가
        searchFilterComboBox = new JComboBox<>(new String[]{"승인 여부", "결재자"});
        searchFilterComboBox.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        searchFilterComboBox.setBounds(180, 15, 100, 30);
        searchFilterComboBox.addActionListener(e -> handleSearchFilterChange()); // 콤보 박스 선택에 따라 동작 변경
        add(searchFilterComboBox);

        // 필터 전환을 위한 CardLayout 패널 생성
        cardLayout = new CardLayout();
        filterPanel = new JPanel(cardLayout);
        filterPanel.setBounds(300, 15, 450, 30); // 라디오 버튼과 텍스트 필드가 들어갈 공간
        filterPanel.setBackground(Color.WHITE);

        // "승인 여부" 패널: 라디오 버튼들 추가
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioButtonPanel.setBackground(Color.WHITE);

        allRadioBtn = createRadioButton("전체", true, 0);
        approvedRadioBtn = createRadioButton("승인", false, 0);
        pendingRadioBtn = createRadioButton("대기중", false, 0);
        rejectedRadioBtn = createRadioButton("거절", false, 0);

        allRadioBtn.addActionListener(e -> handleRadioBtn("전체"));
        approvedRadioBtn.addActionListener(e -> handleRadioBtn("승인"));
        pendingRadioBtn.addActionListener(e -> handleRadioBtn("대기중"));
        rejectedRadioBtn.addActionListener(e -> handleRadioBtn("거절"));

        statusButtonGroup = new ButtonGroup();
        statusButtonGroup.add(allRadioBtn);
        statusButtonGroup.add(approvedRadioBtn);
        statusButtonGroup.add(pendingRadioBtn);
        statusButtonGroup.add(rejectedRadioBtn);

        radioButtonPanel.add(allRadioBtn);
        radioButtonPanel.add(approvedRadioBtn);
        radioButtonPanel.add(pendingRadioBtn);
        radioButtonPanel.add(rejectedRadioBtn);

        // "결재자" 패널: 텍스트 필드와 검색 버튼 추가
        JPanel searchFieldPanel = new JPanel();
        searchFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchFieldPanel.setBackground(Color.WHITE);

        searchField = createPlaceholderTextField("결재자 사번 입력");
        searchField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        searchButton = createButton("검색", e -> handleSearchButtonClick());

        searchFieldPanel.add(searchField);
        searchFieldPanel.add(searchButton);

        // CardLayout에 패널 추가
        filterPanel.add(radioButtonPanel, "승인 여부");
        filterPanel.add(searchFieldPanel, "결재자");

        add(filterPanel); // 메인 패널에 추가
    }

    private void handleSearchFilterChange() {
        // 콤보 박스 선택에 따라 CardLayout 전환
        String selectedOption = (String) searchFilterComboBox.getSelectedItem();
        System.out.println("Filter Changed: " + selectedOption); // 디버깅 로그

        if ("승인 여부".equals(selectedOption)) {
            cardLayout.show(filterPanel, "승인 여부");
            allRadioBtn.setSelected(true);
            loadTableData("전체");
        } else if ("결재자".equals(selectedOption)) {
            cardLayout.show(filterPanel, "결재자");
            searchField.setText("결재자 사번");
            searchField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (searchField.getForeground().equals(Color.GRAY)) {
                        searchField.setText("");
                        searchField.setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (searchField.getText().isEmpty()) {
                        searchField.setText("결재자 사번");
                        searchField.setForeground(Color.GRAY);
                    }
                }
            });
            clearTable();
        }
    }

    private JRadioButton createRadioButton(String label, boolean isSelected, int xPosition) {
        JRadioButton radioButton = new JRadioButton(label);
        radioButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        radioButton.setSelected(isSelected);
        radioButton.setBackground(Color.WHITE);

        radioButton.addActionListener(e -> loadTableData(label));
        return radioButton;
    }

    private JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder, 15);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getForeground().equals(Color.GRAY)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    private JButton createButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        button.addActionListener(listener);
        return button;
    }


    private void addDataTable() {
        String[] columnNames = {"자재코드", "자재명", "창고이름", "아이디", "주문자 이름",
                "단가", "발주량", "승인번호", "발주날짜", "결재자", "승인여부"};
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);
        dataTable.setRowHeight(20);
        dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataTable.getSelectionModel().addListSelectionListener(e -> handleTableSelection(e));
        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBounds(20, 60, 1060, 300);
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane);


    }

    private void addBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBounds(20, 380, 1060, 50);
        bottomPanel.setBackground(Color.WHITE);

        JLabel bottomLabel = new JLabel("발주 요청 승인");
        bottomLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));

        productCode = createPlaceholderTextField("자재 코드 입력");
        productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        confirmId = createPlaceholderTextField("결재자 사번");
        confirmId.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));

        JButton confirmButton = createButtonForConfirm("승인", e -> handleConfirmAction("승인"));
        JButton rejectButton = createButtonForConfirm("거절", e -> handleConfirmAction("거절"));
        JButton clearButton = createButtonForConfirm("초기화", e -> clearFields());

        bottomPanel.add(bottomLabel);
        bottomPanel.add(productCode);
        bottomPanel.add(confirmId);
        bottomPanel.add(confirmButton);
        bottomPanel.add(rejectButton);
        bottomPanel.add(clearButton);

        add(bottomPanel);
    }

    private JButton createButtonForConfirm(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        button.addActionListener(listener);
        return button;
    }

    // ------------------ 이벤트 핸들러 ---------------------
    // 검색 버튼 클릭시 처리
    private void handleSearchButtonClick() {
        System.out.println("버튼 클릭");
        String selectedOption = (String) searchFilterComboBox.getSelectedItem();
        try {
            DefaultTableModel model = new DefaultTableModel();
             if ("결재자".equals(selectedOption)) {
                handleConfirmSearch(model);
             }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "데이터 검색 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // 라디오버튼 클릭 시 처리
    private void handleRadioBtn(String filter) {
        System.out.println("Radio Button Selected: " + filter); // 디버깅 로그
        loadTableData(filter);
    }


    // 테이블 목록 선택 시 자재코드 textField 에 채움
    private void handleTableSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = dataTable.getSelectedRow();
            if (selectedRow != -1) {
                Object selected = tableModel.getValueAt(selectedRow, 0);
                String selectedProductCode = selected != null ? selected.toString() : "";
                productCode.setText(selectedProductCode);
                productCode.setForeground(Color.BLACK);
            }
        }
    }


    // 결재자 기준 검색
    private void handleConfirmSearch(DefaultTableModel model) throws Exception {
        String confirmInput = searchField.getText().trim();
        // 입력값 확인
        if (confirmInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "결재자 사번을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 숫자 확인
        int confirmID;
        try {
            confirmID = Integer.parseInt(confirmInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "결재자 사번은 숫자여야 합니다.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // DAO 호출 및 결과 처리
        ArrayList<OutputAdminVO> resultAdmin;
        try {
            resultAdmin = outputInfoDAO.listForConfirm(confirmID);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "데이터 검색 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        // 결과가 없는 경우 처리
        if (resultAdmin == null || resultAdmin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 테이블 데이터 업데이트
        String[] columnNames = {"결재자 사번", "승인 번호", "결재자명", "결재자 전화번호", "주문자명", "승인 여부", "출고일"};
        model.setColumnIdentifiers(columnNames);

        // 기존 데이터 삭제
        model.setRowCount(0);

        for (OutputAdminVO vo : resultAdmin) {
            Object[] row = {
                    vo.getConfirm_id(),
                    vo.getConfirm_num(),
                    vo.getName(),
                    vo.getTel(),
                    vo.getUser_id(),
                    vo.getStatus(),
                    vo.getRelease_date()
            };
            model.addRow(row);
        }
        // 테이블에 새 모델 적용
        dataTable.setModel(model);

        // 테이블 및 패널 재렌더링
        dataTable.revalidate();
        dataTable.repaint();
    }

    private void loadTableData(String filter) {
        try {
            ArrayList<?> data;
            if ("전체".equals(filter)) {
                data = outputInfo2DAO.getAllData();
            } else if ("승인".equals(filter)) {
                data = outputInfo2DAO.getApprove();
            } else if ("대기중".equals(filter)) {
                data = outputInfo2DAO.getPending();
            } else {
                data = outputInfo2DAO.getReject();
            }

            // 새로운 모델로 테이블 초기화
            String[] columnNames = {"자재코드", "자재명", "창고이름", "아이디", "주문자 이름",
                    "단가", "발주량", "승인번호", "발주날짜", "결재자", "승인여부"};
            tableModel = new DefaultTableModel(columnNames, 0);

            for (Object row : data) {
                tableModel.addRow(extractRowData(row));
            }

            // 테이블에 새 모델 적용
            dataTable.setModel(tableModel);

            // UI 재렌더링
            dataTable.revalidate();
            dataTable.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터 로드 실패: " + e.getMessage());
        }
    }


    private void updateTable(ArrayList<?> data) {
        tableModel.setRowCount(0); // Clear the table
        for (Object row : data) {
            tableModel.addRow(extractRowData(row));
        }
        dataTable.revalidate();
        dataTable.repaint();
    }

    private void clearTable() {
            tableModel.setRowCount(0); // 기존 데이터 제거
            dataTable.setModel(new DefaultTableModel()); // 새 모델로 테이블 초기화
            dataTable.revalidate();
            dataTable.repaint();
    }

    private Object[] extractRowData(Object data) {
        if (data instanceof OutputInfoProductWarehouseInfoOrdererVO vo) {
            return new Object[]{vo.getProduct_code(), vo.getProduct_name(), vo.getWarehouse_name(),
                    vo.getId(), vo.getOrderer_name(), vo.getUnit_price(), vo.getRelease_date(),
                    vo.getConfirm_num(), vo.getRelease_date(), vo.getConfirm_id(), vo.getStatus()};
        }
        return new Object[0];
    }

    private void handleConfirmAction(String status) {
        try {
            String productCodeInput = productCode.getText();
            String confirmIdInput = confirmId.getText();

            OutputInfoVO vo = new OutputInfoVO();
            ProductInfoVO vo2 = new ProductInfoVO();

            vo.setProduct_code(Integer.parseInt(productCodeInput));
            vo.setConfirm_id(Integer.parseInt(confirmIdInput));
            vo.setStatus(status);

            vo2.setProduct_code(Integer.parseInt(productCodeInput));
            outputInfoDAO.updateInventory(vo2);

            if ("승인".equals(status)) {
                outputInfoDAO.updateStatusForApprove(vo);
                outputInfoDAO.updateInventory(vo2);
            } else {
                outputInfoDAO.updateStatusForReject(vo);
            }

            JOptionPane.showMessageDialog(this, status + " 처리 완료");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "처리 실패: " + e.getMessage());
        }
    }

    private void clearFields() {
        productCode.setText("자재코드 입력");
        confirmId.setText("결재자 사번 입력");
    }
}
