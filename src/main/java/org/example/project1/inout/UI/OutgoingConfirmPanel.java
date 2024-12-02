package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.OutputInfoDAO;
import org.example.project1.inout.VO.OutputAdminVO;
import org.example.project1.inout.VO.OutputOrdererVO;
import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoVO;
import org.example.project1.order.VO.OutputInfoVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OutgoingConfirmPanel extends JPanel {
    private JTextField inputField;
    private JComboBox<String> modeSelector;
    private JTable resultTableForStock;
    private JTable resultTableForSearch;
    private OutputInfoDAO outputInfoDAO = new OutputInfoDAO();
    private ProductInfoDAO productInfoDAO = new ProductInfoDAO();

    public OutgoingConfirmPanel() throws Exception {
        setPanel();
        initUI();
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.WHITE);
        setLayout(null);
        setVisible(true);
    }

    // UI 초기화
    private void initUI() throws Exception {
        inputField = new JTextField(20);

        // ------------------ 재고 현황 조회 ---------------------
        JLabel labelForStock = createLabel("재고 현황 조회", 14, Color.BLACK, SwingConstants.CENTER);

        JButton buttonForNew = new JButton("새로 고침");
        buttonForNew.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        buttonForNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultTableForStock = createTable(new String[] {"분류코드", "자재코드", "제조업체 코드", "창고 번호", "단가", "재고 수량", "입고 예정일"});
                    JOptionPane.showMessageDialog(null,"새로 고침 되었습니다.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "새로 고침 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(Color.white);
        topPanel.add(labelForStock);
        topPanel.add(buttonForNew);
        topPanel.setBounds(0, 0, 1100, 30);
        add(topPanel);

        resultTableForStock = createTable(new String[] {"분류코드", "자재코드", "제조업체 코드", "창고 번호", "단가", "재고 수량", "입고 예정일"});

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBounds(0, 30, 1100, 170);
        middlePanel.setBackground(Color.white);
        middlePanel.add(labelForStock, BorderLayout.NORTH);
        middlePanel.add(new JScrollPane(resultTableForStock), BorderLayout.CENTER);
        add(middlePanel);

        // ------------------ 출고 승인, 거절 ---------------------
        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel1.setBackground(Color.WHITE);
        bottomPanel1.setBounds(0, 200, 550, 250);
        add(bottomPanel1);

        // ------------------ 출고 승인 내역 조회 ---------------------
        String[] modes = {"주문자명", "승인 여부", "결재자"};
        modeSelector = new JComboBox<>(modes);
        modeSelector.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));

        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getForeground().equals(Color.GRAY)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                handleFocusLost();
            }
        });

        modeSelector.addActionListener(e -> handleModeSelectorAction());

        // '검색' 버튼 기능
        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        searchButton.addActionListener(e -> handleSearchButtonClick());

        // 패널에 검색창 추가
        JPanel bottomSearchPanel = new JPanel();
        bottomSearchPanel.add(modeSelector);
        bottomSearchPanel.add(inputField);
        bottomSearchPanel.add(searchButton);
        bottomSearchPanel.setBackground(Color.WHITE);
        bottomSearchPanel.setBounds(550, 200, 550, 25);
        add(bottomSearchPanel);

        // 패널에 테이블 추가
        JPanel bottomTablePanel = new JPanel();
        bottomTablePanel.setLayout(new BorderLayout());

        resultTableForSearch = new JTable(new DefaultTableModel());
        JScrollPane scrollPane = new JScrollPane(resultTableForSearch);
        scrollPane.setPreferredSize(new Dimension(550, 225));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bottomTablePanel.add(scrollPane, BorderLayout.CENTER);
        bottomTablePanel.setBackground(Color.white);
        bottomTablePanel.setBounds(550, 225, 550, 225);
        add(bottomTablePanel);
    }

    // Label 생성
    private JLabel createLabel(String text, int fontSize, Color color, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(color);
        label.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, fontSize));
        return label;
    }

    // 테이블 생성
    private JTable createTable(String[] columnNames) throws Exception {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        ArrayList<ProductInfoVO> resultAll = productInfoDAO.getAll();
        for (ProductInfoVO vo : resultAll) {
            Object[] row = {vo.getCode(), vo.getProduct_code(), vo.getManufacturer_code(), vo.getWarehouse_id(),
                    vo.getPrice(), vo.getStock(), vo.getStock_date()};
            model.addRow(row);
        }
        return new JTable(model);
    }

    // 입력 필드 포커스 잃었을 때 처리
    private void handleFocusLost() {
        if (inputField.getText().isEmpty()) {
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

    // 주문자명 기준 검색
    private void handleOrdererSearch(DefaultTableModel model) throws Exception {
        String ordererInput = inputField.getText().trim();
        if (ordererInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "주문자명을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ordererID = Integer.parseInt(ordererInput);
        ArrayList<OutputOrdererVO> resultOrderer = outputInfoDAO.listForOrderer(ordererID);

        if (resultOrderer == null || resultOrderer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"주문자명", "사업자등록번호", "주문자 이름", "주문자 전화번호", "승인 번호", "결재자", "승인여부", "출고량"};
        model.setColumnIdentifiers(columnNames);
        for (OutputOrdererVO vo : resultOrderer) {
            Object[] row = {vo.getUser_id(), vo.getLicense(), vo.getName(), vo.getTel(),
                    vo.getConfirm_num(), vo.getConfirm_id(), vo.getStatus(), vo.getRelease_date()};
            model.addRow(row);
        }
    }

    // 승인 여부 기준 검색
    private void handleStatusSearch(DefaultTableModel model) throws Exception {
        String statusInput = inputField.getText().trim();
        if (statusInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "승인 여부를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<OutputInfoVO> resultConfirm = outputInfoDAO.listForStatus(statusInput);
        if (resultConfirm == null || resultConfirm.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {"승인 여부", "승인 번호", "제품 코드", "주문자명", "출고일"};
        model.setColumnIdentifiers(columnNames);
        for (OutputInfoVO vo : resultConfirm) {
            Object[] row = {vo.getStatus(), vo.getConfirm_num(), vo.getProduct_code(),
                    vo.getUser_id(), vo.getRelease_date()};
            model.addRow(row);
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
}
