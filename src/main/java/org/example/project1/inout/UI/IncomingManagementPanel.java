package org.example.project1.inout.UI;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.inout.DAO.InputDAO;
import org.example.project1.inout.DAO.ProductDAO;
import org.example.project1.inout.VO.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class IncomingManagementPanel extends JPanel {
    private JTextField inputField;
    private JComboBox<String> modeSelector;
    private JTable resultTable;
    private JTextField productCode;
    private JTextField productName;
    private JTextField manufacturerCode;
    private JTextField askingDate;
    private JTextField warehousedQuantity;
    private JTextField warehousedDate;

    InputDAO inputDAO = new InputDAO();
    ProductDAO productDAO = new ProductDAO();

    public IncomingManagementPanel() throws Exception {
        setPanel();
        initUI(); // UI 초기화
        searchAll();
    }

    // Panel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.white);
        setLayout(null);
        setVisible(true);
    }

    // UI 초기화
    private void initUI() {
        // ------------------- 입고 신청 내역 검색 ---------------------
        // 제목 라벨 생성
        JLabel labelForSearch = new JLabel("입고 신청 내역   ");
        labelForSearch.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        labelForSearch.setBounds(10, 20, 0, 0);
        labelForSearch.setForeground(Color.BLACK);

        // 검색창 생성
        inputField = new JTextField(15);

        // 데이터 테이블 생성
        resultTable = new JTable(new DefaultTableModel());
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setBounds(20, 50, 1060, 200);
        add(scrollPane);

        // 모드 선택 기능 구현
        String[] modes = {"전체보기", "자재명", "제조업체", "입고일"};
        modeSelector = new JComboBox<>(modes);
        modeSelector.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        modeSelector.setForeground(Color.BLACK);
        modeSelector.setBackground(ColorSet.color1_light[3]);

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
        searchButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        searchButton.setForeground(Color.BLACK);
        searchButton.setBackground(ColorSet.color1_light[1]);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        // '초기화' 버튼 추가
        JButton clearButton = new JButton("초기화");
        clearButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));;
        clearButton.setForeground(Color.WHITE);
        clearButton.setBackground(ColorSet.color_button[0]);
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
        topPanel.add(labelForSearch);
        topPanel.add(modeSelector);
        topPanel.add(inputField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.setBackground(Color.white);
        topPanel.setBounds(0, 0, 1100, 40);
        add(topPanel);


        // ----------------------- 입고 신청 기능 --------------------------
        // 입고 신청 내용 입력창 생성
        // 제조 업체 코드 입력창
        manufacturerCode = new JTextField(10);
        String placeholderText3 = "제조업체 코드";
        Font placeholderFont3 = new Font("머니그라피TTF Rounded", Font.PLAIN, 16);
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
                    manufacturerCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
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

        // 자재명 입력창
        productName = new JTextField(10);
        productName.setText("자재명");
        productName.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        productName.setForeground(Color.GRAY);
        productName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productName.getForeground().equals(Color.GRAY) && productName.getText().equals("자재명")) {
                    productName.setText("");
                    productName.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    productName.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productName.getText().isEmpty()) {
                    productName.setText("자재명");
                    productName.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    productName.setForeground(Color.GRAY);
                }
            }
        });

        // 자재코드 입력창
        productCode = new JTextField(10);
        productCode.setText("자재코드");
        productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        productCode.setForeground(Color.GRAY);
        productCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (productCode.getForeground().equals(Color.GRAY) && productCode.getText().equals("자재코드")) {
                    productCode.setText("");
                    productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    productCode.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (productCode.getText().isEmpty()) {
                    productCode.setText("자재코드");
                    productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    productCode.setForeground(Color.GRAY);
                }
            }
        });

        // 입고신청일 입력 창
        askingDate = new JTextField(10);
        askingDate.setText("입고신청일");
        askingDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        askingDate.setForeground(Color.GRAY);
        askingDate.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (askingDate.getForeground().equals(Color.GRAY) && askingDate.getText().equals("입고신청일")) {
                    askingDate.setText("");
                    askingDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    askingDate.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (askingDate.getText().isEmpty()) {
                    askingDate.setText("입고신청일");
                    askingDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    askingDate.setForeground(Color.GRAY);
                }
            }
        });

        // 입고 수량 입력 창
        warehousedQuantity = new JTextField(10);
        warehousedQuantity.setText("입고 수량");
        warehousedQuantity.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        warehousedQuantity.setForeground(Color.GRAY);
        warehousedQuantity.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (warehousedQuantity.getForeground().equals(Color.GRAY) && warehousedQuantity.getText().equals("입고 수량")) {
                    warehousedQuantity.setText("");
                    warehousedQuantity.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    warehousedQuantity.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (warehousedQuantity.getText().isEmpty()) {
                    warehousedQuantity.setText("입고 수량");
                    warehousedQuantity.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                    warehousedQuantity.setForeground(Color.GRAY);
                }
            }
        });

        // ----------------------- 버튼 ------------------------
        // 입고 신청 버튼 생성
        JButton application = new JButton("입고 신청");
        application.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        application.setForeground(Color.BLACK);
        application.setBackground(ColorSet.color_button[1]);
        application.addActionListener(e -> handleApplyButton());

        // 추가 입고 버튼 생성
        JButton addApply = new JButton("추가 입고");
        addApply.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        addApply.setForeground(Color.BLACK);
        addApply.setBackground(ColorSet.color_button[1]);
        addApply.addActionListener(e -> handleAddButton());

        // 초기화 버튼
        JButton clearButtonForSearch = new JButton("초기화");
        clearButtonForSearch.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        clearButtonForSearch.setForeground(Color.WHITE);
        clearButtonForSearch.setBackground(ColorSet.color_button[0]);
        clearButtonForSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productName.setText("자재명");
                productName.setForeground(Color.GRAY);
                productName.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                manufacturerCode.setText("제조업체 코드");
                manufacturerCode.setForeground(Color.GRAY);
                manufacturerCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                productCode.setText("자재코드");
                productCode.setForeground(Color.GRAY);
                productCode.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                askingDate.setText("입고신청일");
                askingDate.setForeground(Color.GRAY);
                askingDate.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
                warehousedQuantity.setText("입고 수량");
                warehousedQuantity.setForeground(Color.GRAY);
                warehousedQuantity.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
            }
        });


        // ------------------------- 입고 신청 패널 -------------------------------
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setSize(1100, 200);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBounds(0, 280, 1100, 250);

        // 입고 신청 제목 라벨
        JLabel labelForApply = new JLabel("입고 신청");
        labelForApply.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        labelForApply.setForeground(Color.black);

        // 라벨 (왼쪽 상단)
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setBackground(Color.WHITE);
        labelPanel.add(labelForApply);

        // 입력 필드와 버튼 (아래쪽)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        inputPanel.setBackground(Color.WHITE);

        // 입력 필드와 버튼 추가
        inputPanel.add(productName);
        inputPanel.add(manufacturerCode);
        inputPanel.add(productCode);
        inputPanel.add(askingDate);
        inputPanel.add(warehousedQuantity);
        inputPanel.add(application);
        inputPanel.add(addApply);
        inputPanel.add(clearButtonForSearch);

        // 라벨과 입력 필드 패널을 bottomPanel에 추가
        bottomPanel.add(labelPanel, BorderLayout.NORTH);
        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        // bottomPanel을 프레임에 추가
        add(bottomPanel);

        setVisible(true);
    }


    // ------------------------- 기능 메소드 --------------------------------
    // 검색창 텍스트 필드 설정
    private void setPlaceholderText() {
        String selectMode = (String) modeSelector.getSelectedItem();
        if ("전체보기".equals(selectMode)) {
            inputField.setText("전체 내역 조회");
        } else if ("자재명".equals(selectMode)) {
            inputField.setText("자재명");
        } else if ("제조업체".equals(selectMode)) {
            inputField.setText("제조업체 코드");
        } else if ("입고일".equals(selectMode)) {
            inputField.setText("YYYY-MM-DD");
        }
        inputField.setForeground(Color.GRAY);
        inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
    }

    // 검색 기능 구현
    private void search() {
        String selectMode = (String) modeSelector.getSelectedItem();
        try {
            if ("자재명".equals(selectMode)) {
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

    // 자재명으로 검색
    private void searchByProductCode() {
        String productNameInput = inputField.getText().trim();

        // 자재명으로 자재코드 검색한 결과
        int productCodeInput = inputDAO.ProductCodesByName(productNameInput);

        // 가져온 자재코드로 2차 검색
        if (productNameInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "자재명을 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<InputProductVO> resultProductCode = inputDAO.listForProductCode(productCodeInput);

        if (resultProductCode == null || resultProductCode.isEmpty()) {
            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        populateTable(resultProductCode, new String[]{"자재코드", "자재명", "입고번호", "제조업체 코드", "입고신청일", "입고 수량", "입고일"});

        // 행 클릭하면 자동으로 입고 신청 패널 텍스트 필드에 데이터 삽입
        resultTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = resultTable.getSelectedRow();
                if (row >= 0) {
                    productCode.setText(resultTable.getValueAt(row, 0).toString());
                    productCode.setForeground(Color.BLACK);
                    productName.setText(resultTable.getValueAt(row, 1).toString());
                    productName.setForeground(Color.BLACK);
                    manufacturerCode.setText(resultTable.getValueAt(row, 3).toString());
                    manufacturerCode.setForeground(Color.BLACK);
                }
            }
        });
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


    // ---------------------입고 신청 버튼 기능--------------------------
    private void handleApplyButton() {
        String productNameInput = productName.getText().trim();
        String manufacturerCodeInput = manufacturerCode.getText().trim();
        String productCodeInput = productCode.getText().trim();
        String askingDateInput = askingDate.getText().trim();
        String warehousedQuantityInput = warehousedQuantity.getText().trim();

        System.out.println("텍스트 추출");

        if (manufacturerCodeInput.isEmpty() || productCodeInput.isEmpty() ||
                askingDateInput.isEmpty() || warehousedQuantityInput.isEmpty() ) {
            JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 입고일(=입고예정일) - 오늘 날짜 가져오기
        String warehousedDate = getToday();

        InputVO vo1 = new InputVO();
        vo1.setManufacturerCode(manufacturerCodeInput);
        vo1.setProductCode(Integer.parseInt(productCodeInput));
        vo1.setAskingDate(askingDateInput);
        vo1.setWarehousedQuantity(Integer.parseInt(warehousedQuantityInput));
        vo1.setWarehousedDate(warehousedDate);


        ProductInfoVO vo2 = new ProductInfoVO();
        vo2.setProduct_code(Integer.parseInt(productCodeInput));
        vo2.setManufacturer_code(manufacturerCodeInput);
        vo2.setStock(Integer.parseInt(warehousedQuantityInput));
        vo2.setStock_date(warehousedDate);

        ProductVO vo3 = new ProductVO();
        vo3.setProduct_code(Integer.parseInt(productCodeInput));
        vo3.setProduct_name(productNameInput);

        try {
            // 기존 재고에 물품 있는지 확인하고 있으면 업데이트
            // 없으면 추가 입고 기능(버튼) 사용
            if (inputDAO.checkInventory(vo2)) {
                JOptionPane.showMessageDialog(null, "기존 재고가 존재합니다. 재고가 업데이트 됩니다.");
                // 입고 신청 내역에 데이터 추가
                inputDAO.insertToInput(vo1);
                System.out.println("입고 내역 업데이트 완료");
                inputDAO.updateInventory(vo2);
                System.out.println("재고 내역 업데이트 완료");
            } else {
                // 자재 정보 테이블 업데이트
                try {
                    productDAO.insert(vo3);
                } catch (Exception ex) {
                    System.out.println("자재 정보 업데이트 실패" + ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, "기존 재고가 없습니다. 추가 입고해주세요.");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "입고 신청에 실패했습니다.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "자재 정보 업데이트에 실패했습니다.");
        }
    }

    // 추가 입고 버튼 기능
    private void handleAddButton () {
        InputIncomingFrame inputIncomingFrame = new InputIncomingFrame();
    }

    // 입고일에 오늘 날짜 가져오기
    private String getToday(){
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 형식에 맞게 변환
        String formattedDate = today.format(formatter);

        return formattedDate;
    }
}