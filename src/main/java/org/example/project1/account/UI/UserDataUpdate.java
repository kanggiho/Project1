package org.example.project1.account.UI;

import javax.swing.*;
import java.awt.*;

public class UserDataUpdate extends JPanel{
    String title;

    public UserDataUpdate(String title) {
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
