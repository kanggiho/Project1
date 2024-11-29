package org.example.project1.mainmenu.UI;

import org.example.project1.inventory.UI.InventoryManagementPanel;
import org.example.project1.inout.UI.OutgoingConfirmPanel;
import org.example.project1.account.UI.UserGradeManagementPanel;
import org.example.project1.inout.UI.IncomingManagementPanel;

import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    public AdminMenuFrame(String title) throws Exception {
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
        tabbedPane.addTab("입고관리", new IncomingManagementPanel("입고관리"));
        tabbedPane.addTab("출고요청관리", new OutgoingConfirmPanel("출고요청관리"));
        tabbedPane.addTab("재고관리", new InventoryManagementPanel("재고관리"));
        tabbedPane.addTab("회원등급관리", new UserGradeManagementPanel("회원등급관리"));

        // JTabbedPane 추가 , BorderLayout 으로 만듬
        add(tabbedPane, BorderLayout.CENTER);
    }

}