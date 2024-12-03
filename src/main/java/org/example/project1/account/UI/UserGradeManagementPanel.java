package org.example.project1.account.UI;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.account.DAO.OrderDAO;
import org.example.project1.account.VO.OrderVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// 사용자 등급 관리 패널 클래스
public class UserGradeManagementPanel extends JPanel {

    private UserTablePanel userTablePanel;
    private UserDataLoadPanel userDataLoadPanel;
    private String toss_font = "머니그라피TTF Rounded";
    private OrderDAO orderDAO;

    // 생성자
    public UserGradeManagementPanel(String title) throws Exception {
        this.orderDAO = new OrderDAO();
        setPanel();
        initUI();
        addAction();
        loadUserData(); // 초기 데이터 로드
    }

    // 패널 기본 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.WHITE);
        setLayout(null);
    }

    // UI 초기화
    private void initUI() {
        // 서브 패널 초기화
        userTablePanel = new UserTablePanel();
        userDataLoadPanel = new UserDataLoadPanel();

        // 서브 패널 위치 설정
        userTablePanel.setBounds(50, 120, 1000, 210);
        userDataLoadPanel.setBounds(800, 10, 100, 30);

        // 메인 패널에 서브 패널 추가
        add(userTablePanel);
        add(userDataLoadPanel);
    }

    // 액션 리스너 추가
    private void addAction() {
        userDataLoadPanel.setLoadButtonListener(e -> loadUserData());
    }

    // 사용자 데이터 로드
    private void loadUserData() {
        try {
            ArrayList<OrderVO> userList = orderDAO.getIdLicenseNameTelEmailGrade();
            userTablePanel.updateTableData(userList);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "데이터 로드 중 오류가 발생했습니다: " + e.getMessage(),
                    "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 사용자 테이블 패널 내부 클래스
    private class UserTablePanel extends JPanel {
        private JTable userTable;
        private DefaultTableModel tableModel;

        // 생성자
        public UserTablePanel() {
            setLayout(new BorderLayout());
            initTable();
        }

        // 테이블 초기화
        private void initTable() {
            String[] columnNames = {"ID", "라이센스", "이름", "전화번호", "이메일", "등급"};
            tableModel = new DefaultTableModel(columnNames, 0);
            userTable = new JTable(tableModel);
            userTable.setFont(new Font(toss_font, Font.PLAIN, 14));
            userTable.getTableHeader().setFont(new Font(toss_font, Font.BOLD, 16));

            JScrollPane scrollPane = new JScrollPane(userTable);
            add(scrollPane, BorderLayout.CENTER);
        }

        // 테이블 데이터 업데이트
        public void updateTableData(ArrayList<OrderVO> userList) {
            tableModel.setRowCount(0);
            for (OrderVO user : userList) {
                Object[] rowData = {
                        user.getId(),
                        user.getLicense(),
                        user.getName(),
                        user.getTel(),
                        user.getEmail(),
                        user.getGrade()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    // 사용자 데이터 로드 패널 내부 클래스
    private class UserDataLoadPanel extends JPanel {
        private JButton loadButton;

        // 생성자
        public UserDataLoadPanel() {
            setLayout(new FlowLayout());
            loadButton = new JButton("새로고침");
            loadButton.setFont(new Font(toss_font, Font.PLAIN, 16));
            add(loadButton);
        }

        // 로드 버튼 리스너 설정
        public void setLoadButtonListener(java.awt.event.ActionListener listener) {
            loadButton.addActionListener(listener);
        }
    }
}