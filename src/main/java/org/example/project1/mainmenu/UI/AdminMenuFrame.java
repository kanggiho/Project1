package org.example.project1.mainmenu.UI;

import org.example.project1._common.utility.ColorSet;
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
    private CardLayout cardLayout;
    private JPanel innerPanel;
    private String toss_font = "머니그라피TTF Rounded";
    private JButton currentSelectedButton;
    private StockStatusPanel stockStatusPanel;
    private StockEditPanel stockEditPanel;
    private StockUpdatePanel stockUpdatePanel;

    public AdminMenuFrame(String name) {
        setTitle("My 웨하스 - 관리자 모드");
        setSize(1200, 675);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        createTopBar(name);
        createButtonBar();
        createInnerPanel();

        setVisible(true);
    }

    private void createTopBar(String name) {
        JPanel topBar = new JPanel();
        topBar.setBounds(0, 0, 1200, 60);
        topBar.setBackground(ColorSet.color1_bold[0]);
        topBar.setLayout(null);

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

    private void createButtonBar() {
        JPanel buttonBar = new JPanel();
        buttonBar.setBounds(40, 80, 1100, 65);
        buttonBar.setBackground(Color.decode("#93BFB7"));
        buttonBar.setLayout(new GridLayout(1, 4, 0, 0));
        add(buttonBar);

        JButton btnIncoming = createButton("입고관리", ColorSet.color1_light[1]);
        JButton btnOutgoing = createButton("출고요청관리", ColorSet.color1_light[1]);
        JButton btnInventory = createButton("재고관리", ColorSet.color1_light[1]);
        JButton btnHistory = createButton("회원등급수정", ColorSet.color1_light[1]);

        buttonBar.add(btnIncoming);
        buttonBar.add(btnOutgoing);
        buttonBar.add(btnInventory);
        buttonBar.add(btnHistory);

        btnIncoming.addActionListener(new ButtonActionListener(btnIncoming, "입고관리"));
        btnOutgoing.addActionListener(new ButtonActionListener(btnOutgoing, "출고요청관리"));
        btnInventory.addActionListener(new ButtonActionListener(btnInventory, "재고관리"));
        btnHistory.addActionListener(new ButtonActionListener(btnHistory, "회원등급수정"));

        currentSelectedButton = btnIncoming;
        btnIncoming.setBackground(ColorSet.color1_bold[1]);
    }

    private void createInnerPanel() {
        innerPanel = new JPanel();
        innerPanel.setBounds(40, 145, 1100, 450);
        cardLayout = new CardLayout();
        innerPanel.setLayout(cardLayout);
        innerPanel.setBackground(Color.decode("#97A6A0"));

        innerPanel.add(createPanel("입고관리 화면"), "입고관리");
        innerPanel.add(createPanel("출고요청관리 화면"), "출고요청관리");

        stockStatusPanel = new StockStatusPanel("재고관리 화면");
        stockEditPanel = new StockEditPanel();
        stockUpdatePanel = new StockUpdatePanel();

        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.add(stockStatusPanel, BorderLayout.CENTER);

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

        stockStatusPanel.addPropertyChangeListener("rowSelected", evt -> {
            boolean rowSelected = (boolean) evt.getNewValue();
            editButton.setEnabled(rowSelected);
        });

        add(innerPanel);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font(toss_font, Font.PLAIN, 20));
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);
        return button;
    }

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

    private void openEditDialog() {
        ProductInfoProductVO selectedProduct = stockStatusPanel.getSelectedProduct();
        if (selectedProduct != null) {
            stockEditPanel.updateProductInfo(selectedProduct);
            stockStatusPanel.loadStockData(); // 테이블 데이터 새로고침
        }
    }


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