package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.InputDAO;
import org.example.project1.inout.VO.InputManuVO;
import org.example.project1.inout.VO.InputProductVO;
import org.example.project1.inout.VO.InputVO;
import org.example.project1.inventory.VO.ProductInfoVO;


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
    private JTextField inputNum;
    private JTextField productCode;
    private JTextField manufacturerCode;
    private JTextField askingDate;
    private JTextField warehousedQuantity;
    private JTextField warehousedDate;

    InputDAO inputDAO = new InputDAO();

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
        // 검색창 생성
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
        middlePanel.setBounds(0, 30, 1100, 360);
        add(middlePanel);




        // ----------------------- 입고 신청 기능 --------------------------
        // 입고 신청 내용 입력창 생성
        // 제조 업체 코드 입력창
        manufacturerCode = new JTextField(15);
        String placeholderText3 = "제조 업체 코드";
        Font placeholderFont3 = new Font("머니그라피TTF Rounded", Font.PLAIN, 10);
        Color placeholderColor3 = Color.GRAY;
        Color inputTextColor3 = Color.BLACK;

        manufacturerCode.setText(placeholderText3);
        manufacturerCode.setFont(placeholderFont3);
        manufacturerCode.setForeground(placeholderColor3);

        manufacturerCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (manufacturerCode.getForeground().equals(placeholderColor3) && manufacturerCode.getText().equals(placeholderText3)) {
                    manufacturerCode.setText("");
                    manufacturerCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    manufacturerCode.setForeground(inputTextColor3);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (manufacturerCode.getText().isEmpty()) {
                    manufacturerCode.setText(placeholderText3);
                    manufacturerCode.setFont(placeholderFont3);
                    manufacturerCode.setForeground(placeholderColor3);
                }
            }
        });

        // 자재코드 입력창
        productCode = new JTextField(15);
        String placeholderText2 = "자재 코드";
        Font placeholderFont2 = new Font("머니그라피TTF Rounded", Font.PLAIN, 10);
        Color placeholderColor2 = Color.GRAY;
        Color inputTextColor2 = Color.BLACK;

        productCode.setText(placeholderText2);
        productCode.setFont(placeholderFont2);
        productCode.setForeground(placeholderColor2);

        productCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productCode.getForeground().equals(placeholderColor2) &&  productCode.getText().equals(placeholderText2)) {
                    productCode.setText("");
                    productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    productCode.setForeground(inputTextColor2);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productCode.getText().isEmpty()) {
                    productCode.setText(placeholderText2);
                    productCode.setFont(placeholderFont2);
                    productCode.setForeground(placeholderColor2);
                }
            }
        });


        // 입고신청일 입력 창
        askingDate = new JTextField(15);
        String placeholderText4 = "입고신청일";
        Font placeholderFont4 = new Font("머니그라피TTF Rounded", Font.PLAIN, 10);
        Color placeholderColor4 = Color.GRAY;
        Color inputTextColor4 = Color.BLACK;

        askingDate.setText(placeholderText4);
        askingDate.setFont(placeholderFont4);
        askingDate.setForeground(placeholderColor4);

        askingDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (askingDate.getForeground().equals(placeholderColor4) && askingDate.getText().equals(placeholderText4)) {
                    askingDate.setText("");
                    askingDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    askingDate.setForeground(inputTextColor4);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (askingDate.getText().isEmpty()) {
                    askingDate.setText(placeholderText4);
                    askingDate.setFont(placeholderFont4);
                    askingDate.setForeground(placeholderColor4);
                }
            }
        });

        // 입고 수량 입력 창
        warehousedQuantity = new JTextField(15);
        String placeholderText5 = "입고 수량";
        Font placeholderFont5 = new Font("머니그라피TTF Rounded", Font.PLAIN, 10);
        Color placeholderColor5 = Color.GRAY;
        Color inputTextColor5 = Color.BLACK;

        warehousedQuantity.setText(placeholderText5);
        warehousedQuantity.setFont(placeholderFont5);
        warehousedQuantity.setForeground(placeholderColor5);

        warehousedQuantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (warehousedQuantity.getForeground().equals(placeholderColor5) && warehousedQuantity.getText().equals(placeholderText5)) {
                    warehousedQuantity.setText("");
                    warehousedQuantity.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    warehousedQuantity.setForeground(inputTextColor5);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (warehousedQuantity.getText().isEmpty()) {
                    warehousedQuantity.setText(placeholderText5);
                    warehousedQuantity.setFont(placeholderFont5);
                    warehousedQuantity.setForeground(placeholderColor5);
                }
            }
        });

        // 입고일 입력 창
        warehousedDate = new JTextField(15);
        String placeholderText6 = "입고일";
        Font placeholderFont6 = new Font("머니그라피TTF Rounded", Font.PLAIN, 10);
        Color placeholderColor6 = Color.GRAY;
        Color inputTextColor6 = Color.BLACK;

        warehousedDate.setText(placeholderText6);
        warehousedDate.setFont(placeholderFont6);
        warehousedDate.setForeground(placeholderColor6);

        warehousedDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (warehousedDate.getForeground().equals(placeholderColor6) && warehousedDate.getText().equals(placeholderText6)) {
                    warehousedDate.setText("");
                    warehousedDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 12));
                    warehousedDate.setForeground(inputTextColor6);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (warehousedDate.getText().isEmpty()) {
                    warehousedDate.setText(placeholderText6);
                    warehousedDate.setFont(placeholderFont6);
                    warehousedDate.setForeground(placeholderColor6);
                }
            }
        });

        // 입고 신청 버튼 생성
        JButton application = new JButton("입고 신청");
        application.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        application.addActionListener(e -> handleApplyButton());


        JPanel bottomPanel = new JPanel();
        bottomPanel.add(manufacturerCode);
        bottomPanel.add(productCode);
        bottomPanel.add(askingDate);
        bottomPanel.add(warehousedDate);
        bottomPanel.add(warehousedQuantity);
        bottomPanel.add(application);
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setSize(1100, 145);
        bottomPanel.setBackground(Color.white);
        bottomPanel.setBounds(0, 390, 1100, 60);
        add(bottomPanel);


        setVisible(true);
    }

    // ------------------------- 기능 메소드 --------------------------------
    // 검색창 텍스트 필드 설정
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

    // 검색 기능 구현
    private void search() {
        String selectMode = (String) modeSelector.getSelectedItem();
        try {
            if ("자재코드".equals(selectMode)) {
                searchByProductCode();
            } else if ("제조업체".equals(selectMode)) {
                searchByManufacturer();
            } else if ("입고일".equals(selectMode)) {
                searchByDate();
            } else if ("전체보기".equals(selectMode)) {
                searchAll();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "데이터 검색 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // 자재코드로 검색
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

    // 제조업체 코드로 검색
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

    ///입고일로 검색
    private void searchByDate() {
        String DateInput = inputField.getText().trim();
        if (DateInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "입고일을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<InputVO> resultInput = inputDAO.list(DateInput);

        if (resultInput == null || resultInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        populateTable(resultInput, new String[]{"입고신청일", "입고번호", "제조업체 코드", "자재코드", "입고 수량", "입고일"});
    }

    // 전체 검색
    private void searchAll() {
        ArrayList<InputVO> resultAll = inputDAO.getAll();
        populateTable(resultAll, new String[]{"입고신청일", "입고 번호", "제조업체 코드", "자재 코드", "입고 수량", "입고일"});
    }

    // 테이블 & 데이터
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

    // 입고 신청 버튼 기능
    private void handleApplyButton() {
        String manufacturerCodeInput = manufacturerCode.getText().trim();
        String productCodeInput = productCode.getText().trim();
        String askingDateInput = askingDate.getText().trim();
        String warehousedQuantityInput = warehousedQuantity.getText().trim();
        String warehousedDateInput = warehousedDate.getText().trim();

        System.out.println("텍스트 추출");

        if (manufacturerCodeInput.isEmpty() || productCodeInput.isEmpty() ||
        askingDateInput.isEmpty() || warehousedQuantityInput.isEmpty() || warehousedDateInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            InputVO vo1 = new InputVO();
            vo1.setManufacturerCode(manufacturerCodeInput);
            vo1.setProductCode(Integer.parseInt(productCodeInput));
            vo1.setAskingDate(askingDateInput);
            vo1.setWarehousedQuantity(Integer.parseInt(warehousedQuantityInput));
            vo1.setWarehousedDate(warehousedDateInput);


            ProductInfoVO vo2 = new ProductInfoVO();
            vo2.setProduct_code(Integer.parseInt(productCodeInput));
            vo2.setManufacturer_code(manufacturerCodeInput);
            vo2.setStock(Integer.parseInt(warehousedQuantityInput));
            vo2.setStock_date(warehousedDateInput);


            // 입고 신청 내역에 데이터 추가
            inputDAO.insertToInput(vo1);

            // 기존 재고에 물품 있는지 확인하고
            // 있으면 업데이트, 없으면 추가
            if (inputDAO.checkInventory(vo2)) {
                JOptionPane.showMessageDialog(null, "기존 재고가 존재합니다. 재고가 업데이트 됩니다.");
                inputDAO.updateInventory(vo2);
            } else {
                JOptionPane.showMessageDialog(null, "기존 재고가 없습니다. 추가 데이터를 입력해주세요.");
                // 추가 데이터 입력 창
                JFrame extraframe = new JFrame();

                // 추가 데이터 입력 텍스트 추출
                // 추가 데이터 vo에 담아서 dao에 전송
                inputDAO.insertToInventory(vo2);
            }

            JOptionPane.showMessageDialog(null, "입고 신청 완료");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "숫자 필드에 유효한 숫자를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "입고 신청 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

}
