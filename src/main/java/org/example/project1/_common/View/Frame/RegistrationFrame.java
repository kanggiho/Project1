package org.example.project1._common.View.Frame;

import org.example.project1._common.Model.DAO.OrdererDAO;
import org.example.project1._common.Model.VO.OrdererVO;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RegistrationFrame extends JFrame {

    private JButton completeButton, cancelButton;
    private JTextField idField, nameField, emailField, licenseField, telField, locField;
    private JPasswordField pwField, pwcField;

    public RegistrationFrame() {
        initializeUI();
        setupActions();
    }

    private void registerAccount() {
        try {
            OrdererDAO dao = new OrdererDAO();
            OrdererVO vo = new OrdererVO();
            vo.setId(idField.getText());
            vo.setPassword(String.valueOf(pwField.getPassword()));
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

    private void setupActions() {
        completeButton.addActionListener(e -> {
            try {
                if (validateFields()) {
                    registerAccount();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    private void initializeUI() {
        setTitle("My 웨하스 - 회원가입");
        setSize(1200, 675);
        setLayout(null);
        setLocationRelativeTo(null);

        add(createTopPanel());
        add(createSignupLabel());

        JPanel leftPanel = createInputPanel();
        idField = createInputField(leftPanel, "아이디", "사용할 아이디를 입력하세요.", 0);
        pwField = createPasswordField(leftPanel, "비밀번호", "사용할 비밀번호를 입력하세요.", 80);
        pwcField = createPasswordField(leftPanel, "비밀번호 확인", "사용할 비밀번호를 재입력하세요.", 160);
        emailField = createInputField(leftPanel, "이메일", "사용할 이메일을 입력하세요.", 240);
        leftPanel.setBounds(261, 160, 324, 300);
        add(leftPanel);

        JPanel rightPanel = createInputPanel();
        nameField = createInputField(rightPanel, "이름", "이름을 입력하세요.", 0);
        telField = createInputField(rightPanel, "전화번호", "전화번호를 입력하세요.", 80);
        licenseField = createInputField(rightPanel, "사업자등록번호", "사업자등록번호를 입력하세요.", 160);
        locField = createInputField(rightPanel, "사업자등록주소지", "사업자등록주소지를 입력하세요.", 240);
        rightPanel.setBounds(614, 160, 324, 300);
        add(rightPanel);

        completeButton = createButton("가입 완료", 261, 543);
        cancelButton = createButton("취소", 612, 543);
        add(completeButton);
        add(cancelButton);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBackground(new Color(80, 113, 158));
        topPanel.setBounds(0, 0, 1200, 60);

        JLabel logo = new JLabel();
        ImageIcon resizedIcon = new ImageIcon(new ImageIcon("src/main/resources/MainLogo.png")
                .getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
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
        return panel;
    }

    private JTextField createInputField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = createFieldLabel(panel, label, yPosition);

        JTextField inputField = new JTextField(placeholder);
        setupField(inputField, placeholder, yPosition);
        panel.add(inputField);

        if ("아이디".equals(label)) {
            inputField.getDocument().addDocumentListener(new ValidationListener(inputField, fieldLabel, () -> isValidId(inputField.getText())));
        }

        return inputField;
    }

    private JPasswordField createPasswordField(JPanel panel, String label, String placeholder, int yPosition) {
        JLabel fieldLabel = createFieldLabel(panel, label, yPosition);

        JPasswordField passwordField = new JPasswordField(placeholder);
        setupPasswordField(passwordField, placeholder, yPosition);
        panel.add(passwordField);

        return passwordField;
    }

    private JLabel createFieldLabel(JPanel panel, String label, int yPosition) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        fieldLabel.setBounds(0, yPosition, 324, 20);
        panel.add(fieldLabel);
        return fieldLabel;
    }

    private void setupField(JTextField field, String placeholder, int yPosition) {
        field.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        field.setForeground(new Color(0, 0, 0, 0.5f));
        field.setBounds(0, yPosition + 25, 324, 35);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setupPlaceholder(field, placeholder);
    }

    private void setupPasswordField(JPasswordField field, String placeholder, int yPosition) {
        field.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 14));
        field.setBounds(0, yPosition + 25, 324, 35);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        field.setEchoChar((char) 0); // 플레이스홀더가 보이도록 설정
        field.setForeground(new Color(0, 0, 0, 0.5f)); // 다른 필드와 동일한 회색 설정
        setupPlaceholder(field, placeholder);
    }


    private void setupPlaceholder(JTextComponent field, String placeholder) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('●');
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(0, 0, 0, 0.5f));
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 290, 41);
        button.setFont(new Font("머니그라피TTF Rounded", Font.PLAIN, 16));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        return button;
    }

    private boolean isValidId(String id) {
        try {
            OrdererDAO dao = new OrdererDAO();
            return !dao.confirmID(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean validateFields() throws Exception {
        if (isEmptyField(idField) || isEmptyField(pwField) || isEmptyField(pwcField) ||
                isEmptyField(nameField) || isEmptyField(telField) ||
                isEmptyField(licenseField) || isEmptyField(locField) || isEmptyField(emailField)) {
            JOptionPane.showMessageDialog(this, "모든 필드를 올바르게 입력하세요.");
            return false;
        }

        if (!idField.getText().matches("^[a-z0-9]{5,15}$")) {
            showError("아이디 조건이 맞지 않습니다.\n영어 소문자, 숫자를 포함하여 5-15글자");
            return false;
        }

        if (!isValidId(idField.getText())) {
            showError("아이디가 중복됩니다.");
            return false;
        }

        if (!pwField.getText().matches("^[a-z0-9]{8,15}$")) {
            showError("비밀번호 조건이 맞지 않습니다.\n영어 소문자, 숫자를 포함하여 8-15글자");
            return false;
        }

        if (!pwField.getText().equals(new String(pwcField.getPassword()))) {
            showError("비밀번호가 일치하지 않습니다.");
            return false;
        }

        if (!emailField.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            showError("이메일 조건이 맞지 않습니다.\nXXXX@XXXX.XXX");
            return false;
        }

        if (!nameField.getText().matches("^[a-zA-Z가-힣]{2,15}$")) {
            showError("이름 조건이 맞지 않습니다.\n영어, 한글 2-15글자");
            return false;
        }

        if (!telField.getText().matches("^010-\\d{4}-\\d{4}$")) {
            showError("전화번호 조건이 맞지 않습니다.\n010-XXXX-XXXX");
            return false;
        }

        if (!licenseField.getText().matches("^\\d{3}-\\d{2}-\\d{5}$")) {
            showError("사업자 등록 번호 조건이 맞지 않습니다.\nXXX-XX-XXXXX");
            return false;
        }

        if (!locField.getText().matches("^[0-9a-zA-Z가-힣]{5,50}$")) {
            showError("사업자 등록 주소지 조건이 맞지 않습니다.\n한글, 영어, 숫자를 포함하여 5-50글자");
            return false;
        }

        return true;
    }

    private boolean isEmptyField(JTextComponent field) {
        return field.getText().trim().isEmpty();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private class ValidationListener implements DocumentListener {
        private final JTextField field;
        private final JLabel label;
        private final Validation validation;

        public ValidationListener(JTextField field, JLabel label, Validation validation) {
            this.field = field;
            this.label = label;
            this.validation = validation;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateField();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validateField();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validateField();
        }

        private void validateField() {
            if (field.getText().length() >= 5 && field.getText().length() <= 15) {
                if (field.getText().matches("^[a-z0-9]{5,15}$")) {
                    if (validation.isValid()) {
                        label.setForeground(Color.GREEN);
                        label.setText("아이디 - 해당 아이디는 사용가능합니다.");
                    } else {
                        label.setForeground(Color.RED);
                        label.setText("아이디 - 이미 동일한 아이디가 있습니다.");
                    }
                } else {
                    label.setForeground(Color.BLACK);
                    label.setText("아이디");
                }
            } else {
                label.setForeground(Color.BLACK);
                label.setText("아이디");
            }
        }
    }

    @FunctionalInterface
    private interface Validation {
        boolean isValid();
    }
}
