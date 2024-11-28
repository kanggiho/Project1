package org.example.project1._common.View.Frame;

import org.example.project1._common.Model.DAO.AdminDAO;
import org.example.project1._common.Model.DAO.OrdererDAO;
import org.example.project1._common.Model.VO.AdminVO;
import org.example.project1._common.Model.VO.OrdererVO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField idField;
    private JPasswordField passwordField;

    public LoginFrame() {
        super("My 웨하스 - 로그인");
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 675);
        setLayout(null);
        setLocationRelativeTo(null);

        add(createTopPanel());
        add(createCenterPanel());

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBounds(0, 0, 1200, 60);
        topPanel.setBackground(Color.WHITE);

        ImageIcon resizedIcon = new ImageIcon(new ImageIcon("src/main/resources/MainLogo.png")
                .getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
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

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(500, 150, 225, 300);
        centerPanel.setBackground(Color.WHITE);

        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = createLabel("아이디", 18);
        idField = createTextField();

        JLabel passwordLabel = createLabel("비밀번호", 18);
        passwordField = createPasswordField();

        JLabel findLabel = createClickableLabel("아이디/비밀번호 찾기", () -> new RecoveryAccountFrame());

        JButton loginButton = createButton("로그인", Color.BLACK, Color.WHITE, this::handleLogin);
        JButton registerButton = createButton("회원가입", Color.WHITE, Color.BLACK, () -> new RegistrationFrame());
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(idLabel)
                        .addComponent(idField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(findLabel)
                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
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

    private JLabel createLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, fontSize));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        return passwordField;
    }

    private JLabel createClickableLabel(String text, Runnable action) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        label.setForeground(Color.GRAY);
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                action.run();
            }
        });
        return label;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void handleLogin() {
        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력해주세요.");
            return;
        }

        try {
            AdminDAO Adao = new AdminDAO();
            AdminVO Afind = Adao.idSelect(id);

            OrdererDAO Odao = new OrdererDAO();
            OrdererVO Ofind = Odao.idSelect(id);


            if (Odao.isValid(id, password)) {
                JOptionPane.showMessageDialog(this, String.format("%s님 환영합니다.", Ofind.getName()));
                new UserMenuFrame("유저 메인 화면");
                dispose();
            } else if (Adao.isValid(id,password)){
                JOptionPane.showMessageDialog(this, String.format("%s관리자님 환영합니다.", Afind.getName()));
                new AdminMenuFrame("관리자 메인 화면");
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 다시 확인해주세요.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}