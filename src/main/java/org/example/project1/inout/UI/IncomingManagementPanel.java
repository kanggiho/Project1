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
    private String selectMode;

    public IncomingManagementPanel() {
        setPanel();
        initUI(); // UI 초기화
    }

    //Panel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.decode("#97A6A0"));
        setLayout(null);
        setVisible(true);
    }

    // UI 초기화
    private void initUI() {
        //검색창 생성(자재코드, 제조업체 코드, 입고신청일 기준으로 검색)
        inputField = new JTextField(20);

        //데이터 테이블 생성
        resultTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultTable);


        //모드 선택 기능 구현
        String[] modes = {"전체보기", "자재코드", "제조업체", "입고신청일"};
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
                    String selectedMode = (String) modeSelector.getSelectedItem();
                    if ("전체보기".equals(selectedMode)) {
                        inputField.setText("전체 내역 조회");
                    } else if ("자재코드".equals(selectedMode)) {
                        inputField.setText("자재 코드");
                    } else if ("제조업체".equals(selectedMode)) {
                        inputField.setText("제조업체 코드");
                    } else if ("입고신청일".equals(selectedMode)) {
                        inputField.setText("YYYY-MM-DD");
                    }
                    inputField.setForeground(Color.GRAY);
                }
            }
        });

        modeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectMode = (String) modeSelector.getSelectedItem();
                if ("전체보기".equals(selectMode)) {
                    inputField.setText("전체 내역 조회");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                } else if ("자재코드".equals(selectMode)) {
                    inputField.setText("자재 코드");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                } else if ("제조업체".equals(selectMode)) {
                    inputField.setText("제조업체 코드");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                } else if ("입고신청일".equals(selectMode)) {
                    inputField.setText("YYYY-MM-DD");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                }
            }
        });


        //'검색' 버튼 기능 구현
        JButton searchButton = new JButton("검색");
        searchButton.setFont((new Font("머니그라피TTF Rounded", Font.PLAIN, 10)));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMode = (String) modeSelector.getSelectedItem();
                InputDAO inputDAO = new InputDAO();
                if (selectMode.equals("전체보기")) {
                    // 전체 검색 결과
                } else if (selectMode.equals("자재코드")) {
                    // 자재코드에 따른 검색 결과
                    String productCodeInput = inputField.getText();
                    result();
                } else if (selectMode.equals("제조업체")) {
                    // 제조업체에 따른 검색 결과
                    String manufacturerInput = inputField.getText();
                    result();
                } else if (selectMode.equals("입고신청일")) {
                    // 입고신청일에 따른 검색 결과
                    String askingDateInput = inputField.getText();
                    result();
                }
            }
        });

        //생성된 검색 기능 추가
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(modeSelector);
        topPanel.add(inputField);
        topPanel.add(searchButton);
        topPanel.setBackground(Color.decode("#97A6A0"));
        topPanel.setBounds(0, 0, 1100, 30);
        add(topPanel);

        //테이블 추가
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        middlePanel.add(resultTable);
        middlePanel.setBackground(Color.WHITE);
        middlePanel.setSize(1100,290);
        middlePanel.setBounds(0, 30, 1100, 280);
        add(middlePanel);


        //입고 신청
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setSize(1100, 145);
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setBounds(0, 310, 1100, 140);
        add(bottomPanel);


        setVisible(true);
    }


    private void result() {
        InputDAO inputDAO = new InputDAO();

        if (selectMode.equals("전체보기")) {
            // 전체 검색 결과
            ArrayList<InputVO> resultAll = inputDAO.getAll();

            String[] columnNames = {"입고번호", "제조업체 코드", "자재 코드", "입고신청일", "입고 수량", "입고일"};
            DefaultTableModel modelforResult = new DefaultTableModel();
            for (InputVO vo : resultAll) {
                Object[] rowForResult = {vo.getInputNum(), vo.getManufacturerCode(), vo.getProductCode(),
                        vo.getAskingDate(), vo.getWarehousedQuantity(), vo.getWarehousedDate()};
                modelforResult.addRow(rowForResult);
            }

            resultTable.setModel(modelforResult);
        } else if (selectMode.equals("자재코드")) {
            // 자재코드에 따른 검색 결과
            String productCodeInput = inputField.getText();

            ArrayList<InputProductVO> resultProductCode = inputDAO.listForProductCode(Integer.parseInt(productCodeInput));

            String[] columnNames = {"자재코드", "자재명", "입고번호", "제조업체 코드", "입고신청일", "입고 수량", "입고일"};
            DefaultTableModel modelforResult = new DefaultTableModel();
            for (InputProductVO vo : resultProductCode) {
                Object[] rowForResult = {vo.getProduct_code(), vo.getProduct_name(), vo.getInput_num(),
                        vo.getManufacturer_code(), vo.getAsking_date(), vo.getWarehoused_quantity(),
                        vo.getWarehoused_date()};
                modelforResult.addRow(rowForResult);
            }

            resultTable.setModel(modelforResult);
        } else if (selectMode.equals("제조업체")) {
            // 제조업체에 따른 검색 결과
            String manufacturerInput = inputField.getText();

            ArrayList<InputManuVO> resultManu = inputDAO.listForManu(manufacturerInput);

            String[] columnNames = {"제조업체 코드", "제조업체명", "업종", "입고번호", "자재코드", "입고신청일", "입고 수량", "입고일"};
            DefaultTableModel modelforResult = new DefaultTableModel();
            for (InputManuVO vo : resultManu) {
                Object[] rowForResult = {vo.getManufacturer_code(), vo.getManufacturer_name(), vo.getSorting(),
                        vo.getInput_num(), vo.getProduct_code(), vo.getAsking_date(),
                        vo.getWarehoused_quantity(), vo.getWarehoused_date()};
                modelforResult.addRow(rowForResult);
            }

            resultTable.setModel(modelforResult);
        } else if (selectMode.equals("입고신청일")) {
            // 입고신청일에 따른 검색 결과
            String askingDateInput = inputField.getText();

            ArrayList<InputVO> resultManu = inputDAO.list(askingDateInput);

            String[] columnNames = {"입고신청일", "입고번호", "제조업체 코드", "자재코드", "입고 수량", "입고일"};
            DefaultTableModel modelforResult = new DefaultTableModel();
            for (InputVO vo : resultManu) {
                Object[] rowForResult = {vo.getAskingDate(), vo.getInputNum(), vo.getManufacturerCode(),
                        vo.getProductCode(), vo.getWarehousedQuantity(), vo.getWarehousedDate()};
                modelforResult.addRow(rowForResult);
            }

            resultTable.setModel(modelforResult);
        }
    }
}
