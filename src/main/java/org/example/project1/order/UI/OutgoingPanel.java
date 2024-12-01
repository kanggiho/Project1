package org.example.project1.order.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import org.example.project1.order.DAO.ProductInfoDAO;
import org.example.project1.order.VO.OutputRequestVO;
import org.example.project1.order.VO.ProductInfoProductVO;

/**
 * OutgoingPanel 클래스는 두 개의 JTable과 각 테이블 위에 JLabel과 JButton을 포함하는 패널입니다.
 * 레이아웃을 null로 설정하고 setBounds를 사용하여 컴포넌트의 위치와 크기를 수동으로 지정합니다.
 */
public class OutgoingPanel extends JPanel {

    private String title;
    private String user_name;
    private String toss_font = "머니그라피TTF Rounded";

    // 오른쪽 패널 컴포넌트
    private JLabel productNameLabel;
    private JLabel productPriceLabel;
    private JTextField quantityField;
    private JButton releaseButton;

    // 선택된 제품 정보
    private ProductInfoProductVO selectedProduct;

    // 새로운 출고 요청 테이블 모델
    private OutputRequestTableModel outputRequestTableModel;

    public OutgoingPanel(String title, String user_name) {
        this.title = title;
        this.user_name = user_name;
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

        // --------------------- 재고 확인 섹션 ---------------------
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

        // --------------------- 출고 요청 섹션 ---------------------
        // 출고 요청 레이블 생성
        JLabel outputRequestLabel = new JLabel("출고 요청");
        outputRequestLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
        outputRequestLabel.setBounds(20, 230, 100, 40); // 위치와 크기 설정
        add(outputRequestLabel);

        // "삭제" 버튼 생성
        JButton deleteButton = new JButton("삭제");
        deleteButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색 (Pink)
        deleteButton.setBorder(new LineBorder(Color.BLACK, 2));
        deleteButton.setBounds(130, 230, 100, 40); // 위치와 크기 설정
        add(deleteButton);

        // "전체삭제" 버튼 생성
        JButton deleteAllButton = new JButton("전체삭제");
        deleteAllButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteAllButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색 (Pink)
        deleteAllButton.setBorder(new LineBorder(Color.BLACK, 2));
        deleteAllButton.setBounds(240, 230, 120, 40); // 위치와 크기 설정
        add(deleteAllButton);

        // --------------------- 새로운 출고 요청 테이블 ---------------------
        outputRequestTableModel = new OutputRequestTableModel();
        JTable outputRequestTable = new JTable(outputRequestTableModel);
        outputRequestTable.setFillsViewportHeight(true);
        outputRequestTable.setRowHeight(25);
        outputRequestTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane outputRequestScrollPane = new JScrollPane(outputRequestTable);
        outputRequestScrollPane.setBounds(20, 270, 800, 150); // 위치와 크기 설정
        add(outputRequestScrollPane);

        // --------------------- 오른쪽 출고 기능 섹션 ---------------------
        // 제품명 라벨
        JLabel nameLabel = new JLabel("제품명:");
        nameLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        nameLabel.setBounds(840, 60, 100, 30);
        add(nameLabel);

        productNameLabel = new JLabel("-");
        productNameLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        productNameLabel.setBounds(940, 60, 150, 30);
        add(productNameLabel);

        // 가격 라벨
        JLabel priceLabel = new JLabel("가격:");
        priceLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        priceLabel.setBounds(840, 100, 100, 30);
        add(priceLabel);

        productPriceLabel = new JLabel("-");
        productPriceLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        productPriceLabel.setBounds(940, 100, 150, 30);
        add(productPriceLabel);

        // 수량 입력 필드
        JLabel quantityLabel = new JLabel("출고 수량:");
        quantityLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        quantityLabel.setBounds(840, 140, 100, 30);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setFont(new Font(toss_font, Font.PLAIN, 16));
        quantityField.setBounds(940, 140, 150, 30);
        add(quantityField);

        // "출고하기" 버튼 생성
        releaseButton = new JButton("출고하기");
        releaseButton.setFont(new Font(toss_font, Font.PLAIN, 16));
        releaseButton.setBackground(new Color(144, 238, 144)); // 연한 녹색 (Light Green)
        releaseButton.setBorder(new LineBorder(Color.BLACK, 2));
        releaseButton.setBounds(840, 190, 250, 40);
        add(releaseButton);

        // --------------------- 테이블 선택 이벤트 ---------------------
        stockTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 테이블 선택 이벤트 처리
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = stockTable.getSelectedRow();
                    if (selectedRow != -1) {
                        ProductInfoProductVO product = ((ProductInfoTableModel) stockTable.getModel()).getProductAt(selectedRow);
                        selectedProduct = product;
                        productNameLabel.setText(product.getProduct_name()); // 제품명 표시
                        productPriceLabel.setText(String.valueOf(product.getPrice()));
                    }
                }
            }
        });

        // --------------------- 버튼 액션 이벤트 ---------------------
        // "삭제" 버튼 액션
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택된 출고 요청 삭제 로직 구현
                int selectedRow = outputRequestTable.getSelectedRow();
                if (selectedRow != -1) {
                    OutputRequestVO request = outputRequestTableModel.getOutputRequestAt(selectedRow);
                    // 해당 요청의 출고량을 재고에 반영
                    try {
                        ProductInfoTableModel stockModel = (ProductInfoTableModel) stockTable.getModel();
                        ProductInfoProductVO stockProduct = stockModel.getProductByProductCode(request.getProduct_code());
                        if (stockProduct != null) {
                            stockProduct.setStock(stockProduct.getStock() + request.getRelease_quantity());
                            stockModel.fireTableDataChanged();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "재고 업데이트 중 오류가 발생했습니다.");
                    }

                    // 출고 요청 테이블에서 삭제
                    outputRequestTableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "삭제할 출고 요청을 선택해주세요.");
                }
            }
        });

        // "전체삭제" 버튼 액션
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 모든 출고 요청 삭제 로직 구현
                int confirm = JOptionPane.showConfirmDialog(null, "모든 출고 요청을 삭제하시겠습니까?", "전체삭제", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // 모든 출고 요청을 재고에 반영
                    List<OutputRequestVO> allRequests = outputRequestTableModel.getAllRequests();
                    try {
                        ProductInfoTableModel stockModel = (ProductInfoTableModel) stockTable.getModel();
                        for (OutputRequestVO request : allRequests) {
                            ProductInfoProductVO stockProduct = stockModel.getProductByProductCode(request.getProduct_code());
                            if (stockProduct != null) {
                                stockProduct.setStock(stockProduct.getStock() + request.getRelease_quantity());
                            }
                        }
                        stockModel.fireTableDataChanged();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "재고 업데이트 중 오류가 발생했습니다.");
                        return;
                    }

                    // 출고 요청 테이블 모두 삭제
                    outputRequestTableModel.clearAll();
                }
            }
        });

        // "출고하기" 버튼 액션
        releaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProduct == null) {
                    JOptionPane.showMessageDialog(null, "출고할 제품을 선택해주세요.");
                    return;
                }

                String quantityText = quantityField.getText().trim();
                if (quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "출고 수량을 입력해주세요.");
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(null, "수량은 0보다 커야 합니다.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "유효한 수량을 입력해주세요.");
                    return;
                }

                // 재고 수량 확인 로직 추가
                if (quantity > selectedProduct.getStock()) {
                    JOptionPane.showMessageDialog(null, "출고 수량이 재고 수량을 초과합니다.");
                    return;
                }

                // 출고 날짜는 오늘 날짜로 설정
                String releaseDate = LocalDate.now().toString(); // "2024-11-11" 형식

                // 주문자명은 생성자에서 전달된 user_name 사용
                String userName = user_name;

                // 출고 요청 VO 생성
                OutputRequestVO request = new OutputRequestVO(
                        selectedProduct.getProduct_code(),
                        selectedProduct.getProduct_name(),
                        userName,
                        selectedProduct.getPrice(),
                        quantity,
                        releaseDate
                );

                // 테이블에 출고 요청 추가
                outputRequestTableModel.addOutputRequest(request);

                // 선택된 제품의 재고 수량 업데이트 (출고량만큼 감소)
                selectedProduct.setStock(selectedProduct.getStock() - quantity);
                // 테이블 모델 갱신
                ((ProductInfoTableModel) stockTable.getModel()).fireTableDataChanged();

                // 입력 필드 초기화
                quantityField.setText("");

                JOptionPane.showMessageDialog(null, "출고 요청이 추가되었습니다.");
            }
        });

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
            table.setPreferredScrollableViewportSize(new Dimension(800, 150));

            return table;
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 빈 테이블 반환
            return new JTable();
        }
    }
}
