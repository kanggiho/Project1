package org.example.project1.mainmenu.UI;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.inventory.UI.StockSearchPanel;
import org.example.project1.inventory.UI.StockStatusPanel;
import org.example.project1.inventory.UI.StockEditPanel;
import org.example.project1.inventory.UI.StockUpdatePanel;
import org.example.project1.inventory.VO.ProductInfoProductVO;
import org.example.project1.user.UI.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMenuFrame extends JFrame {
    // 클래스 멤버 변수 선언
    private CardLayout cardLayout;
    private JPanel innerPanel;
    private String toss_font = "머니그라피TTF Rounded";
    private JButton currentSelectedButton;
    private StockStatusPanel stockStatusPanel;
    private StockEditPanel stockEditPanel;
    private StockUpdatePanel stockUpdatePanel;

    // 생성자
    public AdminMenuFrame(String name) {
        // 프레임 기본 설정
        setTitle("My 웨하스 - 관리자 모드");
        setSize(1200, 675);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
// UI 구성요소 생성
        createTopBar(name);
        createButtonBar();
        createInnerPanel();

        setVisible(true);
    }

    // 상단 바 생성 메서드
    private void createTopBar(String name) {
        // 상단 바 패널 생성 및 설정
        JPanel topBar = new JPanel();
        topBar.setBounds(0, 0, 1200, 60);
        topBar.setBackground(ColorSet.color1_bold[0]);
        topBar.setLayout(null);
        // 로고 추가
        JLabel logo = new JLabel();
        ImageIcon resizedIcon = new ImageIcon(new ImageIcon("src/main/resources/MainLogo.png")
                .getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
        logo.setIcon(resizedIcon);
        logo.setBounds(10, 8, 45, 45);
        topBar.add(logo);

        JLabel largeTextLabel = new JLabel("My 웨하스");
        largeTextLabel.setFont(new Font(toss_font, Font.BOLD, 30));
        largeTextLabel.setForeground(Color.WHITE);
        largeTextLabel.setBounds(70, 8, 300, 50);
        topBar.add(largeTextLabel);

        JLabel smallTextLabel = new JLabel("관리자 모드입니다 - %s".formatted(name));
        smallTextLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        smallTextLabel.setForeground(Color.WHITE);
        smallTextLabel.setBounds(230, 23, 200, 30);
        topBar.add(smallTextLabel);
// 로그아웃 라벨 추가
        JLabel logoutLabel = new JLabel("로그아웃");
        logoutLabel.setBounds(1080, 27, 70, 30);
        logoutLabel.setForeground(Color.WHITE);
        logoutLabel.setFont(new Font(toss_font, Font.PLAIN, 14));
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new LoginFrame();
                dispose();
            }
        });
        topBar.add(logoutLabel);

        add(topBar);
    }
    // 버튼 바 생성 메서드
    private void createButtonBar() {
        JPanel buttonBar = new JPanel();
        buttonBar.setBounds(40, 80, 1100, 65);
        buttonBar.setBackground(Color.decode("#93BFB7"));
        buttonBar.setLayout(new GridLayout(1, 4, 0, 0));
        add(buttonBar);
        // 각 기능 버튼 생성
        JButton btnIncoming = createButton("입고관리", ColorSet.color1_light[1]);
        JButton btnOutgoing = createButton("출고요청관리", ColorSet.color1_light[1]);
        JButton btnInventory = createButton("재고관리", ColorSet.color1_light[1]);
        JButton btnHistory = createButton("회원등급수정", ColorSet.color1_light[1]);
// 버튼을 패널에 추가
        buttonBar.add(btnIncoming);
        buttonBar.add(btnOutgoing);
        buttonBar.add(btnInventory);
        buttonBar.add(btnHistory);
// 각 버튼에 액션 리스너 추가

        btnIncoming.addActionListener(new ButtonActionListener(btnIncoming, "입고관리"));
        btnOutgoing.addActionListener(new ButtonActionListener(btnOutgoing, "출고요청관리"));
        btnInventory.addActionListener(new ButtonActionListener(btnInventory, "재고관리"));
        btnHistory.addActionListener(new ButtonActionListener(btnHistory, "회원등급수정"));
        // 초기 선택 버튼 설정
        currentSelectedButton = btnIncoming;
        btnIncoming.setBackground(ColorSet.color1_bold[1]);
    }
    // 내부 패널 생성 메서드
    private void createInnerPanel() {
        // 내부 패널 생성 및 설정
        innerPanel = new JPanel();
        innerPanel.setBounds(40, 145, 1100, 450);
        cardLayout = new CardLayout();
        innerPanel.setLayout(cardLayout);
        innerPanel.setBackground(Color.decode("#97A6A0"));
        // 각 기능별 패널 추가

        innerPanel.add(createPanel("입고관리 화면"), "입고관리");
        innerPanel.add(createPanel("출고요청관리 화면"), "출고요청관리");
//재고 관리 관련 패널 추가
        stockStatusPanel = new StockStatusPanel("재고관리 화면");
        stockEditPanel = new StockEditPanel();
        stockUpdatePanel = new StockUpdatePanel();
        StockSearchPanel stockSearchPanel = new StockSearchPanel(stockStatusPanel);


        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.add(stockSearchPanel, BorderLayout.NORTH);
        inventoryPanel.add(stockStatusPanel, BorderLayout.CENTER);
//버튼 패널 생성 및 버튼 추가
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(e -> stockStatusPanel.loadStockData());
        buttonPanel.add(refreshButton);

        JButton editButton = new JButton("선택 항목 수정");
        editButton.addActionListener(e -> openEditDialog());
        buttonPanel.add(editButton);

        JButton orderButton = new JButton("발주하기");
        orderButton.addActionListener(e -> openOrderDialog());
        buttonPanel.add(orderButton);

        inventoryPanel.add(buttonPanel, BorderLayout.SOUTH);

        innerPanel.add(inventoryPanel, "재고관리");
        innerPanel.add(createPanel("회원등급수정 화면"), "회원등급수정");
//행 선택시 수정 버튼 활성화
        stockStatusPanel.addPropertyChangeListener("rowSelected", evt -> {
            boolean rowSelected = (boolean) evt.getNewValue();
            editButton.setEnabled(rowSelected);
        });

        add(innerPanel);
    }
//버튼 생성 메서드
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font(toss_font, Font.PLAIN, 20));
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        return button;
    }
    // 기본 패널 생성 메서드
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
    //발주 다이얼로그 열기 메서드
    private void openOrderDialog() {
        JDialog orderDialog = new JDialog(this, "발주하기", true);
        orderDialog.setContentPane(stockUpdatePanel);
        orderDialog.pack();
        orderDialog.setLocationRelativeTo(this);

        // 발주 완료 후 새로 고침을 위한 리스너 추가
        stockUpdatePanel.addPropertyChangeListener("stockUpdated", evt -> {
            stockStatusPanel.loadStockData();
            orderDialog.dispose();
        });

        orderDialog.setVisible(true);

        // 다이얼로그가 닫힌 후 재고 목록 새로고침
        stockStatusPanel.loadStockData();
    }
    // 수정 다이얼로그 열기 메서드
    private void openEditDialog() {
        try {
            ProductInfoProductVO selectedProduct = stockStatusPanel.getSelectedProduct();
            if (selectedProduct != null) {
                stockEditPanel.updateProductInfo(selectedProduct);
                stockStatusPanel.loadStockData(); // 데이터 갱신
            } else {
                JOptionPane.showMessageDialog(this, "수정할 항목을 선택해주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "데이터 형식 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류 발생: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
    // 버튼 액션 리스너 내부 클래스

    private class ButtonActionListener implements ActionListener {
        private final JButton button;
        private final String panelName;

        public ButtonActionListener(JButton button, String panelName) {
            this.button = button;
            this.panelName = panelName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentSelectedButton != null) {
                currentSelectedButton.setBackground(ColorSet.color1_light[1]);
            }
            button.setBackground(ColorSet.color1_bold[1]);
            currentSelectedButton = button;
            cardLayout.show(innerPanel, panelName);
        }
    }
}