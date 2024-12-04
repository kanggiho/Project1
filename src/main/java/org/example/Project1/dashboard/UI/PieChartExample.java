package org.example.project1.dashboard.UI;

import org.example.project1.dashboard.VO.OutputProductVO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PieChartExample extends JPanel {


    int size = 0;

    int[] value = {0, 0, 0, 0, 0};
    String[] product = {"","","","",""};


    public PieChartExample(List<OutputProductVO> list) {
        setPanel();
        setData(list);
    }

    private void setPanel() {
        setBackground(Color.WHITE);
    }

    private void setData(List<OutputProductVO> list) {
        size = list.size();
        for(int i = 0; i < size; i++) {
            value[i] = list.get(i).getReleaseQuantity();
            product[i] = list.get(i).getProductName();
        }
    }




    // 색상 배열 (파이 조각의 색상을 지정)
    Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Graphics2D로 캐스팅하여 그래픽 향상
        Graphics2D g2d = (Graphics2D) g;

        // 전체 합 계산
        int total = value[0] + value[1] + value[2] + value[3] + value[4];

        // 각 변수의 각도 계산
        double angleA = (double) value[0] / total * 360;
        double angleB = (double) value[1] / total * 360;
        double angleC = (double) value[2] / total * 360;
        double angleD = (double) value[3] / total * 360;
        double angleE = (double) value[4] / total * 360;

        // 파이 차트의 위치와 크기
        int x = 50;
        int y = 50;
        int width = 300;
        int height = 300;

        // 시작 각도
        double startAngle = 0;

        // 파이 조각 그리기
        g2d.setColor(colors[0]);
        g2d.fillArc(x, y, width, height, (int) startAngle, (int) angleA);
        startAngle += angleA;

        g2d.setColor(colors[1]);
        g2d.fillArc(x, y, width, height, (int) startAngle, (int) angleB);
        startAngle += angleB;

        g2d.setColor(colors[2]);
        g2d.fillArc(x, y, width, height, (int) startAngle, (int) angleC);
        startAngle += angleC;

        g2d.setColor(colors[3]);
        g2d.fillArc(x, y, width, height, (int) startAngle, (int) angleD);
        startAngle += angleD;

        g2d.setColor(colors[4]);
        g2d.fillArc(x, y, width, height, (int) startAngle, (int) angleE);
        // startAngle += angleE; // 마지막 조각이므로 필요 없음

        // 범례 그리기
        int legendX = x + width + 20;
        int legendY = y;
        int legendWidth = 20;
        int legendHeight = 20;

        // 변수 이름과 색상을 표시
        String[] labels = new String[size];

        for(int i = 0; i < size; i++) {
            labels[i] = product[i] + " = " + value[i];
        }

        for (int i = 0; i < labels.length; i++) {
            g2d.setColor(colors[i]);
            g2d.fillRect(legendX, legendY + i * (legendHeight + 10), legendWidth, legendHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawRect(legendX, legendY + i * (legendHeight + 10), legendWidth, legendHeight);
            g2d.drawString(labels[i], legendX + legendWidth + 10, legendY + i * (legendHeight + 10) + 15);
        }
    }

}