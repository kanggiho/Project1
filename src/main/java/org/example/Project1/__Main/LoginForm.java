package org.example.Project1.__Main;

import javax.swing.*;
import java.awt.*;

public class LoginForm {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("마이 웨어하우스");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 675); // 프레임 크기 설정
        frame.setLayout(null);

        // 상단 로고 및 프로그램 네임
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBounds(0, 0, 1200, 60);
        topPanel.setBackground(Color.WHITE);

        // 로고 이미지
        ImageIcon originalIcon = new ImageIcon("src/main/resources/MainLogo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(resizedIcon);

        JLabel programTitle = new JLabel("마이 웨어하우스");
        programTitle.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 30));
        programTitle.setForeground(Color.BLACK);

        topPanel.add(logoLabel);
        topPanel.add(programTitle);
        frame.add(topPanel);

        // 중앙 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(500, 150, 225, 300); // 중앙 패널 크기
        centerPanel.setBackground(Color.WHITE);

        // GroupLayout 설정
        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true); // 컴포넌트 간 간격 자동 생성
        layout.setAutoCreateContainerGaps(true); // 컨테이너와 컴포넌트 간 간격 생성

        // 아이디 입력
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        JTextField idField = new JTextField();
        idField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        idField.setPreferredSize(new Dimension(200, 30));

        // 비밀번호 입력
        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));

        // 비밀번호 찾기 버튼
        JLabel findLabel = new JLabel("아이디/비밀번호 찾기");
        findLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        findLabel.setForeground(Color.GRAY);

        findLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JOptionPane.showMessageDialog(frame, "아이디/비밀번호 찾기 페이지로 이동합니다.");
            }
        });

        // 로그인 버튼
        JButton loginButton = new JButton("로그인");
        loginButton.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 14));
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            if (id.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "아이디와 비밀번호를 입력해주세요.");
            } else {
                JOptionPane.showMessageDialog(frame, "로그인 성공!\n아이디: " + id);
            }
        });

        // 회원가입 버튼
        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 14));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        registerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "회원가입 페이지로 이동합니다.");
        });

        // GroupLayout으로 컴포넌트 배치
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(idLabel)
                                        .addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        )
                        .addComponent(findLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        )
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(idLabel)
                        .addComponent(idField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(20)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(findLabel)
                        .addGap(20)
                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        );

        frame.add(centerPanel);

        // 화면 보이기
        frame.setVisible(true);
    }
}
