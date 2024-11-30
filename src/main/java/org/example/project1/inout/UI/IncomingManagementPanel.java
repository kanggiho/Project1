package org.example.project1.inout.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class IncomingManagementPanel extends JPanel {

    public IncomingManagementPanel() {
        setPanel();
        initUI(); // UI 초기화
    }

    private void setPanel() {

    }

    // UI 초기화
    private void initUI() {
        JLabel Label = new JLabel();
        setLayout(new BorderLayout());
        //add(Label, BorderLayout.CENTER);

        // JTextField- 검색창
        // 자재코드 기준으로 검색
        // 제조업체 코드 기준으로 검색
        // 입고신청일 기준으로 검색
        JTextField inputProductCode = new JTextField();



        //JTable - 입고 기록 조회


        // JPanel- 입고 신청
        DefaultTableModel dtm = new DefaultTableModel();



        JTable inputTable = new JTable(dtm);
        inputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        inputTable.setAutoCreateRowSorter(true);
        add(inputTable, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(inputTable);
        add(scrollPane);


    }
}
