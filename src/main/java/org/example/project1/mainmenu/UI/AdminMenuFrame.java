package org.example.project1.mainmenu.UI;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.account.UI.UserGradeManagementPanel;
import org.example.project1.inout.UI.IncomingManagementPanel;
import org.example.project1.inout.UI.OutgoingConfirmPanel;
import org.example.project1.inventory.UI.InventoryManagementPanel;
import org.example.project1.inventory.UI.StockStatusPanel;
import org.example.project1.user.UI.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMenuFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel innerPanel;
    private String toss_font = "머니그라피TTF Rounded";
    private JButton currentSelectedButton; // 현재 선택된 버튼 저장

    public AdminMenuFrame(String name) throws Exception {
        // 프레임 설정
        setTitle("My 웨하스 - 관리자 모드");
        setSize(1200, 675);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // 절대 위치 사용

        // TopBar 생성
        JPanel topBar = new JPanel();
        topBar.setBounds(0, 0, 1200, 60);
        topBar.setBackground(ColorSet.color1_bold[0]);
        topBar.setLayout(null); // 절대 위치 설정

        // 이미지 로고 추가
        JLabel logo = new JLabel();
        ImageIcon resizedIcon = new ImageIcon(new ImageIcon("src/main/resources/MainLogo.png")
                .getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
        logo.setIcon(resizedIcon);
        logo.setPreferredSize(new Dimension(45, 45));
        logo.setBounds(10, 8, 45, 45);
        topBar.add(logo);

        // 큰 텍스트 레이블 (40px)
        JLabel largeTextLabel = new JLabel("My 웨하스");
        largeTextLabel.setFont(new Font(toss_font, Font.BOLD, 30));
        largeTextLabel.setForeground(Color.WHITE);
        largeTextLabel.setBounds(70, 8, 300, 50); // 텍스트 위치 조정
        topBar.add(largeTextLabel);

        // 작은 텍스트 레이블 (20px)
        JLabel smallTextLabel = new JLabel("관리자 모드입니다 - %s".formatted(name));
        smallTextLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        smallTextLabel.setForeground(Color.WHITE);
        smallTextLabel.setBounds(230, 23, 200, 30);
        topBar.add(smallTextLabel);

        JLabel logoutLabel = new JLabel("로그아웃");
        logoutLabel.setBounds(1080, 27, 70, 30);
        logoutLabel.setForeground(Color.WHITE);
        topBar.add(logoutLabel);
        logoutLabel.setFont(new Font(toss_font, Font.PLAIN, 14));

        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginFrame();
                dispose();
                super.mouseClicked(e);
            }
        });


        add(topBar);

        // ButtonBar 생성
        JPanel buttonBar = new JPanel();
        buttonBar.setBounds(40, 80, 1100, 65);
        buttonBar.setBackground(Color.decode("#93BFB7"));
        buttonBar.setLayout(new GridLayout(1, 4, 0, 0)); // 버튼 간 간격 제거
        add(buttonBar);

        // 버튼 생성 및 추가
        JButton btnIncoming = createButton("입고관리", ColorSet.color1_light[1]);
        JButton btnOutgoing = createButton("출고요청관리", ColorSet.color1_light[1]);
        JButton btnInventory = createButton("재고관리", ColorSet.color1_light[1]);
        JButton btnHistory = createButton("회원등급수정", ColorSet.color1_light[1]);

        buttonBar.add(btnIncoming);
        buttonBar.add(btnOutgoing);
        buttonBar.add(btnInventory);
        buttonBar.add(btnHistory);

        // InnerPanel 생성
        innerPanel = new JPanel();
        innerPanel.setBounds(40, 145, 1100, 450);
        cardLayout = new CardLayout();
        innerPanel.setLayout(cardLayout);
        innerPanel.setBackground(Color.decode("#97A6A0"));


        IncomingManagementPanel incomingManagementPanel = new IncomingManagementPanel();
        OutgoingConfirmPanel outgoingConfirmPanel = new OutgoingConfirmPanel();
        InventoryManagementPanel inventoryManagementPanel =new InventoryManagementPanel("재고관리",this);
        UserGradeManagementPanel userGradeManagementPanel =new UserGradeManagementPanel("회원등급수정");


        // 각 패널 추가
        innerPanel.add(incomingManagementPanel, "입고관리");
        innerPanel.add(outgoingConfirmPanel, "출고요청관리");
        innerPanel.add(inventoryManagementPanel, "재고관리");
        innerPanel.add(userGradeManagementPanel, "회원등급수정");
        add(innerPanel);

        // 버튼 클릭 이벤트
        btnIncoming.addActionListener(new ButtonActionListener(btnIncoming, "입고관리"));
        btnOutgoing.addActionListener(new ButtonActionListener(btnOutgoing, "출고요청관리"));
        btnInventory.addActionListener(new ButtonActionListener(btnInventory, "재고관리"));
        btnHistory.addActionListener(new ButtonActionListener(btnHistory, "회원등급수정"));

        // 초기 선택된 버튼 설정
        currentSelectedButton = btnIncoming;
        btnIncoming.setBackground(ColorSet.color1_bold[1]);
        setVisible(true);
    }

    // 버튼 생성 메서드
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font(toss_font, Font.PLAIN, 20));
        button.setBackground(bgColor); // 배경색 설정
        button.setOpaque(true);
        button.setBorderPainted(false); // 버튼 테두리 제거
        button.setForeground(Color.WHITE); // 글자색 흰색
        return button;
    }

    // 패널 생성 메서드
    private JPanel createPanel(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font(toss_font, Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.CENTER);
        panel.setBackground(ColorSet.color1_light[0]);
        return panel;
    }

    // 버튼 액션 리스너 클래스
    private class ButtonActionListener implements ActionListener {
        private final JButton button;
        private final String panelName;

        public ButtonActionListener(JButton button, String panelName) {
            this.button = button;
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 이전 버튼의 색상 초기화
            if (currentSelectedButton != null) {
                currentSelectedButton.setBackground(ColorSet.color1_light[1]);
            }
            // 현재 버튼의 색상 변경
            button.setBackground(ColorSet.color1_bold[1]);
            currentSelectedButton = button;

            // 패널 전환
            cardLayout.show(innerPanel, panelName);
        }
    }
}