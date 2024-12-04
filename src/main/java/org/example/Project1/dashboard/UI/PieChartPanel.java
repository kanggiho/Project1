package org.example.project1.dashboard.UI;

import org.example.project1.dashboard.DAO.OutputProductDAO;
import org.example.project1.dashboard.VO.OutputProductVO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PieChartPanel extends JPanel {
    public PieChartPanel() {
        setPanel();
        getNew();
    }



    private void setPanel() {
        setSize(500, 500);
        setLayout(null);

    }

    public void getNew(){
        removeAll();
        OutputProductDAO outputProductDAO = new OutputProductDAO();

        List<OutputProductVO> resultList = new ArrayList<>();

        // 합산된 데이터 조회
        List<OutputProductVO> aggregatedProducts = outputProductDAO.getAggregatedOutputProductsByUserId(5001);
        resultList.clear();

        if(aggregatedProducts.size() > 5) {
            for (int i = 0; i < 5; i++) {
                resultList.add(aggregatedProducts.get(i));
            }
        }else{
            resultList = aggregatedProducts;
        }
        int resSize = resultList.size();

        // 패널 추가
        PieChartExample pieChart = new PieChartExample(resultList);
        pieChart.setBounds(0,0,500,500);
        add(pieChart);
    }

    public static void main(String[] args) {
        new PieChartPanel();
    }
}
