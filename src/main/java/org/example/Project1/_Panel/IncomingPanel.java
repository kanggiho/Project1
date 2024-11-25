package org.example.Project1._Panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class IncomingPanel extends JPanel {
    String title;

    public IncomingPanel(String title) {
        this.title = title;
        setPanel();
        initUI(); // UI 초기화
    }

    // JPanel 설정
    private void setPanel() {

    }

    // UI 초기화
    private void initUI() {
        JLabel Label = new JLabel(title);
        setLayout(new BorderLayout());
        //add(Label, BorderLayout.CENTER);


        String[] columns = {"아이디", "제품명", "식품부류", "제조사", "생산일자", "창고", "개수"};
        Object[][] data = {
                {"F100", "Chicken", "Instant", "Puradak", "2024-11-20", "Warehouse-1","5"},
                {"F101", "Pizza", "Instant", "Domino", "2024-11-20", "Warehouse-3","7"},
                {"F102", "Pasta", "Instant", "Rolling", "2024-11-20", "Warehouse-7","13"},
                {"F103", "Cola", "SoftDrink", "Cokacola", "2024-11-20", "Warehouse-2","6"},
                {"F104", "PorkRib", "MainDishes", "VIPS", "2024-11-20", "Warehouse-5","2"},
                {"F105", "Grape", "fruit", "Farm-A", "2024-11-20", "Warehouse-6","22"},
                {"F106", "Cider", "SoftDrink", "Chilseong", "2024-11-20", "Warehouse-7","32"}
        };
        DefaultTableModel dtm = new DefaultTableModel(data, columns);


        JTable inputTable = new JTable(dtm);
        inputTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        inputTable.setAutoCreateRowSorter(true);
        add(inputTable, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(inputTable);
        add(scrollPane);


    }
}
