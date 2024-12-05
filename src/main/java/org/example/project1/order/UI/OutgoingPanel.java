package org.example.project1.order.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.example.project1._common.utility.ColorSet;
import org.example.project1.order.DAO.OutputInfoDAO;
import org.example.project1.order.DAO.ProductInfoDAO;
import org.example.project1.order.DAO.OrdererDAO;
import org.example.project1.order.TableModel.OutputRequestTableModel;
import org.example.project1.order.TableModel.ProductInfoTableModel;
import org.example.project1.order.VO.OutputInfoVO;
import org.example.project1.order.VO.OutputRequestVO;
import org.example.project1.order.VO.ProductInfoProductVO;

public class OutgoingPanel extends JPanel {

    private String title;
    private String user_name;
    private String toss_font = "머니그라피TTF Rounded";

    // 오른쪽 패널 컴포넌트
    private JLabel productNameLabel;
    private JLabel productPriceLabel;
    private JTextField quantityField;
    private JButton releaseButton;
    private JButton finalReleaseButton;

    // 선택된 제품 정보
    private ProductInfoProductVO selectedProduct;

    // 새로운 출고 요청 테이블 모델
    private OutputRequestTableModel outputRequestTableModel;

    // 재고 확인 테이블
    private JTable stockTable;

    // 총 출고 비용 라벨
    private JLabel totalCostLabel;

    // DAO 클래스 멤버 변수
    private ProductInfoDAO productInfoDAO;

    public OutgoingPanel(String title, String user_name) {
        this.title = title;
        this.user_name = user_name;
        setPanel();
        initUI();
        refresh();
    }

    public void refresh(){
        try {
            productInfoDAO.refreshInventoryStatus(stockTable);
        }catch (Exception e) {

        }
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
        stockTable = createStockTable();    // 윗쪽 테이블
        JScrollPane stockScrollPane = new JScrollPane(stockTable);
        stockScrollPane.setBounds(20, 60, 800, 150); // 위치와 크기 설정
        add(stockScrollPane);

        // --------------------- 출고 요청 섹션 ---------------------
        // 출고 요청 레이블 생성
        JLabel outputRequestLabel = new JLabel("발주 요청");
        outputRequestLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
        outputRequestLabel.setBounds(20, 230, 100, 40); // 위치와 크기 설정
        add(outputRequestLabel);

        // "삭제" 버튼 생성
        JButton deleteButton = new JButton("삭제");
        deleteButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteButton.setBackground(ColorSet.color_button[0]); // 연한 빨간색 (Pink)
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(new LineBorder(Color.GRAY, 1));
        deleteButton.setBounds(100, 235, 80, 25); // 위치와 크기 설정
        add(deleteButton);

        // "전체삭제" 버튼 생성
        JButton deleteAllButton = new JButton("전체삭제");
        deleteAllButton.setFont(new Font(toss_font, Font.PLAIN, 14));
        deleteAllButton.setBackground(Color.RED);
        deleteAllButton.setForeground(Color.WHITE);
        deleteAllButton.setBorder(new LineBorder(Color.GRAY, 1));
        deleteAllButton.setBounds(190, 235, 80, 25); // 위치와 크기 설정
        add(deleteAllButton);

        // --------------------- 새로운 출고 요청 테이블 ---------------------
        outputRequestTableModel = new OutputRequestTableModel();
        JTable outputRequestTable = new JTable(outputRequestTableModel);
        outputRequestTable.setFillsViewportHeight(true);
        outputRequestTable.setRowHeight(20);
        outputRequestTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane outputRequestScrollPane = new JScrollPane(outputRequestTable);
        outputRequestScrollPane.setBounds(20, 270, 800, 150); // 위치와 크기 설정
        add(outputRequestScrollPane);

        // --------------------- 총 출고 비용 라벨 ---------------------
        JLabel totalCostTextLabel = new JLabel("총 발주 비용:");
        totalCostTextLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        totalCostTextLabel.setBounds(840, 240, 100, 30);
        add(totalCostTextLabel);

        totalCostLabel = new JLabel("0");
        totalCostLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        totalCostLabel.setBounds(940, 240, 150, 30);
        add(totalCostLabel);

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
        JLabel quantityLabel = new JLabel("발주 수량:");
        quantityLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
        quantityLabel.setBounds(840, 140, 100, 30);
        add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setFont(new Font(toss_font, Font.PLAIN, 16));
        quantityField.setBounds(940, 140, 150, 30);
        add(quantityField);

        // "발주하기" 버튼 생성
        releaseButton = new JButton("발주하기");
        releaseButton.setFont(new Font(toss_font, Font.PLAIN, 16));
        releaseButton.setBackground(ColorSet.color_button[2]); // 연한 녹색 (Light Green)
        releaseButton.setForeground(Color.white);
        releaseButton.setBorder(new LineBorder(Color.GRAY, 1));
        releaseButton.setBounds(840, 190, 250, 40);
        add(releaseButton);

        // --------------------- 최종 발주하기 버튼 ---------------------
        finalReleaseButton = new JButton("최종 발주");
        finalReleaseButton.setFont(new Font(toss_font, Font.PLAIN, 20));
        finalReleaseButton.setBackground(new Color(19, 102, 19)); // 짙은 녹색
        finalReleaseButton.setForeground(Color.WHITE);
        finalReleaseButton.setBorder(new LineBorder(Color.GRAY, 1));
        finalReleaseButton.setBounds(840, 300, 250, 100);
        add(finalReleaseButton);

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
                    JOptionPane.showMessageDialog(null, "삭제할 발주 요청을 선택해주세요.");
                }
            }
        });

        // "전체삭제" 버튼 액션
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 모든 출고 요청 삭제 로직 구현
                int confirm = JOptionPane.showConfirmDialog(null, "모든 발주 요청을 삭제하시겠습니까?", "전체삭제", JOptionPane.YES_NO_OPTION);
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
                    JOptionPane.showMessageDialog(null, "발주할 제품을 선택해주세요.");
                    return;
                }

                String quantityText = quantityField.getText().trim();
                if (quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "발주 수량을 입력해주세요.");
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
                    JOptionPane.showMessageDialog(null, "발주 수량이 재고 수량을 초과합니다.");
                    return;
                }

                // 출고 날짜는 오늘 날짜로 설정
                String releaseDate = LocalDate.now().toString(); // "2024-11-11" 형식

                // 주문자명은 생성자에서 전달된 user_name 사용
                String userName = user_name;

                // 출고 요청 VO 생성 (warehouse_id 포함)
                OutputRequestVO request = new OutputRequestVO(
                        selectedProduct.getProduct_code(),
                        selectedProduct.getProduct_name(),
                        userName,
                        selectedProduct.getPrice(),
                        quantity,
                        releaseDate,
                        selectedProduct.getWarehouse_id() // 창고 ID 추가
                );

                // 테이블에 출고 요청 추가
                outputRequestTableModel.addOutputRequest(request);

                // 선택된 제품의 재고 수량 업데이트 (출고량만큼 감소)
                selectedProduct.setStock(selectedProduct.getStock() - quantity);
                // 테이블 모델 갱신
                ((ProductInfoTableModel) stockTable.getModel()).fireTableDataChanged();

                // 입력 필드 초기화
                quantityField.setText("");

                //JOptionPane.showMessageDialog(null, "출고 요청이 추가되었습니다.");
            }
        });

        // "최종 출고하기" 버튼 액션


        finalReleaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<OutputRequestVO> allRequests = outputRequestTableModel.getAllRequests();

                if (allRequests.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "출고 요청이 없습니다.");
                    return;
                }

                // DAO 인스턴스 생성을 위한 Connection 객체 생성
                Connection conn = null;
                try {
                    // DB 연결 정보 (실제 정보로 변경)
                    String url = "jdbc:mysql://localhost:3306/project1"; // DB URL
                    String user = "root"; // DB 사용자명
                    String password = "1234"; // DB 비밀번호
                    conn = DriverManager.getConnection(url, user, password);
                    conn.setAutoCommit(false); // 트랜잭션 시작

                    // DAO 인스턴스 생성 (Connection을 전달)
                    ProductInfoDAO productInfoDAO = new ProductInfoDAO(conn);
                    OutputInfoDAO outputInfoDAO = new OutputInfoDAO(conn);
                    OrdererDAO ordererDAO = new OrdererDAO(conn);

                    for (OutputRequestVO request : allRequests) {
                        // 재고 확인 테이블에서 해당 제품의 정보를 가져옴
                        ProductInfoProductVO stockProduct = productInfoDAO.getProductByProductCodeAndWarehouseId(
                                request.getProduct_code(),
                                request.getWarehouse_id()
                        );

                        // 랜덤 8자리 양의 정수 confirm_num 생성
                        int confirmNum = generateConfirmNum();

                        if (stockProduct == null) {
                            JOptionPane.showMessageDialog(null, "제품 코드 " + request.getProduct_code() + "에 해당하는 창고 정보가 없습니다.");
                            conn.rollback();
                            return;
                        }

                        // 현재 재고에서 출고량을 차감한 새 재고 계산
                        int updatedStock = stockProduct.getStock() - request.getRelease_quantity();

                        // product_info 테이블의 재고 업데이트
                        productInfoDAO.updateProductStock(
                                stockProduct.getProduct_code(),
                                stockProduct.getWarehouse_id(),
                                updatedStock
                        );

                        // 사용자명으로 user_id 가져오기
                        int userId = ordererDAO.getUserIdByUserName(user_name);

                        // OutputInfoVO 객체 생성
                        OutputInfoVO outputInfo = new OutputInfoVO();
                        outputInfo.setProduct_code(request.getProduct_code());
                        outputInfo.setWarehouse_id(stockProduct.getWarehouse_id());
                        outputInfo.setUser_id(userId);
                        outputInfo.setConfirm_num(confirmNum);
                        outputInfo.setConfirm_id(19981114);
                        outputInfo.setStatus("대기중");
                        outputInfo.setUnit_price(request.getPrice());
                        outputInfo.setRelease_quantity(request.getRelease_quantity());
                        outputInfo.setRelease_date(LocalDate.parse(request.getRelease_date()));

                        // DB에 저장
                        outputInfoDAO.insertOutputInfo(outputInfo);



                    }

                    // 트랜잭션 커밋
                    conn.commit();

                    JOptionPane.showMessageDialog(null, "발주가 완료되었습니다.");
                    // 출고 요청 테이블 초기화
                    outputRequestTableModel.clearAll();
                    productInfoDAO.refreshInventoryStatus(stockTable);
                    updateTotalCost();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    try {
                        if (conn != null) {
                            conn.rollback();
                        }
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "발주 중 오류가 발생했습니다: " + ex.getMessage());
                } finally {
                    try {
                        if (conn != null) {
                            conn.setAutoCommit(true);
                            conn.close();
                        }
                    } catch (SQLException closeEx) {
                        closeEx.printStackTrace();
                    }
                }
            }
        });

        // --------------------- 커스텀 셀 에디터 설정 ---------------------
        // '출고 수량' 칼럼 (인덱스 4)용 커스텀 셀 에디터 생성
        TableColumn releaseQuantityColumn = outputRequestTable.getColumnModel().getColumn(4);
        releaseQuantityColumn.setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean stopCellEditing() {
                String input = (String) getCellEditorValue();
                int row = outputRequestTable.getEditingRow();
                int productCode = (int) outputRequestTable.getModel().getValueAt(row, 0);
                int newQuantity;
                try {
                    newQuantity = Integer.parseInt(input);
                    if (newQuantity <= 0) {
                        JOptionPane.showMessageDialog(null, "수량은 0보다 커야 합니다.");
                        return false;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "유효한 수량을 입력해주세요.");
                    return false;
                }

                // Get the stock model
                ProductInfoTableModel stockModel = (ProductInfoTableModel) stockTable.getModel();
                ProductInfoProductVO stockProduct = stockModel.getProductByProductCode(productCode);
                if (stockProduct != null) {
                    OutputRequestVO request = outputRequestTableModel.getOutputRequestAt(row);
                    int currentReleaseQuantity = request.getRelease_quantity();
                    int availableStock = stockProduct.getStock() + currentReleaseQuantity;

                    if (newQuantity > availableStock) {
                        JOptionPane.showMessageDialog(null, "발주 수량이 재고 수량을 초과합니다.");
                        return false;
                    }

                    // Adjust the stock
                    int delta = newQuantity - currentReleaseQuantity;
                    stockProduct.setStock(stockProduct.getStock() - delta);
                    stockModel.fireTableDataChanged();

                    // Update the request's release_quantity
                    request.setRelease_quantity(newQuantity);
                    outputRequestTableModel.fireTableRowsUpdated(row, row);

                    // Update the total cost
                    updateTotalCost();
                }

                return super.stopCellEditing();
            }
        });

        // --------------------- 총 출고 비용 업데이트 ---------------------
        // 테이블 모델 리스너 추가하여 총 출고 비용 갱신
        outputRequestTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                updateTotalCost();
            }
        });

        // UI 갱신
        revalidate();
        repaint();
    }

    // 랜덤 8자리 양의 정수 생성 메서드
    private int generateConfirmNum() {
        return (int) (10000000 + Math.random() * 90000000);
    }

    // 재고 확인 테이블 생성
    private JTable createStockTable() {
        try {
            // Connection 생성
            String url = "jdbc:mysql://localhost:3306/project1"; // DB URL
            String user = "root"; // DB 사용자명
            String password = "1234"; // DB 비밀번호
            Connection conn = DriverManager.getConnection(url, user, password);

            // 클래스 멤버 변수에 ProductInfoDAO 할당
            productInfoDAO = new ProductInfoDAO(conn);

            // 실제 데이터 로드
            List<ProductInfoProductVO> productList = productInfoDAO.getInventoryStatus();

            // 테이블 모델 생성
            ProductInfoTableModel model = new ProductInfoTableModel(productList);

            // 테이블 생성
            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setRowHeight(20);
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

    // 총 출고 비용 계산 및 업데이트 메서드
    private void updateTotalCost() {
        int totalCost = 0;
        List<OutputRequestVO> requests = outputRequestTableModel.getAllRequests();
        for (OutputRequestVO request : requests) {
            totalCost += request.getPrice() * request.getRelease_quantity();
        }
        totalCostLabel.setText(String.valueOf(totalCost));
    }
}