package org.example.Project1.View.Panel.Admin;

import javax.swing.*;
import java.awt.*;

public class OutgoingConfirmPanel extends JPanel {
    String title;

    public OutgoingConfirmPanel(String title) {
        this.title = title;
        setPanel();
        initUI(); // UI 초기화
    }

    // JPanel 설정
    private void setPanel(){

    }

    // UI 초기화
    private void initUI(){
        JLabel Label = new JLabel(title);
        setLayout(new BorderLayout());
        add(Label, BorderLayout.CENTER);
        add(Label);
    }
}