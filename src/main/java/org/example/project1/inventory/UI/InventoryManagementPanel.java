package org.example.project1.inventory.UI;

import javax.swing.*;
import java.awt.*;

public class InventoryManagementPanel extends JPanel {
    String title;

    public InventoryManagementPanel(String title) {
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