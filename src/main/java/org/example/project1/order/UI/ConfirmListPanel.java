//package org.example.project1.order.UI;
//
//import org.example.project1.order.DAO.OutputInfoDAO;
//import org.example.project1.order.DAO.ProductInfoDAO;
//import org.example.project1.order.VO.OutputInfoVO;
//import org.example.project1.order.VO.ProductInfoProductVO;
//
//import javax.swing.*;
//import javax.swing.border.LineBorder;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class ConfirmListPanel extends JPanel {
//    private String title;
//    private String toss_font = "머니그라피TTF Rounded";
//
//    // 오른쪽 패널 컴포넌트
//    private JLabel productNameLabel;
//    private JLabel productPriceLabel;
//    private JTextField quantityField;
//    private JButton releaseButton;
//
//    // 선택된 제품 정보
//    private ProductInfoProductVO selectedProduct;
//
//    public ConfirmListPanel(String title) {
//        this.title = title;
//        setPanel();
//        initUI();
//    }
//
//    // JPanel 설정
//    private void setPanel() {
//        setSize(1100, 450);
//        setBackground(Color.WHITE);
//        setLayout(null); // 레이아웃을 null로 설정
//    }
//
//    // UI 초기화
//    private void initUI() {
//        // 패널 초기화
//        removeAll();
//
//        // --------------------- 재고 확인 섹션 ---------------------
//        // 라벨 생성
//        JLabel stockCheckLabel = new JLabel("재고 확인");
//        stockCheckLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
//        stockCheckLabel.setBounds(20, 20, 100, 40); // 위치와 크기 설정
//        add(stockCheckLabel);
//
//        // 재고 확인 테이블 생성
//        JTable stockTable = createStockTable();    // 윗쪽 테이블
//        JScrollPane stockScrollPane = new JScrollPane(stockTable);
//        stockScrollPane.setBounds(20, 60, 800, 150); // 위치와 크기 설정
//        add(stockScrollPane);
//
//        // --------------------- 출고 요청 섹션 ---------------------
//        // 출고 요청 레이블 생성
//        JLabel outputRequestLabel = new JLabel("출고 요청");
//        outputRequestLabel.setFont(new Font(toss_font, Font.PLAIN, 18));
//        outputRequestLabel.setBounds(20, 230, 100, 40); // 위치와 크기 설정
//        add(outputRequestLabel);
//
//        // "삭제" 버튼 생성
//        JButton deleteButton = new JButton("삭제");
//        deleteButton.setFont(new Font(toss_font, Font.PLAIN, 14));
//        deleteButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색 (Pink)
//        deleteButton.setBorder(new LineBorder(Color.BLACK, 2));
//        deleteButton.setBounds(130, 230, 100, 40); // 위치와 크기 설정
//        add(deleteButton);
//
//        // "전체삭제" 버튼 생성
//        JButton deleteAllButton = new JButton("전체삭제");
//        deleteAllButton.setFont(new Font(toss_font, Font.PLAIN, 14));
//        deleteAllButton.setBackground(new Color(255, 182, 193)); // 연한 빨간색 (Pink)
//        deleteAllButton.setBorder(new LineBorder(Color.BLACK, 2));
//        deleteAllButton.setBounds(240, 230, 120, 40); // 위치와 크기 설정
//        add(deleteAllButton);
//
//        // 출고 요청 테이블 생성
//        JTable outputTable = createOutputTable();  // 아래쪽 테이블
//        JScrollPane outputScrollPane = new JScrollPane(outputTable);
//        outputScrollPane.setBounds(20, 270, 800, 150); // 위치와 크기 설정
//        add(outputScrollPane);
//
//        // --------------------- 오른쪽 출고 기능 섹션 ---------------------
//        // 제품명 라벨
//        JLabel nameLabel = new JLabel("제품명 :");
//        nameLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
//        nameLabel.setBounds(840, 60, 100, 30);
//        add(nameLabel);
//
//        productNameLabel = new JLabel("-");
//        productNameLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
//        productNameLabel.setBounds(940, 60, 150, 30);
//        add(productNameLabel);
//
//        // 가격 라벨
//        JLabel priceLabel = new JLabel("가격 :");
//        priceLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
//        priceLabel.setBounds(840, 100, 100, 30);
//        add(priceLabel);
//
//        productPriceLabel = new JLabel("-");
//        productPriceLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
//        productPriceLabel.setBounds(940, 100, 150, 30);
//        add(productPriceLabel);
//
//        // 수량 입력 필드
//        JLabel quantityLabel = new JLabel("출고 수량 :");
//        quantityLabel.setFont(new Font(toss_font, Font.PLAIN, 16));
//        quantityLabel.setBounds(840, 140, 100, 30);
//        add(quantityLabel);
//
//        quantityField = new JTextField();
//        quantityField.setFont(new Font(toss_font, Font.PLAIN, 16));
//        quantityField.setBounds(940, 140, 150, 30);
//        add(quantityField);
//
//        // "출고하기" 버튼 생성
//        releaseButton = new JButton("출고하기");
//        releaseButton.setFont(new Font(toss_font, Font.PLAIN, 16));
//        releaseButton.setBackground(new Color(144, 238, 144)); // 연한 녹색 (Light Green)
//        releaseButton.setBorder(new LineBorder(Color.BLACK, 2));
//        releaseButton.setBounds(840, 190, 250, 40);
//        add(releaseButton);
//
//        // --------------------- 테이블 선택 이벤트 ---------------------
//        stockTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                // 테이블 선택 이벤트 처리
//                if (!e.getValueIsAdjusting()) {
//                    int selectedRow = stockTable.getSelectedRow();
//                    if (selectedRow != -1) {
//                        ProductInfoProductVO product = ((ProductInfoTableModel) stockTable.getModel()).getProductAt(selectedRow);
//                        selectedProduct = product;
//                        productNameLabel.setText(product.getProduct_name()); // 제품명을 표시
//                        productPriceLabel.setText(String.valueOf(product.getPrice()));
//                    }
//                }
//            }
//        });
//
//        // --------------------- 버튼 액션 이벤트 ---------------------
//        // "삭제" 버튼 액션
//        deleteButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 선택된 출고 요청 삭제 로직 구현
//                int selectedRow = outputTable.getSelectedRow();
//                if (selectedRow != -1) {
//                    OutputInfoTableModel model = (OutputInfoTableModel) outputTable.getModel();
//                    OutputInfoVO output = model.getOutputAt(selectedRow);
//                    // DAO를 통해 삭제
//                    try {
//                        OutputInfoDAO dao = new OutputInfoDAO();
//                        dao.delete(output.getConfirm_num()); // 예시: confirm_num을 기준으로 삭제
//                        // 테이블 갱신
//                        initUI();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "삭제할 출고 요청을 선택해주세요.");
//                }
//            }
//        });
//
//        // "전체삭제" 버튼 액션
//        deleteAllButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // 모든 출고 요청 삭제 로직 구현
//                int confirm = JOptionPane.showConfirmDialog(null, "모든 출고 요청을 삭제하시겠습니까?", "전체삭제", JOptionPane.YES_NO_OPTION);
//                if (confirm == JOptionPane.YES_OPTION) {
//                    try {
//                        OutputInfoDAO dao = new OutputInfoDAO();
//                        dao.deleteAll(); // 모든 출고 요청 삭제 메서드 필요
//                        // 테이블 갱신
//                        initUI();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(null, "전체삭제 중 오류가 발생했습니다.");
//                    }
//                }
//            }
//        });
//
//        // "출고하기" 버튼 액션
//        releaseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (selectedProduct == null) {
//                    JOptionPane.showMessageDialog(null, "출고할 제품을 선택해주세요.");
//                    return;
//                }
//
//                String quantityText = quantityField.getText().trim();
//                if (quantityText.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "출고 수량을 입력해주세요.");
//                    return;
//                }
//
//                int quantity;
//                try {
//                    quantity = Integer.parseInt(quantityText);
//                    if (quantity <= 0) {
//                        JOptionPane.showMessageDialog(null, "수량은 0보다 커야 합니다.");
//                        return;
//                    }
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "유효한 수량을 입력해주세요.");
//                    return;
//                }
//
//                // 출고 로직 구현 (DAO를 통해 데이터베이스 업데이트)
//                try {
//                    OutputInfoDAO dao = new OutputInfoDAO();
//                    dao.releaseProduct(selectedProduct.getProduct_code(), quantity); // 예시 메서드
//                    JOptionPane.showMessageDialog(null, "출고가 완료되었습니다.");
//                    // 테이블 갱신
//                    initUI();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    JOptionPane.showMessageDialog(null, "출고 중 오류가 발생했습니다.");
//                }
//            }
//        });
//
//        // UI 갱신
//        revalidate();
//        repaint();
//    }
//
//    // 재고 확인 테이블 생성
//    private JTable createStockTable() {
//        try {
//            ProductInfoDAO productInfoDAO = new ProductInfoDAO();
//
//            // 실제 데이터 로드
//            java.util.List<ProductInfoProductVO> productList = productInfoDAO.getInventoryStatus();
//
//            // 테이블 모델 생성
//            ProductInfoTableModel model = new ProductInfoTableModel(productList);
//
//            // 테이블 생성
//            JTable table = new JTable(model);
//            table.setFillsViewportHeight(true);
//            table.setRowHeight(25);
//            table.getTableHeader().setReorderingAllowed(false);
//
//            // 테이블 크기 조정
//            table.setPreferredScrollableViewportSize(new Dimension(800, 150));
//
//            return table;
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 예외 발생 시 빈 테이블 반환
//            return new JTable();
//        }
//    }
//
//    // 출고 요청 테이블 생성
//    private JTable createOutputTable() {
//        try {
//            OutputInfoDAO outputInfoDAO = new OutputInfoDAO();
//
//            // 실제 데이터 로드
//            List<OutputInfoVO> outputList = outputInfoDAO.getAll();
//
//            // 테이블 모델 생성
//            OutputInfoTableModel model = new OutputInfoTableModel(outputList);
//
//            // 테이블 생성
//            JTable table = new JTable(model);
//            table.setFillsViewportHeight(true);
//            table.setRowHeight(25);
//            table.getTableHeader().setReorderingAllowed(false);
//
//            // 테이블 크기 조정
//            table.setPreferredScrollableViewportSize(new Dimension(800, 150));
//
//            return table;
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 예외 발생 시 빈 테이블 반환
//            return new JTable();
//        }
//    }
//}



//        finalReleaseButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                List<OutputRequestVO> allRequests = outputRequestTableModel.getAllRequests();
//
//                if (allRequests.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "출고 요청이 없습니다.");
//                    return;
//                }
//
//                // DAO 인스턴스 생성을 위한 Connection 객체 생성
//                Connection conn = null;
//                try {
//                    // DB 연결 정보 (실제 정보로 변경)
//                    String url = "jdbc:mysql://localhost:3306/project1"; // DB URL
//                    String user = "root"; // DB 사용자명
//                    String password = "1234"; // DB 비밀번호
//                    conn = DriverManager.getConnection(url, user, password);
//                    conn.setAutoCommit(false); // 트랜잭션 시작
//
//                    // DAO 인스턴스 생성 (Connection을 전달)
//                    ProductInfoDAO productInfoDAO = new ProductInfoDAO(conn);
//                    OutputInfoDAO outputInfoDAO = new OutputInfoDAO(conn);
//                    OrdererDAO ordererDAO = new OrdererDAO(conn);
//
//                    for (OutputRequestVO request : allRequests) {
//                        // 랜덤 8자리 양의 정수 confirm_num 생성
//                        int confirmNum = generateConfirmNum();
//
//                        // 재고 확인 테이블에서 해당 제품의 창고 ID 가져오기
//                        ProductInfoProductVO stockProduct = productInfoDAO.getProductByProductCodeAndWarehouseId(
//                                request.getProduct_code(),
//                                request.getWarehouse_id()
//                        );
//
//                        if (stockProduct == null) {
//                            JOptionPane.showMessageDialog(null, "제품 코드 " + request.getProduct_code() + "에 해당하는 창고 정보가 없습니다.");
//                            conn.rollback();
//                            return;
//                        }
//
//                        // 사용자명으로 user_id 가져오기
//                        int userId = ordererDAO.getUserIdByUserName(user_name);
//
//                        // OutputInfoVO 객체 생성
//                        OutputInfoVO outputInfo = new OutputInfoVO();
//                        outputInfo.setProduct_code(request.getProduct_code());
//                        outputInfo.setWarehouse_id(stockProduct.getWarehouse_id());
//                        outputInfo.setUser_id(userId);
//                        outputInfo.setConfirm_num(confirmNum);
//                        outputInfo.setConfirm_id(19981114);
//                        outputInfo.setStatus("대기중");
//                        outputInfo.setUnit_price(request.getPrice());
//                        outputInfo.setRelease_quantity(request.getRelease_quantity());
//                        outputInfo.setRelease_date(LocalDate.parse(request.getRelease_date()));
//
//                        // DB에 저장
//                        outputInfoDAO.insertOutputInfo(outputInfo);
//
//                        // product_info 테이블의 재고 수량 업데이트 (이미 출고량 감소됨)
//                        productInfoDAO.updateProductStock(
//                                stockProduct.getProduct_code(),
//                                stockProduct.getWarehouse_id(),
//                                stockProduct.getStock()
//                        );
//                    }
//
//                    // 트랜잭션 커밋
//                    conn.commit();
//
//                    JOptionPane.showMessageDialog(null, "출고가 완료되었습니다.");
//                    // 출고 요청 테이블 및 재고 테이블 초기화
//                    outputRequestTableModel.clearAll();
//                    productInfoDAO.refreshInventoryStatus(stockTable);
//                    updateTotalCost();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                    try {
//                        if (conn != null) {
//                            conn.rollback();
//                        }
//                    } catch (SQLException rollbackEx) {
//                        rollbackEx.printStackTrace();
//                    }
//                    JOptionPane.showMessageDialog(null, "출고 중 오류가 발생했습니다: " + ex.getMessage());
//                } finally {
//                    try {
//                        if (conn != null) {
//                            conn.setAutoCommit(true);
//                            conn.close();
//                        }
//                    } catch (SQLException closeEx) {
//                        closeEx.printStackTrace();
//                    }
//                }
//            }
//        });
