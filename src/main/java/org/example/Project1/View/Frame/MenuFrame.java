package org.example.Project1.View.Frame;

import org.example.Project1.View.Panel.HistoryPanel;
import org.example.Project1.View.Panel.IncomingPanel;
import org.example.Project1.View.Panel.InventoryPanel;
import org.example.Project1.View.Panel.OutgoingPanel;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {

    public MenuFrame(String title) throws Exception {
        super(title); // Title 설정
        setFrame(); // JFrame 설정
        initUI(); // UI 초기화
        setVisible(true); // 화면 표시
    }

    // JFrame 설정
    private void setFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // 레이아웃 변경: BorderLayout 사용
        setSize(1200, 675); // 프레임 사이즈 설정
        setLocationRelativeTo(null); // 화면 중간에 출력되도록 설정
    }

    // UI 초기화
    private void initUI() {
        // JTabbedPane 생성 (상단 선택 탭)
        JTabbedPane tabbedPane = new JTabbedPane();

        // 탭 생성 , 이모티콘 추가 예정
        tabbedPane.addTab("입고관리", new IncomingPanel("입고관리"));
        tabbedPane.addTab("출고관리", new OutgoingPanel("출고관리"));
        tabbedPane.addTab("재고관리", new InventoryPanel("재고관리"));
        tabbedPane.addTab("입출고내역", new HistoryPanel("입출고내역"));

        // JTabbedPane 추가 , BorderLayout 으로 만듬
        add(tabbedPane, BorderLayout.CENTER);
    }

}