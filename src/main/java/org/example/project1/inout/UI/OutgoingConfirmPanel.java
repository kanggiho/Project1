package org.example.project1.inout.UI;

import javax.swing.*;
import java.awt.*;

public class OutgoingConfirmPanel extends JPanel {

    public OutgoingConfirmPanel() {
        setPanel();
        initUI(); // UI 초기화
    }

    // JPanel 설정
    private void setPanel(){

    }

    // UI 초기화
    private void initUI(){
        JLabel Label = new JLabel();
        setLayout(new BorderLayout());
        add(Label, BorderLayout.CENTER);
        add(Label);

        //JTable - 재고현황조회

       //JPanel - 출고 승인, 거절

       //JTextField - 검색창
       //주문자명 기준으로 검색
       //승인여부 기준으로 검색
       //결재자 기준으로 검색

       //JTable - 출고 승인 내역 조회

    }
}