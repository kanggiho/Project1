package org.example.Project1.__Test;

import java.awt.GraphicsEnvironment;

public class AvailableFonts {
    public static void main(String[] args) {
        // GraphicsEnvironment 객체 가져오기
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // 사용 가능한 모든 폰트 이름 가져오기
        String[] fontNames = ge.getAvailableFontFamilyNames();

        // 폰트 이름 출력
        System.out.println("사용 가능한 폰트 목록:");
        for (String fontName : fontNames) {
            System.out.println(fontName);
        }
    }
}
