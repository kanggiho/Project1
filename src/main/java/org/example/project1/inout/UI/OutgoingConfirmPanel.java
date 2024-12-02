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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class OutgoingConfirmPanel extends JPanel {
    JTextField inputField;
    private JComboBox<String> modeSelector;
    private JTable resultTable;

    OutputInfoDAO outputInfoDAO = new OutputInfoDAO();

    public OutgoingConfirmPanel() throws Exception {
        setPanel();
        initUI();
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.decode("#97A6A0"));
        setLayout(null);
        setVisible(true);
    }

    // UI 초기화
    private void initUI() throws Exception {
        inputField = new JTextField(20);

        //------------------ 재고 현황 조회 ---------------------
        JLabel labelForStock = new JLabel("재고 현황 조회", SwingConstants.CENTER);
        labelForStock.setForeground(Color.WHITE);
        labelForStock.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));

        resultTable = new JTable(new DefaultTableModel());
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(1100, 180));

        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        ArrayList<ProductInfoVO> resultAll = productInfoDAO.getAll();

        String[] columnNames = {"분류코드", "자재코드", "제조업체 코드", "창고 번호", "단가", "재고 수량", "입고 예정일"};
        DefaultTableModel modelforResult = new DefaultTableModel(columnNames, 0);

        for (ProductInfoVO vo : resultAll) {
            Object[] rowForResult = {vo.getCode(), vo.getProduct_code(), vo.getManufacturer_code(),
                    vo.getWarehouse_id(), vo.getPrice(), vo.getStock(), vo.getStock_date()};
            modelforResult.addRow(rowForResult);
        }

        resultTable.setModel(modelforResult);

        // topPanel 설정
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBounds(0, 0, 1100, 200);
        topPanel.setBackground(Color.decode("#97A6A0"));

        topPanel.add(labelForStock, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        add(topPanel);


        //------------------ 출고 승인, 거절 ---------------------
        //JPanel - 출고 승인, 거절
        JPanel middlePanel1 = new JPanel();
        middlePanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        middlePanel1.setBackground(Color.WHITE);
        middlePanel1.setBounds(0, 200, 550, 250);
        add(middlePanel1);




//        ------------------ 출고 승인 내역 조회 ---------------------
//        주문자명, 승인여부, 결재자 기준으로 검색
        String[] modes = {"주문자명", "승인 여부", "결재자"};
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
                    String selectMode = (String) modeSelector.getSelectedItem();
                    if ("주문자명".equals(selectMode)) {
                        inputField.setText("주문자 아이디");
                    } else if ("승인 여부".equals(selectMode)) {
                        inputField.setText("승인 여부(승인/거절/대기중)");
                    } else if ("결재자".equals(selectMode)) {
                        inputField.setText("결재자 사번");
                    }
                    inputField.setForeground(Color.GRAY);
                }
            }
        });

        modeSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectMode = (String) modeSelector.getSelectedItem();
                if ("주문자명".equals(selectMode)) {
                    inputField.setText("주문자 아이디");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                } else if ("승인 여부".equals(selectMode)) {
                    inputField.setText("승인 여부(승인/거절/대기중)");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                } else if ("결재자".equals(selectMode)) {
                    inputField.setText("결재자 사번");
                    inputField.setForeground(Color.GRAY);
                    inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
                }
            }
        });

//        '검색' 버튼 기능 구현
        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 10));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectMode = (String) modeSelector.getSelectedItem();
                try {
                    if ("주문자명".equals(selectMode)) {
                        //주문자명 기준 검색
                        String ordererInput = inputField.getText().trim();
                        if (ordererInput.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "자재 코드를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        int ordererID = Integer.parseInt(ordererInput);
                        ArrayList<OutputOrdererVO> resultOrderer = outputInfoDAO.listForOrderer(ordererID);

                        if (resultOrderer == null || resultOrderer.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "검색 결과가 없습니다.", "결과 없음", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        String[] columnNames = {"주문자명", "사업자등록번호", "주문자 이름", "주문자 전화번호", "승인 번호", "결재자", "승인여부", "출고량"};
                        DefaultTableModel modelforResult = new DefaultTableModel(columnNames, 0);

                        for (OutputOrdererVO vo : resultOrderer) {
                            Object[] rowForResult = {vo.getUser_id(), vo.getLicense(), vo.getName(), vo.getTel(),
                                                    vo.getConfirm_num(), vo.getConfirm_id(), vo.getStatus(), vo.getRelease_date()};
                            modelforResult.addRow(rowForResult);
                        }

                        resultTable.setModel(modelforResult);

                    } else if ("승인 여부".equals(selectMode)) {
                        // 승인 여부 기준 검색
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
                        DefaultTableModel modelforResult = new DefaultTableModel(columnNames, 0);

                        for (OutputInfoVO vo : resultConfirm) {
                            Object[] rowForResult = {vo.getStatus(), vo.getConfirm_num(), vo.getProduct_code(),
                                                    vo.getUser_id(), vo.getRelease_date()};
                            modelforResult.addRow(rowForResult);
                        }

                        resultTable.setModel(modelforResult);

                    } else if ("결재자".equals(selectMode)) {
                        // 결재자 기준
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
                        DefaultTableModel modelforResult = new DefaultTableModel(columnNames, 0);

                        for (OutputAdminVO vo : resultAdmin) {
                            Object[] rowForResult = {vo.getConfirm_id(), vo.getConfirm_num(), vo.getName(), vo.getTel(),
                                                    vo.getUser_id(), vo.getStatus(), vo.getRelease_date()};
                            modelforResult.addRow(rowForResult);
                        }

                        resultTable.setModel(modelforResult);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "데이터 검색 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JPanel middlePanel2 = new JPanel();
        middlePanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        middlePanel1.setBackground(Color.decode("#97A6A0"));
        middlePanel1.setBounds(0, 200, 550, 250);
        add(middlePanel1);

    }
}