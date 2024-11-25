package org.example.Project1.View.Panel;

import javax.swing.*;
import java.awt.*;

public class InventoryPanel extends JPanel {
    String title;

    public InventoryPanel(String title) {
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
