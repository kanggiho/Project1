package org.example.test.waste;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PasswordRecoveryFrame extends JFrame {
    final String GOOGLE_ACCOUNT = "kgh8685@gmail.com";

    public PasswordRecoveryFrame() {

        super("아이디/비밀번호 찾기");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // 이메일 입력 필드
        JLabel emailLabel = new JLabel("등록된 이메일:");
        emailLabel.setBounds(50, 50, 100, 25);
        add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 50, 200, 25);
        add(emailField);

        // 결과 메시지 출력
        JLabel resultLabel = new JLabel("");
        resultLabel.setBounds(50, 150, 300, 25);
        resultLabel.setForeground(Color.RED);
        add(resultLabel);

        // 전송 버튼
        JButton sendButton = new JButton("전송");
        sendButton.setBounds(150, 100, 100, 30);
        add(sendButton);

        sendButton.addActionListener(e -> {
            String userEmail = emailField.getText();
            if (userEmail.isEmpty()) {
                resultLabel.setText("이메일을 입력해주세요.");
                return;
            }

            // 이메일 전송 로직 호출
            boolean success = sendRecoveryEmail(userEmail);
            if (success) {
                resultLabel.setText("이메일이 전송되었습니다.");
            } else {
                resultLabel.setText("이메일 전송에 실패했습니다.");
            }
        });

        setVisible(true);
    }

    private boolean sendRecoveryEmail(String userEmail) {
        // Gmail 계정 정보
        final String senderEmail = GOOGLE_ACCOUNT; // 발신자 Gmail 주소
        final String senderPassword = "xlgx dgci swbn vfzy"; // 앱 비밀번호

        final String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // STARTTLS 활성화
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // SMTP 포트 (TLS)

        // 인증 객체 생성
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // 이메일 작성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(userEmail)
            );
            message.setSubject("아이디/비밀번호 찾기");
            message.setText("안녕하세요,\n\n요청하신 아이디와 비밀번호는 다음과 같습니다:\n\n아이디: 김진오\n비밀번호: 김진오\n\n감사합니다.");

            // 이메일 전송
            Transport.send(message);

            return true; // 전송 성공
        } catch (MessagingException e) {
            e.printStackTrace();
            return false; // 전송 실패
        }
    }
}
