package org.example.project1.inout.UI;

import org.example.project1.inout.DAO.*;
import org.example.project1.inout.VO.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class InputIncomingFrame extends JFrame {

    JLabel topLabel;
    JLabel productNameLabel, priceLabel, quantityLabel, askingDateLabel, manufacturerNameLabel, itemClassLabel, warehouseNameLabel;
    JTextField productNameField, priceField, quantityField, askingDateField;
    JComboBox<String> manufacturerNameComboBox, itemClassComboBox, warehouseNameComboBox;
    JButton confirmButton;

    ItemClassDAO itemDAO;
    ManufacturingDAO manuDAO;
    WarehouseInfoDAO wareDAO;

    static int warehouse_id = 0;
    static String itemclass_code = "";
    static String manufacturer_code = "";
    int newProductCode = new Random().nextInt(900000) + 100000;


    String toss_font = "머니그라피TTF Rounded";

    public InputIncomingFrame() {
        super("입고 요청");
        setFrame();
        setUI();
        setComboBox();
        setVisible(true);

    }

    public void setFrame() {
        setSize(600, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

    public void setUI() {
        // 폰트 설정
        Font font = new Font(toss_font, Font.PLAIN, 14);
        Font topFont = new Font(toss_font, Font.PLAIN, 24);

        // 상단 레이블
        topLabel = new JLabel("입고요청");
        topLabel.setFont(topFont);
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setBounds(0, 10, 600, 30);
        add(topLabel);

        // 좌측 패널 설정 (왼쪽 컴포넌트)
        JPanel leftPanel = new JPanel(null);
        leftPanel.setBounds(50, 60, 218, 160); // width 줄임 (250 → 218)
        add(leftPanel);

        // 우측 패널 설정 (오른쪽 컴포넌트)
        JPanel rightPanel = new JPanel(null);
        rightPanel.setBounds(288, 60, 250, 160); // x 위치 왼쪽으로 이동 (320 → 288)
        add(rightPanel);

        // 레이블과 필드 설정 (좌측 패널)
        // 상품명
        productNameLabel = new JLabel("상품명:");
        productNameLabel.setFont(font);
        productNameLabel.setBounds(0, 0, 80, 25);
        leftPanel.add(productNameLabel);

        productNameField = new JTextField();
        productNameField.setFont(font);
        productNameField.setBounds(90, 0, 128, 25); // width 줄임 (160 → 128)
        leftPanel.add(productNameField);

        // 가격
        priceLabel = new JLabel("가격:");
        priceLabel.setFont(font);
        priceLabel.setBounds(0, 40, 80, 25);
        leftPanel.add(priceLabel);

        priceField = new JTextField();
        priceField.setFont(font);
        priceField.setBounds(90, 40, 128, 25); // width 줄임
        leftPanel.add(priceField);

        // 수량
        quantityLabel = new JLabel("수량:");
        quantityLabel.setFont(font);
        quantityLabel.setBounds(0, 80, 80, 25);
        leftPanel.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setFont(font);
        quantityField.setBounds(90, 80, 128, 25); // width 줄임
        leftPanel.add(quantityField);

        // 요청일자
        askingDateLabel = new JLabel("요청일자:");
        askingDateLabel.setFont(font);
        askingDateLabel.setBounds(0, 120, 80, 25);
        leftPanel.add(askingDateLabel);

        askingDateField = new JTextField(); // 전역 변수로 변경
        askingDateField.setFont(font);
        askingDateField.setBounds(90, 120, 128, 25); // width 줄임
        leftPanel.add(askingDateField);

        // 레이블과 콤보박스 설정 (우측 패널)
        // 제조사명
        manufacturerNameLabel = new JLabel("제조사명:");
        manufacturerNameLabel.setFont(font);
        manufacturerNameLabel.setBounds(0, 0, 100, 25);
        rightPanel.add(manufacturerNameLabel);

        manufacturerNameComboBox = new JComboBox<>();
        manufacturerNameComboBox.addItem("선택하세요.");
        manufacturerNameComboBox.setFont(font);
        manufacturerNameComboBox.setBounds(110, 0, 130, 25); // 위치 조정
        rightPanel.add(manufacturerNameComboBox);

        // 품목구분
        itemClassLabel = new JLabel("품목구분:");
        itemClassLabel.setFont(font);
        itemClassLabel.setBounds(0, 40, 100, 25);
        rightPanel.add(itemClassLabel);

        itemClassComboBox = new JComboBox<>();
        itemClassComboBox.addItem("선택하세요.");
        itemClassComboBox.setFont(font);
        itemClassComboBox.setBounds(110, 40, 130, 25); // 위치 조정
        rightPanel.add(itemClassComboBox);

        // 창고명
        warehouseNameLabel = new JLabel("창고명:");
        warehouseNameLabel.setFont(font);
        warehouseNameLabel.setBounds(0, 80, 100, 25);
        rightPanel.add(warehouseNameLabel);

        warehouseNameComboBox = new JComboBox<>();
        warehouseNameComboBox.addItem("선택하세요.");
        warehouseNameComboBox.setFont(font);
        warehouseNameComboBox.setBounds(110, 80, 130, 25); // 위치 조정
        rightPanel.add(warehouseNameComboBox);

        // 확인 버튼
        confirmButton = new JButton("확인");
        confirmButton.setFont(font);
        confirmButton.setBackground(Color.BLACK);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBounds(250, 240, 100, 30); // 하단 중앙 위치 유지
        add(confirmButton);
        confirmButton.addActionListener(ActionEvent -> {
            makeProduct();
            makeInput();
            makeProductInfo();
        });
    }

    private void setComboBox() {
        try {

            // ---------------------- 창고 정보 콤보박스 ----------------------
            wareDAO = new WarehouseInfoDAO();
            ArrayList<WarehouseInfoVO> wareList = wareDAO.getAll();


            for (WarehouseInfoVO vo : wareList) {
                warehouseNameComboBox.addItem(vo.getWarehouse_name());
            }
            warehouseNameComboBox.setSelectedIndex(0);

            warehouseNameComboBox.addActionListener(ActionEvent -> {
                //todo : 해당 콤보박스가 클릭되었을때 수행 할 작업
                try {
                    warehouse_id = wareDAO.one(warehouseNameComboBox.getSelectedItem().toString()).getWarehouse_id();
                } catch (Exception e) {

                }
            });

            // ---------------------- 아이템 정보 콤보박스 ----------------------
            itemDAO = new ItemClassDAO();
            ArrayList<ItemClassVO> itemList = itemDAO.getAll();

            for (ItemClassVO vo : itemList) {
                itemClassComboBox.addItem(vo.getItem_classification());
            }

            itemClassComboBox.setSelectedIndex(0);

            itemClassComboBox.addActionListener(ActionEvent -> {
                //todo : 해당 콤보박스가 클릭되었을때 수행 할 작업
                try {
                    itemclass_code = itemDAO.one(itemClassComboBox.getSelectedItem().toString()).getCode();
                } catch (Exception e) {

                }
            });

            // ---------------------- 제조 업체 콤보박스 ----------------------
            manuDAO = new ManufacturingDAO();
            ArrayList<ManufacturingVO> manuList = manuDAO.getAll();

            for (ManufacturingVO vo : manuList) {
                manufacturerNameComboBox.addItem(vo.getManufacturer_name());
            }

            manufacturerNameComboBox.setSelectedIndex(0);

            manufacturerNameComboBox.addActionListener(ActionEvent -> {
                //todo : 해당 콤보박스가 클릭되었을때 수행 할 작업
                try {
                    manufacturer_code = manuDAO.one(manufacturerNameComboBox.getSelectedItem().toString()).getManufacturer_code();

                } catch (Exception e) {

                }
            });

            itemclass_code = itemDAO.one(itemClassComboBox.getSelectedItem().toString()).getCode();
            manufacturer_code = manuDAO.one(manufacturerNameComboBox.getSelectedItem().toString()).getManufacturer_code();
            warehouse_id = wareDAO.one(warehouseNameComboBox.getSelectedItem().toString()).getWarehouse_id();

        } catch (Exception e) {

        }
    }
    private String getToday(){
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 형식에 맞게 변환
        String formattedDate = today.format(formatter);

        return formattedDate;
    }

    private void makeProduct() {
        try {
            ProductVO productVO = new ProductVO();
            ProductDAO productDAO = new ProductDAO();

            productVO.setProduct_code(newProductCode);
            productVO.setProduct_name(productNameField.getText());
            productDAO.insert(productVO);
        } catch (Exception e) {

        }
    }

    private void makeInput() {
        try {
            InputVO inputVO = new InputVO();
            InputDAO inputDAO = new InputDAO();

            inputVO.setInputNum(-1);
            inputVO.setProductCode(newProductCode);
            inputVO.setManufacturerCode(manufacturer_code);
            inputVO.setAskingDate(askingDateField.getText());
            inputVO.setWarehousedQuantity(Integer.parseInt(quantityField.getText()));
            inputVO.setWarehousedDate(getToday());
            inputDAO.insertToInput(inputVO);
        } catch (Exception e) {

        }

    }

    private void makeProductInfo() {
        try {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            ProductInfoDAO productInfoDAO = new ProductInfoDAO();

            productInfoVO.setCode(itemclass_code);
            productInfoVO.setProduct_code(newProductCode);
            productInfoVO.setWarehouse_id(warehouse_id);
            productInfoVO.setManufacturer_code(manufacturer_code);
            productInfoVO.setPrice(Integer.parseInt(priceField.getText()));
            productInfoVO.setStock(Integer.parseInt(quantityField.getText()));
            productInfoVO.setStock_date(getToday());
            productInfoDAO.insert(productInfoVO);
        } catch (Exception e) {

        }


    }
}
