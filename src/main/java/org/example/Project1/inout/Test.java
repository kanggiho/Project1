package org.example.project1.inout;

import org.example.project1.inout.UI.IncomingManagementPanel;
import org.example.project1.inout.UI.OutgoingConfirmPanel;

import javax.swing.*;

public class Test {
    public static void main(String[] args) throws Exception {
        JFrame testFrame = new JFrame("테스트");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(1200, 675);

//        IncomingManagementPanel panel1 = new IncomingManagementPanel();
//        testFrame.add(panel1);

        OutgoingConfirmPanel panel2 = new OutgoingConfirmPanel();
        testFrame.add(panel2);

        testFrame.setVisible(true);
    }
}
