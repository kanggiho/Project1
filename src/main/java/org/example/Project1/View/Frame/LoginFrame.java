package org.example.Project1.View.Frame;

import org.example.Project1.Model.DAO.OrdererDAO;
import org.example.Project1.Model.VO.OrdererVO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {


    JTextField idField;
    JPasswordField passwordField;


    public LoginFrame() {
        super("My 웨하스 - 로그인"); // JFrame의 제목 설정
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 675); // 프레임 크기 설정
        setLayout(null);
        setLocationRelativeTo(null); // 화면 중앙에 표시

        // 상단 로고 및 프로그램 네임
        JPanel topPanel = createTopPanel();
        add(topPanel);

        // 중앙 패널
        JPanel centerPanel = createCenterPanel();
        add(centerPanel);

        // 화면 보이기
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBounds(0, 0, 1200, 60);
        topPanel.setBackground(Color.WHITE);

        // 로고 이미지
        ImageIcon originalIcon = new ImageIcon("src/main/resources/MainLogo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(resizedIcon);

        JLabel programTitle = new JLabel("My 웨하스");
        programTitle.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 30));
        programTitle.setForeground(Color.BLACK);

        JLabel versionTitle = new JLabel("ver 1.0.0");
        versionTitle.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        versionTitle.setForeground(Color.GRAY);

        topPanel.add(logoLabel);
        topPanel.add(programTitle);
        topPanel.add(versionTitle);

        return topPanel;
    }

    private void handleLogin() {
        String temp_id = idField.getText();
        String temp_pw = new String(passwordField.getPassword());

        try {
            OrdererDAO dao = new OrdererDAO(); // DAO 객체 선언
            OrdererVO ovo = dao.idSelect(temp_id);
            if (dao.isValid(temp_id, temp_pw)) {
                JOptionPane.showMessageDialog(this, "%s님 환영합니다.".formatted(ovo.getName()));
                new UserMenuFrame("메인 화면");
                dispose(); // 현재 창 닫기
            } else {
                JOptionPane.showMessageDialog(this, "다시 확인해주세요.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(500, 150, 225, 300);
        centerPanel.setBackground(Color.WHITE);

        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // 아이디 입력 필드
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        idField = new JTextField();
        idField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        idField.setPreferredSize(new Dimension(200, 30));

        // 비밀번호 입력 필드
        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));

        // 비밀번호 찾기
        JLabel findLabel = new JLabel("아이디/비밀번호 찾기");
        findLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        findLabel.setForeground(Color.GRAY);
        findLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RecoveryAccountFrame();
                //JOptionPane.showMessageDialog(LoginFrame.this, "아이디/비밀번호 찾기 페이지로 이동합니다.");
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
                JOptionPane.showMessageDialog(LoginFrame.this, "아이디와 비밀번호를 입력해주세요.");
            } else {
                handleLogin();
            }
        });

        // 회원가입 버튼
        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 14));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        registerButton.addActionListener(e -> new RegistrationFrame());

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

        return centerPanel;
    }
}
