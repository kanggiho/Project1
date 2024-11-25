package org.example.Project1.__Main;

import javax.swing.*;
import java.awt.*;
public class RegistrationForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("<프로그램 네임>");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 675);
            frame.setLayout(null);
            // 상단 패널
            JPanel topPanel = new JPanel();
            topPanel.setBackground(new Color(80, 113, 158));
            topPanel.setBounds(0, 0, 1200, 60);
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
            JLabel logo = new JLabel();
            logo.setIcon(new ImageIcon("path/to/logo.png")); // 로고 이미지 경로 설정
            logo.setPreferredSize(new Dimension(40, 40));
            JLabel title = new JLabel("<프로그램 네임>");
            title.setFont(new Font("머니그라피", Font.PLAIN, 24));
            title.setForeground(Color.BLACK);
            topPanel.add(logo);
            topPanel.add(title);
            frame.add(topPanel);
            // 회원가입 타이틀
            JLabel signupLabel = new JLabel("회원가입", SwingConstants.CENTER);
            signupLabel.setBounds(254, 119, 132, 48);
            signupLabel.setFont(new Font("머니그라피", Font.PLAIN, 32));
            signupLabel.setForeground(Color.BLACK);
            frame.add(signupLabel);
            // 오른쪽 입력 폼
            JPanel rightPanel = new JPanel(null);
            rightPanel.setBounds(614, 203, 324, 270);
            frame.add(rightPanel);
            addInputField(rightPanel, "비밀번호", "사용할 비밀번호를 입력하세요.", 0);
            addInputField(rightPanel, "전화번호", "전화번호를 입력하세요.", 105);
            addInputField(rightPanel, "사업자등록주소지", "사업자등록주소지를 입력하세요.", 210);
            // 왼쪽 입력 폼
            JPanel leftPanel = new JPanel(null);
            leftPanel.setBounds(261, 205, 324, 268);
            frame.add(leftPanel);
            addInputField(leftPanel, "아이디", "사용할 아이디를 입력하세요.", 0);
            addInputField(leftPanel, "이름", "이름을 입력하세요.", 104);
            addInputField(leftPanel, "사업자등록번호", "사업자등록번호를 입력하세요.", 208);
            // 가입 완료 버튼
            JButton completeButton = new JButton("가입 완료");
            completeButton.setBounds(455, 543, 290, 41);
            completeButton.setFont(new Font("머니그라피", Font.PLAIN, 16));
            completeButton.setBackground(Color.BLACK);
            completeButton.setForeground(Color.WHITE);
            completeButton.setFocusPainted(false);
            completeButton.setBorderPainted(false);
            completeButton.setOpaque(true);
            frame.add(completeButton);
            frame.setVisible(true);
        });
    }
    private static void addInputField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("머니그라피", Font.PLAIN, 14));
        fieldLabel.setBounds(0, yPosition, 324, 20);
        panel.add(fieldLabel);
        JPanel inputWrapper = new JPanel(new BorderLayout());
        inputWrapper.setBounds(0, yPosition + 25, 324, 35);
        inputWrapper.setBackground(Color.WHITE);
        inputWrapper.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0.1f)));
        inputWrapper.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 8));
        JLabel placeholderLabel = new JLabel(placeholder);
        placeholderLabel.setFont(new Font("머니그라피", Font.PLAIN, 14));
        placeholderLabel.setForeground(new Color(0, 0, 0, 0.5f));
        inputWrapper.add(placeholderLabel);
        panel.add(inputWrapper);
    }
}