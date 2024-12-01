package org.example.project1.order.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import org.example.project1.order.DAO.OutputInfoDAO;
import org.example.project1.order.DAO.ProductInfoDAO;
import org.example.project1.order.VO.OutputInfoVO;
import org.example.project1.order.VO.ProductInfoProductVO;
import org.example.project1.order.VO.ProductInfoVO;

/**
 * OutgoingPanel 클래스는 두 개의 JTable과 각 테이블 위에 JLabel과 JComboBox를 포함하는 패널입니다.
 * 레이아웃을 null로 설정하고 setBounds를 사용하여 컴포넌트의 위치와 크기를 수동으로 지정합니다.
 */
public class OutgoingPanel extends JPanel {


    private String title;
    private String toss_font = "머니그라피TTF Rounded";


    public OutgoingPanel(String title) {
        this.title = title;
        setPanel();
        initUI();
    }

    // JPanel 설정
    private void setPanel() {
        setSize(1100, 450);
        setBackground(Color.WHITE);
        setLayout(null); // 레이아웃을 null로 설정
    }

    // UI 초기화
    private void initUI() {
        // 패널 초기화
        removeAll();

        // 라벨 생성
        JLabel stockCheckLabel = new JLabel("재고 확인");
        stockCheckLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
        stockCheckLabel.setBounds(20, 20, 100, 40); // 위치와 크기 설정
        add(stockCheckLabel);

        // 재고 확인 테이블 생성
        JTable stockTable = createStockTable();    // 윗쪽 테이블
        JScrollPane stockScrollPane = new JScrollPane(stockTable);
        stockScrollPane.setBounds(20, 60, 800, 150); // 위치와 크기 설정
        add(stockScrollPane);

        // 출고 요청 레이블 생성
        JLabel outputRequestLabel = new JLabel("출고 요청");
        outputRequestLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
        outputRequestLabel.setBounds(20, 230, 100, 40); // 위치와 크기 설정
        add(outputRequestLabel);

        // 출고 요청 테이블 생성
        JTable outputTable = createOutputTable();  // 아래쪽 테이블
        JScrollPane outputScrollPane = new JScrollPane(outputTable);
        outputScrollPane.setBounds(20, 270, 800, 150); // 위치와 크기 설정
        add(outputScrollPane);

        // 추가적인 레이아웃 설정이나 컴포넌트 배치가 필요하다면 여기에 추가

        // UI 갱신
        revalidate();
        repaint();
    }

    // 재고 확인 테이블 생성
    private JTable createStockTable() {
        try {
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();

            // 실제 데이터 로드
            List<ProductInfoProductVO> productList = productInfoDAO.getInventoryStatus();

            // 테이블 모델 생성
            ProductInfoTableModel model = new ProductInfoTableModel(productList);

            // 테이블 생성
            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setRowHeight(25);
            table.getTableHeader().setReorderingAllowed(false);

            // 테이블 크기 조정
            table.setPreferredScrollableViewportSize(new Dimension(480, 150));

            return table;
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 빈 테이블 반환
            return new JTable();
        }
    }

    // 출고 요청 테이블 생성
    private JTable createOutputTable() {
        try {
            OutputInfoDAO outputInfoDAO = new OutputInfoDAO();

            // 실제 데이터 로드
            List<OutputInfoVO> outputList = outputInfoDAO.getAll();

            // 테이블 모델 생성
            OutputInfoTableModel model = new OutputInfoTableModel(outputList);

            // 테이블 생성
            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setRowHeight(25);
            table.getTableHeader().setReorderingAllowed(false);

            // 테이블 크기 조정
            table.setPreferredScrollableViewportSize(new Dimension(480, 150));

            return table;
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 빈 테이블 반환
            return new JTable();
        }
    }
}
