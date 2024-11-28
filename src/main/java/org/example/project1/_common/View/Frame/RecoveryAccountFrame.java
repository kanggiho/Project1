package org.example.project1._common.View.Frame;

import org.example.project1._common.Model.DAO.OrdererDAO;
import org.example.project1._common.Model.VO.OrdererVO;

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
    private final String GOOGLE_ACCOUNT = "kgh8685@gmail.com";
    private final String GOOGLE_PASSWORD = "xlgx dgci swbn vfzy";
    private final String GOOGLE_HOST = "smtp.gmail.com";


    public RecoveryAccountFrame() {
        setUI();
    }

    private void setUI() {
        setTitle("My 웨하스 - 아이디/비밀번호 찾기");
        setSize(1200, 675);
        setLayout(null);
        setLocationRelativeTo(null);

        // 상단 패널
        JPanel topPanel = createTopPanel();
        add(topPanel);

        // 라벨 스타일의 RadioButton
        JPanel radioPanel = createStyledRadioButtonPanel();
        add(radioPanel);

        // 입력 필드 패널
        JPanel inputPanel = createInputPanel();
        add(inputPanel);

        // 버튼 패널
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel);

        setVisible(true);
    }

    private boolean sendRecoveryEmail(String userEmail, String content) {
        final String senderEmail = GOOGLE_ACCOUNT; // 발신자 Gmail 주소
        final String senderPassword = GOOGLE_PASSWORD; // 앱 비밀번호
        final String host = GOOGLE_HOST;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // STARTTLS 활성화
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // SMTP 포트 (TLS)

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // 이메일 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("아이디/비밀번호 찾기");
            message.setText(content);

            // 이메일 전송
            Transport.send(message);

            return true; // 전송 성공
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // 전송 실패
        }
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(80, 113, 158));
        topPanel.setBounds(0, 0, 1200, 60);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel logo = new JLabel();
        ImageIcon originalIcon = new ImageIcon("src/main/resources/MainLogo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        logo.setIcon(resizedIcon);
        logo.setPreferredSize(new Dimension(45, 45));

        JLabel title = new JLabel("My 웨하스 - 아이디/비밀번호 찾기");
        title.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 24));
        title.setForeground(Color.WHITE);

        topPanel.add(logo);
        topPanel.add(title);

        return topPanel;
    }

    private JPanel createStyledRadioButtonPanel() {
        JPanel radioPanel = new JPanel();
        radioPanel.setBounds(400, 170, 400, 50);
        radioPanel.setLayout(new GridLayout(1, 2, 20, 0));

        findIdButton = createStyledRadioButton("아이디 찾기", Color.DARK_GRAY, Color.LIGHT_GRAY);
        findPwButton = createStyledRadioButton("비밀번호 찾기", Color.LIGHT_GRAY, Color.DARK_GRAY);

        findIdButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectStyledRadioButton(findIdButton, findPwButton);
                toggleInputFields(true);
            }
        });

        findPwButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectStyledRadioButton(findPwButton, findIdButton);
                toggleInputFields(false);
            }
        });

        radioPanel.add(findIdButton);
        radioPanel.add(findPwButton);

        return radioPanel;
    }

    private JLabel createStyledRadioButton(String text, Color backgroundColor, Color foregroundColor) {
        JLabel button = new JLabel(text, SwingConstants.CENTER);
        button.setFont(new Font("머니그라피TTF Rounded", Font.BOLD, 18));
        button.setOpaque(true);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    private void selectStyledRadioButton(JLabel selected, JLabel other) {
        selected.setBackground(Color.DARK_GRAY);
        selected.setForeground(Color.LIGHT_GRAY);
        other.setBackground(Color.LIGHT_GRAY);
        other.setForeground(Color.DARK_GRAY);
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(400, 280, 400, 200);
        inputPanel.setLayout(null);

        // 이메일 필드
        emailLabel = new JLabel("이메일");
        emailLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        emailLabel.setBounds(0, 0, 80, 25);
        inputPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        emailField.setBounds(90, 0, 300, 30);
        inputPanel.add(emailField);

        // 아이디 필드 (비밀번호 찾기에만 표시)
        idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 18));
        idLabel.setBounds(0, 50, 80, 25);
        inputPanel.add(idLabel);

        idField = new JTextField();
        idField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        idField.setBounds(90, 50, 300, 30);
        inputPanel.add(idField);

        toggleInputFields(true); // 기본값: 아이디 찾기 선택

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(455, 500, 290, 50); // 중앙 정렬
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0)); // 버튼 간격 추가

        completeButton = new JButton("완료");
        completeButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        completeButton.setBackground(Color.BLACK);
        completeButton.setForeground(Color.WHITE);
        completeButton.setFocusPainted(false);
        completeButton.setPreferredSize(new Dimension(290, 41));
        completeButton.addActionListener(e -> handleRecoveryAction());

        cancelButton = new JButton("취소");
        cancelButton.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(290, 41));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(completeButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void toggleInputFields(boolean isFindId) {
        idLabel.setVisible(!isFindId);
        idField.setVisible(!isFindId);
    }


    private void handleRecoveryAction() {
        String foundId = "exampleID"; // 데이터베이스에서 조회된 아이디
        String foundPassword = "examplePassword"; // 데이터베이스에서 조회된 비밀번호

        String email = emailField.getText();
        if (findIdButton.getBackground().equals(Color.DARK_GRAY)) { // 아이디 찾기
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "이메일을 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: DB에서 이메일로 아이디 조회
            boolean success;

            try {
                OrdererDAO dao = new OrdererDAO();
                OrdererVO vo = dao.emailSelect(email);
                foundId = vo.getId();
                if (foundId == null) {
                    JOptionPane.showMessageDialog(this, "해당 정보가 없습니다.");
                    return;
                } else {
                    success = true;
                }

            } catch (Exception e) {

            }

            success = sendRecoveryEmail(email, "찾으신 아이디는 다음과 같습니다:\n\n아이디: " + foundId);
            if (success) {
                JOptionPane.showMessageDialog(this, "아이디가 이메일로 전송되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "이메일 전송에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        } else { // 비밀번호 찾기
            String id = idField.getText();
            if (email.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "이메일과 아이디를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // TODO: DB에서 이메일과 아이디로 비밀번호 조회
            boolean success;

            try {
                OrdererDAO dao = new OrdererDAO();
                OrdererVO vo = dao.idemailSelect(id, email);
                foundPassword = vo.getPassword();
                if (foundPassword == null) {
                    JOptionPane.showMessageDialog(this, "해당 정보가 없습니다.");
                    return;
                } else {
                    success = true;
                }


            } catch (Exception e) {

            }


            success = sendRecoveryEmail(email, "찾으신 비밀번호는 다음과 같습니다:\n\n비밀번호: " + foundPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "비밀번호가 이메일로 전송되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "이메일 전송에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}