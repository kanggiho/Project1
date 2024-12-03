package org.example.project1._common.Model.Service;

import org.example.project1._common.Model.DAO.ItemClassDAO;
import org.example.project1._common.Model.DAO.WarehouseInfoDAO;
import org.example.project1._common.Model.VO.ItemClassVO;
import org.example.project1._common.Model.VO.WarehouseInfoVO;

import javax.swing.*;
import java.util.ArrayList;

public class getComboBox {

    static int warehouse_id = 0;
    static String code = "";

    public getComboBox() {
        init();
    }
    private void init() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(600,400);

        JComboBox<String> warehouseComboBox = new JComboBox<>();
        JComboBox<String> itemclassComboBox = new JComboBox<>();

        try {

            // ---------------------- 창고 정보 콤보박스 ----------------------
//            WarehouseInfoDAO wareDAO = new WarehouseInfoDAO();
//            ArrayList<WarehouseInfoVO> wareList = wareDAO.getAll();
//
//
//            for(WarehouseInfoVO vo : wareList){
//                warehouseComboBox.addItem(vo.getWarehouse_name());
//            }
//            warehouseComboBox.setSelectedIndex(0);
//            warehouseComboBox.setSize(80,30);
//
//            warehouseComboBox.addActionListener(ActionEvent -> {
//                //todo : 해당 콤보박스가 클릭되었을때 수행 할 작업
//                try {
//                    warehouse_id = wareDAO.one(warehouseComboBox.getSelectedItem().toString()).getWarehouse_id();
//                } catch (Exception e) {
//
//                }
//                System.out.println(warehouse_id);
//            });

            // ---------------------- 아이템 정보 콤보박스 ----------------------
            ItemClassDAO itemDAO = new ItemClassDAO();
            ArrayList<ItemClassVO> itemList = itemDAO.getAll();

            for(ItemClassVO vo : itemList){
                itemclassComboBox.addItem(vo.getItem_classification());
            }

            itemclassComboBox.setSelectedIndex(0);
            itemclassComboBox.setSize(120,30);

            itemclassComboBox.addActionListener(ActionEvent -> {
                //todo : 해당 콤보박스가 클릭되었을때 수행 할 작업
                try {
                    code = itemDAO.one(itemclassComboBox.getSelectedItem().toString()).getCode();
                } catch (Exception e) {

                }
                System.out.println(code);
            });

            warehouseComboBox.setBounds(10,10,80,30);
            itemclassComboBox.setBounds(100,10,120,30);

            frame.add(warehouseComboBox);
            frame.add(itemclassComboBox);

        }catch (Exception e){

        }








        frame.setVisible(true);
    }
}
