package org.example.Project1.__Test.waste;

import org.example.Project1.DAO.ordererDAO;
import org.example.Project1.VO.ordererVO;
import org.example.Project1._Frame.MenuFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;

public class Login extends JFrame {

    static JTextArea allText;
    private final ordererDAO dao = new ordererDAO(); // DAO 객체 선언

    private JTextField id;
    private JPasswordField pw;

    public Login() throws Exception {
        // JFrame 기본 설정
        setFrame();
        // UI 구성 요소 초기화
        initUI();
        // 화면 표시
        setVisible(true);
    }

    public void setFrame(){
        setTitle("로그인");
        setSize(1200, 675);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initUI() {

        //로고 필드

        URL imageUrl = Login.class.getClassLoader().getResource("ware1.png");
        if (imageUrl != null) {
            // ImageIcon 생성
            ImageIcon logoIcon = new ImageIcon(imageUrl);

            // JLabel에 이미지 설정
            JLabel logoLabel = new JLabel(logoIcon);
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
            Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 이미지 사이즈 조절
            ImageIcon resizedIcon = new ImageIcon(scaledImage);
            logoLabel.setIcon(resizedIcon);
            logoLabel.setBorder(new EmptyBorder(0, 20, 0, 20)); // 보더 값 주기

            // JLabel을 JFrame에 추가
            add(logoLabel, BorderLayout.CENTER);
        } else {
            System.out.println("로고 이미지를 찾을 수 없습니다!");
        }


        // 아이디 필드
        add(new JLabel("아이디  : "));
        id = new JTextField(12);
        id.setText("abcd1111");
        add(id);

        // 비밀번호 필드
        add(new JLabel("비밀번호 : "));
        pw = new JPasswordField(12);
        pw.setText("abcd1111");
        add(pw);

        // 엔터 키 이벤트 추가
        id.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        pw.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인");
        add(loginBtn);
        loginBtn.addActionListener(e -> handleLogin());

        // 초기화 버튼
        JButton resetBtn = new JButton("초기화");
        add(resetBtn);
        resetBtn.addActionListener(e -> {
            id.setText("");
            pw.setText("");
        });

        // 회원가입 버튼
        JButton registerBtn = new JButton("회원가입");
        add(registerBtn);
        registerBtn.addActionListener(e -> {
            try {
                new Register();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "회원가입 화면을 여는 중 문제가 발생했습니다.");
                ex.printStackTrace();
            }
        });

        // 전체 계정 확인 버튼
        JButton showBtn = new JButton("전체 계정 확인");
        add(showBtn);
        showBtn.addActionListener(e -> toggleAccountList());

        // 종료 버튼
        JButton exitBtn = new JButton("종료");
        add(exitBtn);
        exitBtn.addActionListener(e -> dispose());

        // 전체 계정 텍스트 에어리어
        allText = new JTextArea(8, 20);
        allText.setEditable(false);
        allText.setLineWrap(true);
        allText.setVisible(false);
        add(allText);
    }

    private void handleLogin() {
        String temp_id = id.getText();
        String temp_pw = new String(pw.getPassword());

        try {
            ordererVO ovo = dao.one(temp_id);
            if (dao.isValid(temp_id, temp_pw)) {
                JOptionPane.showMessageDialog(this, "%s님 환영합니다.".formatted(ovo.getName()));
                new MenuFrame("메인 화면");
                dispose(); // 현재 창 닫기
            } else {
                JOptionPane.showMessageDialog(this, "다시 확인해주세요.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "오류가 발생했습니다: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void toggleAccountList() {
        if (allText.isVisible()) {
            allText.setVisible(false);
        } else {
            allText.setVisible(true);

            try {
                ArrayList<ordererVO> user_list = dao.getAll();
                allText.setText("");
                for (ordererVO user : user_list) {
                    String temp = "아이디 : " + user.getId() + " 비밀번호 : " + user.getPassword() + "\n";
                    allText.append(temp);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "계정 목록을 가져오는 중 문제가 발생했습니다.");
                ex.printStackTrace();
            }
        }
    }
}
