package org.example.Project1.__Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegistrationForm {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("마이 웨어하우스");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 675);
            frame.setLayout(null);

            // 상단 패널
            JPanel topPanel = new JPanel();
            topPanel.setBackground(new Color(80, 113, 158));
            topPanel.setBounds(0, 0, 1200, 60);
            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
            JLabel logo = new JLabel();
            ImageIcon originalIcon = new ImageIcon("src/main/resources/MainLogo.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            logo.setIcon(resizedIcon); // 로고 이미지 경로 설정

            logo.setPreferredSize(new Dimension(40, 40));
            JLabel title = new JLabel("마이 웨어하우스");
            title.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 24));
            title.setForeground(Color.BLACK);
            topPanel.add(logo);
            topPanel.add(title);
            frame.add(topPanel);

            // 회원가입 타이틀
            JLabel signupLabel = new JLabel("회원가입", SwingConstants.CENTER);
            signupLabel.setBounds(254, 119, 132, 48);
            signupLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 32));
            signupLabel.setForeground(Color.BLACK);
            frame.add(signupLabel);

            // 오른쪽 입력 폼
            JPanel rightPanel = new JPanel(null);
            rightPanel.setBounds(614, 205, 324, 270);
            frame.add(rightPanel);
            addInputField(rightPanel, "비밀번호", "사용할 비밀번호를 입력하세요.", 0);
            addInputField(rightPanel, "전화번호", "전화번호를 입력하세요.", 105);
            addInputField(rightPanel, "사업자등록주소지", "사업자등록주소지를 입력하세요.", 210);

            // 왼쪽 입력 폼
            JPanel leftPanel = new JPanel(null);
            leftPanel.setBounds(261, 205, 324, 270);
            frame.add(leftPanel);
            addInputField(leftPanel, "아이디", "사용할 아이디를 입력하세요.", 0);
            addInputField(leftPanel, "이름", "이름을 입력하세요.", 105);
            addInputField(leftPanel, "사업자등록번호", "사업자등록번호를 입력하세요.", 210);

            // 가입 완료 버튼
            JButton completeButton = new JButton("가입 완료");
            completeButton.setBounds(455, 543, 290, 41);
            completeButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
            completeButton.setBackground(Color.BLACK);
            completeButton.setForeground(Color.WHITE);
            completeButton.setFocusPainted(false);
            completeButton.setBorderPainted(false);
            completeButton.setOpaque(true);
            frame.add(completeButton);

            // 가입 완료 버튼 클릭 이벤트
            completeButton.addActionListener(e -> {
                // TODO: 입력된 데이터를 처리 (예: DB 저장 또는 검증)
                JOptionPane.showMessageDialog(frame, "회원가입 완료!");
            });

            frame.setVisible(true);
        });
    }

    private static void addInputField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        fieldLabel.setBounds(0, yPosition, 324, 20);
        panel.add(fieldLabel);

        JTextField inputField = new JTextField(placeholder);
        inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        inputField.setForeground(new Color(0, 0, 0, 0.5f));
        inputField.setBounds(0, yPosition + 25, 324, 35);
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Placeholder 기능 구현
        inputField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (inputField.getText().equals(placeholder)) {
                    inputField.setText("");
                    inputField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputField.getText().isEmpty()) {
                    inputField.setText(placeholder);
                    inputField.setForeground(new Color(0, 0, 0, 0.5f));
                }
            }
        });

        panel.add(inputField);
    }
}
