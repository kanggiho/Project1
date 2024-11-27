package org.example.Project1.View.Frame;

import org.example.Project1.Model.DAO.OrdererDAO;
import org.example.Project1.Model.VO.OrdererVO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RegistrationFrame extends JFrame {

    private JButton completeButton, cancelButton;
    private JTextField idField, nameField, emailField, licenseField, telField, locField;
    private JPasswordField pwField, pwcField; // 비밀번호 확인 필드 추가


    public RegistrationFrame() {
        setUI();
        addAction();
    }

    private void registerAccount() {
        try {
            OrdererDAO dao = new OrdererDAO();
            // 아이디 생성
            OrdererVO vo = new OrdererVO();
            vo.setId(idField.getText());
            vo.setPassword(pwField.getText());
            vo.setName(nameField.getText());
            vo.setTel(telField.getText());
            vo.setLicense(licenseField.getText());
            vo.setLoc(locField.getText());
            vo.setEmail(emailField.getText());
            vo.setGrade("BRONZE");
            dao.insert(vo);
            JOptionPane.showMessageDialog(this, "정상적으로 가입되었습니다.");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "회원가입 처리 중 오류가 발생했습니다.");
        }
    }

    private void addAction() {
        // 가입 완료 버튼 클릭 이벤트
        completeButton.addActionListener(e -> {

            try {
                if (validation()) {
                    registerAccount();
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        cancelButton.addActionListener(e -> {
            dispose();
        });
    }

    private void setUI() {
        setTitle("My 웨하스 - 회원가입");
        setSize(1200, 675);
        setLayout(null);
        setLocationRelativeTo(null);


        // 상단 패널
        JPanel topPanel = createTopPanel();
        add(topPanel);

        // 회원가입 타이틀
        JLabel signupLabel = createSignupLabel();
        add(signupLabel);

        // 왼쪽 입력 폼
        JPanel leftPanel = createInputPanel();
        leftPanel.setBounds(261, 160, 324, 300); // 정확한 위치 지정
        idField = addInputField(leftPanel, "아이디", "사용할 아이디를 입력하세요.", 0);
        pwField = addPasswordField(leftPanel, "비밀번호", "사용할 비밀번호를 입력하세요.", 80); // JPasswordField 추가
        pwcField = addPasswordField(leftPanel, "비밀번호 확인", "사용할 비밀번호를 재입력하세요.", 160); // 비밀번호 확인 필드 추가
        emailField = addInputField(leftPanel, "이메일", "사용할 이메일을 입력하세요.", 240);

        add(leftPanel);

        // 오른쪽 입력 폼
        JPanel rightPanel = createInputPanel();
        rightPanel.setBounds(614, 160, 324, 300); // 높이를 늘려 비밀번호 확인 필드 포함
        nameField = addInputField(rightPanel, "이름", "이름을 입력하세요.", 0);
        telField = addInputField(rightPanel, "전화번호", "전화번호를 입력하세요.", 80);
        licenseField = addInputField(rightPanel, "사업자등록번호", "사업자등록번호를 입력하세요.", 160);
        locField = addInputField(rightPanel, "사업자등록주소지", "사업자등록주소지를 입력하세요.", 240);
        add(rightPanel);

        // 가입 완료 버튼
        completeButton = createCompleteButton();
        cancelButton = createCancelButton();

        add(completeButton);
        add(cancelButton);

        setVisible(true);
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

        JLabel title = new JLabel("My 웨하스");
        title.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 24));
        title.setForeground(Color.BLACK);

        topPanel.add(logo);
        topPanel.add(title);

        return topPanel;
    }

    private JLabel createSignupLabel() {
        JLabel signupLabel = new JLabel("회원가입", SwingConstants.CENTER);
        signupLabel.setBounds(254, 90, 132, 48);
        signupLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 32));
        signupLabel.setForeground(Color.BLACK);
        return signupLabel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(null);
        panel.setBounds(261, 205, 324, 320); // 높이를 늘려 추가 필드를 포함
        return panel;
    }

    private JTextField addInputField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        fieldLabel.setBounds(0, yPosition, 324, 20);
        panel.add(fieldLabel);

        JTextField inputField = new JTextField(placeholder);
        inputField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        inputField.setForeground(new Color(0, 0, 0, 0.5f));
        inputField.setBounds(0, yPosition + 25, 324, 35);
        inputField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // id 유효성 검증
        if(label.equals("아이디")){
            inputField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {

                    if (inputField.getText().length() >= 5 && inputField.getText().length() <= 15){
                        if(idField.getText().matches("^[a-z0-9]{5,15}$")){
                            if (!id_valid()) {
                                fieldLabel.setForeground(Color.RED);
                                fieldLabel.setText(label + "- 이미 동일한 아이디가 있습니다.");
                            } else {
                                fieldLabel.setForeground(Color.GREEN);
                                fieldLabel.setText(label + "- 해당 아이디는 사용가능합니다.");
                            }
                        }else{
                            fieldLabel.setForeground(Color.BLACK);
                            fieldLabel.setText(label);
                        }
                    }else{
                        fieldLabel.setForeground(Color.BLACK);
                        fieldLabel.setText(label);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (inputField.getText().length() >= 5 && inputField.getText().length() <= 15){
                        if(idField.getText().matches("^[a-z0-9]{5,15}$")){
                            if (!id_valid()) {
                                fieldLabel.setForeground(Color.RED);
                                fieldLabel.setText(label + "- 이미 동일한 아이디가 있습니다.");
                            } else {
                                fieldLabel.setForeground(Color.GREEN);
                                fieldLabel.setText(label + "- 해당 아이디는 사용가능합니다.");
                            }
                        }else{
                            fieldLabel.setForeground(Color.BLACK);
                            fieldLabel.setText(label);
                        }
                    }else{
                        fieldLabel.setForeground(Color.BLACK);
                        fieldLabel.setText(label);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (inputField.getText().length() >= 5 && inputField.getText().length() <= 15){
                        if(idField.getText().matches("^[a-z0-9]{5,15}$")){
                            if (!id_valid()) {
                                fieldLabel.setForeground(Color.RED);
                                fieldLabel.setText(label + "- 이미 동일한 아이디가 있습니다.");
                            } else {
                                fieldLabel.setForeground(Color.GREEN);
                                fieldLabel.setText(label + "- 해당 아이디는 사용가능합니다.");
                            }
                        }else{
                            fieldLabel.setForeground(Color.BLACK);
                            fieldLabel.setText(label);
                        }
                    }else{
                        fieldLabel.setForeground(Color.BLACK);
                        fieldLabel.setText(label);
                    }
                }
            });
        }



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
        return inputField;
    }

    private JPasswordField addPasswordField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        fieldLabel.setBounds(0, yPosition, 324, 20);
        panel.add(fieldLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        passwordField.setBounds(0, yPosition + 25, 324, 35);
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        passwordField.setForeground(new Color(0, 0, 0, 0.5f)); // 플레이스홀더 색상

        // 초기 플레이스홀더 설정
        passwordField.setText(placeholder);
        passwordField.setEchoChar((char) 0); // 플레이스홀더를 볼 수 있도록 설정

        // FocusListener로 플레이스홀더 구현
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('●'); // 입력된 비밀번호를 숨김
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(new Color(0, 0, 0, 0.5f)); // 플레이스홀더 색상
                    passwordField.setEchoChar((char) 0); // 플레이스홀더를 볼 수 있도록 설정
                }
            }
        });

        panel.add(passwordField);
        return passwordField;
    }


    private JButton createCompleteButton() {
        JButton button = new JButton("가입 완료");
        button.setBounds(261, 543, 290, 41);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private JButton createCancelButton() {
        JButton button = new JButton("취소");
        button.setBounds(612, 543, 290, 41);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }


    private boolean id_valid() {
        try {
            OrdererDAO dao = new OrdererDAO();
            if (dao.confirmID(idField.getText())) {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }


    private boolean validation() throws Exception {
        OrdererDAO dao = new OrdererDAO();

        // 계정 등록시 유효성 검증
        // 공백 검증
        if (idField.getText().trim().isEmpty() || pwField.getText().trim().isEmpty() ||
                nameField.getText().trim().isEmpty() || telField.getText().trim().isEmpty() ||
                licenseField.getText().trim().isEmpty() || locField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "모든 필드를 올바르게 입력하세요.");
            return false;
        }

        // 1. 아이디 검증
        if (!idField.getText().matches("^[a-z0-9]{5,15}$")) {
            JOptionPane.showMessageDialog(this, "아이디 조건이 맞지 않습니다.\n영어 소문자, 숫자를 포함하여 5-15글자");
            return false;
        }

        try {
            if (dao.confirmID(idField.getText())) {
                JOptionPane.showMessageDialog(this, "아이디가 중복됩니다.");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // 2. 비밀번호 검증
        if (!pwField.getText().matches("^[a-z0-9]{8,15}$")) {
            JOptionPane.showMessageDialog(this, "비밀번호 조건이 맞지 않습니다.\n영어 소문자, 숫자를 포함하여 8-15글자");
            return false;
        }

        if (!pwField.getText().equals(pwcField.getText())) {
            JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
            return false;
        }

        // 3. 이메일 검증
        if (!emailField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "이메일 조건이 맞지 않습니다.\nXXXX@XXXX.XXX");
            return false;
        }


        // 4. 이름 검증
        if (!nameField.getText().matches("^[a-zA-Z가-힣]{2,15}$")) {
            JOptionPane.showMessageDialog(this, "이름 조건이 맞지 않습니다.\n영어, 한글 2-5글자");
            return false;
        }

        // 5. 전화번호 검증
        if (!telField.getText().matches("^010-\\d{4}-\\d{4}$")) {
            JOptionPane.showMessageDialog(this, "전화번호 조건이 맞지 않습니다.\n010-XXXX-XXXX");
            return false;
        }

        // 6. 사업자 등록 번호 검증
        if (!licenseField.getText().matches("^\\d{3}-\\d{2}-\\d{5}$")) {
            JOptionPane.showMessageDialog(this, "사업자 등록 번호 조건이 맞지 않습니다.\nXXX-XX-XXXXX");
            return false;
        }

        // 7. 사업자 등록 주소지 검증
        if (!locField.getText().matches("^[a-zA-Z가-힣]{5,50}$")) {
            JOptionPane.showMessageDialog(this, "사업자 등록 주소지 조건이 맞지 않습니다.\n한글, 영어, 숫자를 포함하여 5-50글자");
            return false;
        }
        return true;
    }
}
