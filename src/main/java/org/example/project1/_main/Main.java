package org.example.project1._main;


import org.example.project1._common.View.Frame.LoginFrame;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) throws Exception {


        Color color = new Color(0x5A2121);



        try {
            // Look and Feel 설정
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { // Nimbus가 설치된 Look and Feel 중 선택
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 스윙 컴포넌트 생성
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}