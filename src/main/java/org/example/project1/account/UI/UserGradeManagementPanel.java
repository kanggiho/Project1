package org.example.project1.account.UI;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.account.DAO.OrderDAO;
import org.example.project1.account.VO.OrderVO;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserGradeManagementPanel extends JPanel {
    private UserTablePanel userTablePanel;
    private UserDataLoadPanel userDataLoadPanel;
    private UserGradeUpdatePanel userGradeUpdatePanel;
    private String toss_font = "머니그라피TTF Rounded";
    private OrderDAO orderDAO;

    public UserGradeManagementPanel(String title) throws Exception {
        this.orderDAO = new OrderDAO();
        setPanel();
        initUI();
        addAction();
        loadUserData();
    }

    private void setPanel() {
        setSize(1100, 550);
        setBackground(Color.WHITE);
        setLayout(null);
    }

    private void initUI() {
        userTablePanel = new UserTablePanel();
        userDataLoadPanel = new UserDataLoadPanel();
        userGradeUpdatePanel = new UserGradeUpdatePanel();

        userTablePanel.setBounds(50, 120, 1000, 210);
        userDataLoadPanel.setBounds(800, 10, 100, 30);
        userGradeUpdatePanel.setBounds(50, 350, 1000, 150);

        add(userTablePanel);
        add(userDataLoadPanel);
        add(userGradeUpdatePanel);
    }

    private void addAction() {
        userDataLoadPanel.setLoadButtonListener(e -> loadUserData());
        userTablePanel.setTableSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = userTablePanel.userTable.getSelectedRow();
                if (selectedRow != -1) {
                    String id = (String) userTablePanel.userTable.getValueAt(selectedRow, 0);
                    String name = (String) userTablePanel.userTable.getValueAt(selectedRow, 2);
                    String currentGrade = (String) userTablePanel.userTable.getValueAt(selectedRow, 5);
                    userGradeUpdatePanel.resetUserInfo(id, name, currentGrade);
                }
            }
        });
        userGradeUpdatePanel.setConfirmButtonListener(e -> updateUserGrade());
    }

    private void loadUserData() {
        try {
            ArrayList<OrderVO> userList = orderDAO.getIdLicenseNameTelEmailGrade();
            userTablePanel.updateTableData(userList);
            userGradeUpdatePanel.resetToDefault(); // 추가: 기본값으로 초기화
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "데이터 로드 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateUserGrade() {
        String id = userGradeUpdatePanel.getUserId();
        String name = userGradeUpdatePanel.getUserName();
        String newGrade = userGradeUpdatePanel.getSelectedGrade();
        try {
            orderDAO.updateGrade(newGrade, name, id);
            loadUserData();
            JOptionPane.showMessageDialog(this, "등급이 성공적으로 업데이트되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "등급 업데이트 중 오류가 발생했습니다: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class UserTablePanel extends JPanel {
        private JTable userTable;
        private DefaultTableModel tableModel;

        public UserTablePanel() {
            setLayout(new BorderLayout());
            initTable();
        }

        private void initTable() {
            String[] columnNames = {"ID", "라이센스", "이름", "전화번호", "이메일", "등급"};
            tableModel = new DefaultTableModel(columnNames, 0);
            userTable = new JTable(tableModel);
            userTable.setFont(new Font(toss_font, Font.PLAIN, 14));
            userTable.getTableHeader().setFont(new Font(toss_font, Font.BOLD, 16));
            JScrollPane scrollPane = new JScrollPane(userTable);
            add(scrollPane, BorderLayout.CENTER);
        }

        public void updateTableData(ArrayList<OrderVO> userList) {
            tableModel.setRowCount(0);
            for (OrderVO user : userList) {
                Object[] rowData = {user.getId(), user.getLicense(), user.getName(), user.getTel(), user.getEmail(), user.getGrade()};
                tableModel.addRow(rowData);
            }
        }

        public void setTableSelectionListener(ListSelectionListener listener) {
            userTable.getSelectionModel().addListSelectionListener(listener);
        }
    }

    private class UserDataLoadPanel extends JPanel {
        private JButton loadButton;

        public UserDataLoadPanel() {
            setLayout(new FlowLayout());
            loadButton = new JButton("새로고침");
            loadButton.setFont(new Font(toss_font, Font.PLAIN, 16));
            add(loadButton);
        }

        public void setLoadButtonListener(ActionListener listener) {
            loadButton.addActionListener(listener);
        }
    }

    private class UserGradeUpdatePanel extends JPanel {
        private JLabel idLabel, nameLabel;
        private JRadioButton bronzeRadio, silverRadio, goldRadio, diamondRadio;
        private ButtonGroup gradeGroup;
        private JButton confirmButton;

        public UserGradeUpdatePanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
            initComponents();
        }

        private void initComponents() {
            idLabel = new JLabel("사용자 ID");
            nameLabel = new JLabel("사용자 NAME");
            idLabel.setFont(new Font(toss_font, Font.PLAIN, 14));
            nameLabel.setFont(new Font(toss_font, Font.PLAIN, 14));

            bronzeRadio = new JRadioButton("BRONZE");
            silverRadio = new JRadioButton("SILVER");
            goldRadio = new JRadioButton("GOLD");
            diamondRadio = new JRadioButton("DIAMOND");

            gradeGroup = new ButtonGroup();
            gradeGroup.add(bronzeRadio);
            gradeGroup.add(silverRadio);
            gradeGroup.add(goldRadio);
            gradeGroup.add(diamondRadio);

            confirmButton = new JButton("변경");
            confirmButton.setFont(new Font(toss_font, Font.PLAIN, 14));

            add(idLabel);
            add(nameLabel);
            add(bronzeRadio);
            add(silverRadio);
            add(goldRadio);
            add(diamondRadio);
            add(confirmButton);
        }

        public void resetUserInfo(String id, String name, String currentGrade) {
            idLabel.setText("사용자 ID: " + id);
            nameLabel.setText("사용자 NAME: " + name);
            updateGradeRadioButtons(currentGrade);
        }

        private void updateGradeRadioButtons(String currentGrade) {
            gradeGroup.clearSelection();
            bronzeRadio.setVisible(!currentGrade.equals("BRONZE"));
            silverRadio.setVisible(!currentGrade.equals("SILVER"));
            goldRadio.setVisible(!currentGrade.equals("GOLD"));
            diamondRadio.setVisible(!currentGrade.equals("DIAMOND"));
        }

        public String getUserId() {
            return idLabel.getText().substring(idLabel.getText().indexOf(":") + 2);
        }

        public String getUserName() {
            return nameLabel.getText().substring(nameLabel.getText().indexOf(":") + 2);
        }

        public String getSelectedGrade() {
            if (bronzeRadio.isSelected()) return "BRONZE";
            if (silverRadio.isSelected()) return "SILVER";
            if (goldRadio.isSelected()) return "GOLD";
            if (diamondRadio.isSelected()) return "DIAMOND";
            return "";
        }
        public void resetToDefault() {
            idLabel.setText("사용자 ID");
            nameLabel.setText("사용자 NAME");
            gradeGroup.clearSelection(); // 모든 라디오 버튼 선택 해제
            bronzeRadio.setVisible(true);
            silverRadio.setVisible(true);
            goldRadio.setVisible(true);
            diamondRadio.setVisible(true);
        }

        public void setConfirmButtonListener(ActionListener listener) {
            confirmButton.addActionListener(listener);
        }
    }
}