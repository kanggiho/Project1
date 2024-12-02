package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.InputDAO;
import org.example.project1.inout.VO.InputManuVO;
import org.example.project1.inout.VO.InputProductVO;
import org.example.project1.inout.VO.InputVO;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


public class IncomingManagementPanel extends JPanel {
    private JTextField inputField;
    private JComboBox<String> modeSelector;
    private JTable resultTable;
    private InputDAO inputDAO = new InputDAO();

    public IncomingManagementPanel() {
        setPanel();
        initUI(); // UI 초기화
    }

    // Panel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.decode("#97A6A0"));
        setLayout(null);
        setVisible(true);
    }

    // UI 초기화
    private void initUI() {
        // ------------------- 입고 신청 내역 검색 ---------------------
        inputField = new JTextField(20);

        // 데이터 테이블 생성
        resultTable = new JTable(new DefaultTableModel());
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane);

        // 모드 선택 기능 구현
        String[] modes = {"전체보기", "자재코드", "제조업체", "입고일"};
        modeSelector = new JComboBox<>(modes);
        modeSelector.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));

        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getForeground().equals(Color.GRAY)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    setPlaceholderText();
                }
            }
        });

        modeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPlaceholderText();
            }
        });

        // '검색' 버튼 기능 구현
        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        // '초기화' 버튼 추가
        JButton clearButton = new JButton("초기화");
        clearButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField.setText("");
                resultTable.setModel(new DefaultTableModel());
                setPlaceholderText();
            }
        });

        // 생성된 검색 기능 추가
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(modeSelector);
        topPanel.add(inputField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.setBackground(Color.white);
        topPanel.setBounds(0, 0, 1100, 30);
        add(topPanel);

        // 테이블 추가
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.add(scrollPane,BorderLayout.CENTER);
        middlePanel.setBackground(Color.white);
        middlePanel.setBounds(0, 30, 1100, 280);
        add(middlePanel);

        // ----------------------- 입고 신청 기능 --------------------------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setSize(1100, 145);
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBounds(0, 310, 1100, 140);
        add(bottomPanel);

        setVisible(true);
    }

    private void setPlaceholderText() {
        String selectMode = (String) modeSelector.getSelectedItem();
        if ("전체보기".equals(selectMode)) {
            inputField.setText("전체 내역 조회");
        } else if ("자재코드".equals(selectMode)) {
            inputField.setText("자재 코드");
        } else if ("제조업체".equals(selectMode)) {
            inputField.setText("제조업체 코드");
        } else if ("입고일".equals(selectMode)) {
            inputField.setText("YYYY-MM-DD");
        }
        inputField.setForeground(Color.GRAY);
        inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
    }

    private void search() {
        String selectMode = (String) modeSelector.getSelectedItem();
        try {
            if ("자재코드".equals(selectMode)) {
                searchByProductCode();
            } else if ("제조업체".equals(selectMode)) {
                searchByManufacturer();
            } else if ("입고일".equals(selectMode)) {
                searchByAskingDate();
            } else if ("전체보기".equals(selectMode)) {
                searchAll();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "데이터 검색 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void searchByProductCode() {
        String productCodeInput = inputField.getText().trim();
        if (productCodeInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "자재 코드를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int productCode = Integer.parseInt(productCodeInput);
        ArrayList<InputProductVO> resultProductCode = inputDAO.listForProductCode(productCode);

        if (resultProductCode == null || resultProductCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        populateTable(resultProductCode, new String[]{"자재코드", "자재명", "입고번호", "제조업체 코드", "입고신청일", "입고 수량", "입고일"});
    }

    private void searchByManufacturer() {
        String manufacturerInput = inputField.getText().trim();
        if (manufacturerInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "제조 업체 코드를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<InputManuVO> resultManu = inputDAO.listForManu(manufacturerInput);

        if (resultManu == null || resultManu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        populateTable(resultManu, new String[]{"제조업체 코드", "제조업체명", "업종", "입고번호", "자재코드", "입고신청일", "입고 수량", "입고일"});
    }

    private void searchByAskingDate() {
        String askingDateInput = inputField.getText().trim();
        if (askingDateInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "입고일을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<InputVO> resultInput = inputDAO.list(askingDateInput);

        if (resultInput == null || resultInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        populateTable(resultInput, new String[]{"입고신청일", "입고번호", "제조업체 코드", "자재코드", "입고 수량", "입고일"});
    }

    private void searchAll() {
        ArrayList<InputVO> resultAll = inputDAO.getAll();
        populateTable(resultAll, new String[]{"입고번호", "제조업체 코드", "자재 코드", "입고신청일", "입고 수량", "입고일"});
    }

    private void populateTable(ArrayList<?> data, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Object item : data) {
            Object[] row = extractRowData(item);
            model.addRow(row);
        }

        resultTable.setModel(model);
    }

    private Object[] extractRowData(Object data) {
        if (data instanceof InputProductVO) {
            InputProductVO vo = (InputProductVO) data;
            return new Object[]{vo.getProduct_code(), vo.getProduct_name(), vo.getInput_num(),
                    vo.getManufacturer_code(), vo.getAsking_date(), vo.getWarehoused_quantity(),
                    vo.getWarehoused_date()};
        } else if (data instanceof InputManuVO) {
            InputManuVO vo = (InputManuVO) data;
            return new Object[]{vo.getManufacturer_code(), vo.getManufacturer_name(), vo.getSorting(),
                    vo.getInput_num(), vo.getProduct_code(), vo.getAsking_date(),
                    vo.getWarehoused_quantity(), vo.getWarehoused_date()};
        } else if (data instanceof InputVO) {
            InputVO vo = (InputVO) data;
            return new Object[]{vo.getAskingDate(), vo.getInputNum(), vo.getManufacturerCode(),
                    vo.getProductCode(), vo.getWarehousedQuantity(), vo.getWarehousedDate()};
        }
        return new Object[0];
    }
}
