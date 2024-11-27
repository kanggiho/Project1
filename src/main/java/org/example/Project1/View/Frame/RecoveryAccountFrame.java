package org.example.Project1.View.Frame;

import org.example.Project1.Model.DAO.OrdererDAO;
import org.example.Project1.Model.VO.OrdererVO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class RecoveryAccountFrame extends JFrame {

    private JLabel findIdButton, findPwButton;
    private JTextField emailField, idField;
    private JButton completeButton, cancelButton;
    private JLabel emailLabel, idLabel;

    private static final String GOOGLE_ACCOUNT = "kgh8685@gmail.com";
    private static final String GOOGLE_PASSWORD = "xlgx dgci swbn vfzy";
    private static final String GOOGLE_HOST = "smtp.gmail.com";

    public RecoveryAccountFrame() {
        setupUI();
    }

    private void setupUI() {
        setTitle("My 웨하스 - 아이디/비밀번호 찾기");
        setSize(1200, 675);
        setLayout(null);
        setLocationRelativeTo(null);

        add(createTopPanel());
        add(createStyledRadioButtonPanel());
        add(createInputPanel());
        add(createButtonPanel());

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBounds(0, 0, 1200, 60);
        topPanel.setBackground(new Color(80, 113, 158));

        JLabel logo = createLogoLabel();
        JLabel title = createTitleLabel("My 웨하스 - 아이디/비밀번호 찾기");

        topPanel.add(logo);
        topPanel.add(title);

        return topPanel;
    }

    private JLabel createLogoLabel() {
        ImageIcon resizedIcon = new ImageIcon(new ImageIcon("src/main/resources/MainLogo.png")
                .getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
        JLabel logo = new JLabel(resizedIcon);
        logo.setPreferredSize(new Dimension(45, 45));
        return logo;
    }

    private JLabel createTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 24));
        title.setForeground(Color.WHITE);
        return title;
    }

    private JPanel createStyledRadioButtonPanel() {
        JPanel radioPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        radioPanel.setBounds(400, 170, 400, 50);

        findIdButton = createStyledRadioButton("아이디 찾기", Color.DARK_GRAY, Color.LIGHT_GRAY, true);
        findPwButton = createStyledRadioButton("비밀번호 찾기", Color.LIGHT_GRAY, Color.DARK_GRAY, false);

        radioPanel.add(findIdButton);
        radioPanel.add(findPwButton);

        return radioPanel;
    }

    private JLabel createStyledRadioButton(String text, Color bgColor, Color fgColor, boolean isSelected) {
        JLabel button = new JLabel(text, SwingConstants.CENTER);
        button.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 18));
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                toggleRadioButtons(button == findIdButton);
            }
        });

        if (isSelected) {
            toggleRadioButtons(true);
        }

        return button;
    }

    private void toggleRadioButtons(boolean isFindId) {
        setButtonStyle(findIdButton, isFindId);
        setButtonStyle(findPwButton, !isFindId);
        toggleInputFields(isFindId);
    }

    private void setButtonStyle(JLabel button, boolean isSelected) {
        button.setBackground(isSelected ? Color.DARK_GRAY : Color.LIGHT_GRAY);
        button.setForeground(isSelected ? Color.LIGHT_GRAY : Color.DARK_GRAY);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(400, 280, 400, 200);

        emailLabel = createLabel("이메일", 0, 0);
        emailField = createTextField(90, 0);

        idLabel = createLabel("아이디", 0, 50);
        idField = createTextField(90, 50);

        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        toggleInputFields(true);
        return inputPanel;
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        label.setBounds(x, y, 80, 25);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        textField.setBounds(x, y, 300, 30);
        return textField;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBounds(455, 500, 290, 50);

        completeButton = createButton("완료", e -> handleRecoveryAction());
        cancelButton = createButton("취소", e -> dispose());

        buttonPanel.add(completeButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private JButton createButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(290, 41));
        button.addActionListener(action);
        return button;
    }

    private void toggleInputFields(boolean isFindId) {
        idLabel.setVisible(!isFindId);
        idField.setVisible(!isFindId);
    }

    private void handleRecoveryAction() {
        String email = emailField.getText();
        String id = idField.getText();

        if (findIdButton.getBackground().equals(Color.DARK_GRAY)) {
            handleIdRecovery(email);
        } else {
            handlePwRecovery(email, id);
        }
    }

    private void handleIdRecovery(String email) {
        if (email.isEmpty()) {
            showErrorMessage("이메일을 입력해주세요.");
            return;
        }

        try {
            OrdererDAO dao = new OrdererDAO();
            OrdererVO vo = dao.emailSelect(email);

            if (vo != null) {
                sendRecoveryEmail(email, "찾으신 아이디는 다음과 같습니다:\n\n아이디: " + vo.getId());
                JOptionPane.showMessageDialog(this, "아이디가 이메일로 전송되었습니다.");
            } else {
                showErrorMessage("해당 정보가 없습니다.");
            }
        } catch (Exception e) {
            showErrorMessage("오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void handlePwRecovery(String email, String id) {
        if (email.isEmpty() || id.isEmpty()) {
            showErrorMessage("이메일과 아이디를 입력해주세요.");
            return;
        }

        try {
            OrdererDAO dao = new OrdererDAO();
            OrdererVO vo = dao.idemailSelect(id, email);

            if (vo != null) {
                sendRecoveryEmail(email, "찾으신 비밀번호는 다음과 같습니다:\n\n비밀번호: " + vo.getPassword());
                JOptionPane.showMessageDialog(this, "비밀번호가 이메일로 전송되었습니다.");
            } else {
                showErrorMessage("해당 정보가 없습니다.");
            }
        } catch (Exception e) {
            showErrorMessage("오류가 발생했습니다: " + e.getMessage());
        }
    }

    private boolean sendRecoveryEmail(String recipient, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", GOOGLE_HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GOOGLE_ACCOUNT, GOOGLE_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(GOOGLE_ACCOUNT));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("아이디/비밀번호 찾기");
            message.setText(content);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
    }
}
