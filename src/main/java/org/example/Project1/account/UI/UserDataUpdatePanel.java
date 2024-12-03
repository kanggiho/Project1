package org.example.project1.account.UI;

import org.example.project1.account.DAO.OrderDAO;
import org.example.project1.account.VO.OrderVO;
import org.example.project1.mainmenu.UI.UserMenuFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class UserDataUpdatePanel extends JPanel {
    String title;
    String name;

    JFrame frame;


    String toss_font = "머니그라피TTF rounded"; // 사용자 정의 폰트
    String temp_id, temp_pw, temp_name, temp_regNumber, temp_phone, temp_address;

    OrderDAO orderDAO;
    OrderVO orderVO;


    // 전역 변수로 컴포넌트 선언
    JLabel titleLabel;
    JLabel idLabel, idInutLabel, pwLabel, nameLabel, regNumberLabel, phoneLabel, addressLabel;
    JTextField nameField, regNumberField, phoneField, addressField;
    JPasswordField pwField;
    JButton saveButton;

    public UserDataUpdatePanel(String title, String name, JFrame frame) {
        this.title = title;
        this.name = name;
        this.frame = frame;
        setPanel(); // 패널 설정
        initUI(); // UI 초기화
        setData(); // 데이터 설정
        addAction(); // 액션리스너 설정
    }

    // JPanel 설정
    private void setPanel() {
        setBackground(Color.WHITE); // 배경색 흰색으로 설정
    }

    // UI 초기화
    private void initUI() {
        setLayout(null); // null 레이아웃 설정

        // 공통 폰트 설정
        Font commonFont = new Font(toss_font, Font.PLAIN, 18); // toss_font, PLAIN, 크기 18

        // 컴포넌트 기본 설정
        int labelToFieldGap = 20; // JLabel과 JTextField 간 간격
        int textFieldWidth = 200; // 텍스트 필드 너비
        int textFieldHeight = 30; // 텍스트 필드 높이
        int labelWidth = 150;     // 레이블 너비
        int labelHeight = 30;     // 레이블 높이
        int yOffset = 80;         // 모든 컴포넌트의 Y값 오프셋
        int xOffset = 50;         // 모든 컴포넌트의 X값 오프셋

        // 회원 정보 수정 레이블
        titleLabel = new JLabel(title);
        titleLabel.setBounds(100 + xOffset, 30, 300, 60);
        titleLabel.setFont(new Font(toss_font, Font.BOLD, 30));

        // 아이디 컴포넌트
        idLabel = new JLabel("아이디");
        idLabel.setBounds(100 + xOffset, 50 + yOffset, labelWidth, labelHeight);
        idLabel.setFont(commonFont);
        idInutLabel = new JLabel("여기");
        idInutLabel.setBounds(100 + labelWidth + labelToFieldGap + xOffset, 50 + yOffset, labelWidth, labelHeight);
        idInutLabel.setFont(commonFont);

        // 비밀번호 컴포넌트
        pwLabel = new JLabel("비밀번호");
        pwLabel.setBounds(100 + xOffset, 125 + yOffset, labelWidth, labelHeight);
        pwLabel.setFont(commonFont);
        pwField = new JPasswordField();
        pwField.setBounds(100 + labelWidth + labelToFieldGap + xOffset, 125 + yOffset, textFieldWidth, textFieldHeight);
        pwField.setFont(commonFont);

        // 이름 컴포넌트
        nameLabel = new JLabel("이름");
        nameLabel.setBounds(100 + xOffset, 200 + yOffset, labelWidth, labelHeight);
        nameLabel.setFont(commonFont);
        nameField = new JTextField();
        nameField.setBounds(100 + labelWidth + labelToFieldGap + xOffset, 200 + yOffset, textFieldWidth, textFieldHeight);
        nameField.setFont(commonFont);

        // 사업자등록번호 컴포넌트
        regNumberLabel = new JLabel("사업자등록번호");
        regNumberLabel.setBounds(520 + xOffset, 125 + yOffset, labelWidth, labelHeight);
        regNumberLabel.setFont(commonFont);
        regNumberField = new JTextField();
        regNumberField.setBounds(520 + labelWidth + labelToFieldGap + xOffset, 125 + yOffset, textFieldWidth, textFieldHeight);
        regNumberField.setFont(commonFont);

        // 전화번호 컴포넌트
        phoneLabel = new JLabel("전화번호");
        phoneLabel.setBounds(520 + xOffset, 50 + yOffset, labelWidth, labelHeight);
        phoneLabel.setFont(commonFont);
        phoneField = new JTextField();
        phoneField.setBounds(520 + labelWidth + labelToFieldGap + xOffset, 50 + yOffset, textFieldWidth, textFieldHeight);
        phoneField.setFont(commonFont);

        // 사업자등록주소지 컴포넌트
        addressLabel = new JLabel("사업자등록주소지");
        addressLabel.setBounds(520 + xOffset, 200 + yOffset, labelWidth, labelHeight);
        addressLabel.setFont(commonFont);
        addressField = new JTextField();
        addressField.setBounds(520 + labelWidth + labelToFieldGap + xOffset, 200 + yOffset, textFieldWidth, textFieldHeight);
        addressField.setFont(commonFont);

        // 버튼 컴포넌트 (정보수정완료)
        saveButton = new JButton("정보수정완료");
        saveButton.setBounds(420 + xOffset, 280 + yOffset, 150, 40);
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font(toss_font, Font.PLAIN, 16));

        // 패널에 컴포넌트 추가
        add(titleLabel);
        add(idLabel);
        add(idInutLabel);
        add(pwLabel);
        add(pwField);
        add(nameLabel);
        add(nameField);
        add(regNumberLabel);
        add(regNumberField);
        add(phoneLabel);
        add(phoneField);
        add(addressLabel);
        add(addressField);
        add(saveButton);
    }

    // 데이터 가져오기
    public void setData() {
        try {
            orderDAO = new OrderDAO();
            orderVO = orderDAO.nameSelect(name);
            idInutLabel.setText(orderVO.getId());
            pwField.setText(orderVO.getPassword());
            nameField.setText(orderVO.getName());
            regNumberField.setText(orderVO.getLicense());
            phoneField.setText(orderVO.getTel());
            addressField.setText(orderVO.getLoc());
            temp_id = orderVO.getId();
            temp_pw = orderVO.getPassword();
            temp_name = orderVO.getName();
            temp_regNumber = orderVO.getLicense();
            temp_phone = orderVO.getTel();
            temp_address = orderVO.getLoc();



        } catch (Exception e) {

        }
    }

    private void addAction() {

        regNumberField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handleRegNumber();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handleRegNumber();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handleRegNumber();
            }
        });

        phoneField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                handlePhone();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                handlePhone();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                handlePhone();
            }
        });
        saveButton.addActionListener(e -> {
            handleUpdate();
        });
    }

    private void handleRegNumber() {
        try {

            if (!regNumberField.getText().equals(temp_regNumber)) {
                if (orderDAO.confirmRegnum(regNumberField.getText())) {
                    regNumberLabel.setForeground(Color.RED);
                    regNumberLabel.setText("사업자 - 중복됨");

                } else {
                    regNumberLabel.setForeground(Color.BLACK);
                    regNumberLabel.setText("사업자등록번호");
                }
            } else {
                regNumberLabel.setForeground(Color.BLACK);
                regNumberLabel.setText("사업자등록번호");
            }

        } catch (Exception e) {

        }
    }

    private void handlePhone() {
        try {
            if (!phoneField.getText().equals(temp_phone)) {
                if (orderDAO.confirmPhone(phoneField.getText())) {
                    phoneLabel.setForeground(Color.RED);
                    phoneLabel.setText("전화번호 - 중복됨");
                } else {
                    phoneLabel.setForeground(Color.BLACK);
                    phoneLabel.setText("전화번호");
                }
            } else {
                phoneLabel.setForeground(Color.BLACK);
                phoneLabel.setText("전화번호");
            }
        } catch (Exception e) {

        }
    }

    private void handleUpdate() {
        try {
            if (validateFields()) {
                orderVO.setPassword(pwField.getText());
                orderVO.setName(nameField.getText());
                orderVO.setLicense(regNumberField.getText());
                orderVO.setTel(phoneField.getText());
                orderVO.setLoc(addressField.getText());
                orderDAO.updatebyId(temp_id, orderVO);
                showError("정상적으로 변경 되었습니다.");
                new UserMenuFrame(nameField.getText());
                frame.dispose();
            }
        } catch (Exception e) {

        }
    }

    private boolean validateFields() throws Exception {
        if (isEmptyField(pwField) || isEmptyField(nameField) || isEmptyField(phoneField) ||
                isEmptyField(regNumberField) || isEmptyField(addressField)) {
            JOptionPane.showMessageDialog(this, "모든 필드를 올바르게 입력하세요.");
            return false;
        }

        if (!pwField.getText().matches("^[a-z0-9]{8,15}$")) {
            showError("비밀번호 조건이 맞지 않습니다.\n영어 소문자, 숫자를 포함하여 8-15글자");
            return false;
        }

        if (!nameField.getText().matches("^[a-zA-Z가-힣]{2,15}$")) {
            showError("이름 조건이 맞지 않습니다.\n영어, 한글 2-15글자");
            return false;
        }

        if (!phoneField.getText().matches("^010-\\d{4}-\\d{4}$")) {
            showError("전화번호 조건이 맞지 않습니다.\n010-XXXX-XXXX");
            return false;
        }

        if (!regNumberField.getText().matches("^\\d{3}-\\d{2}-\\d{5}$")) {
            showError("사업자 등록 번호 조건이 맞지 않습니다.\nXXX-XX-XXXXX");
            return false;
        }

        if (!addressField.getText().matches("^[0-9a-zA-Z가-힣._]{5,50}$")) {
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
}
