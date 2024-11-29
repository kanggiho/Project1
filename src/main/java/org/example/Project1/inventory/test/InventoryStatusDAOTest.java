package org.example.project1.inventory.test;

import org.example.project1.inventory.DAO.InventoryStatusDAO;
import org.example.project1.inventory.VO.ProductInfoVO;

import java.util.List;

public class InventoryStatusDAOTest {

    public static void main(String[] args) {
        // InventoryStatusDAO 인스턴스 생성
        InventoryStatusDAO inventoryStatusDAO = new InventoryStatusDAO();

        // 재고 현황 조회
        List<ProductInfoVO> inventoryList = inventoryStatusDAO.getInventoryStatus();

        // 결과 출력
        System.out.println("재고 현황:");
        System.out.println("----------------------------------------");
        System.out.printf("%-10s %-10s %-20s %-15s %-10s %-10s %-10s%n",
                "코드", "제품코드", "제조업체코드", "창고ID", "가격", "재고", "재고일자");
        System.out.println("----------------------------------------");

        for (ProductInfoVO productInfo : inventoryList) {
            System.out.printf("%-10s %-10d %-20s %-15d %-10d %-10d %-10s%n",
                    productInfo.getCode(),
                    productInfo.getProduct_code(),
                    productInfo.getManufacturer_code(),
                    productInfo.getWarehouse_id(),
                    productInfo.getPrice(),
                    productInfo.getStock(),
                    productInfo.getStock_date());
        }

        System.out.println("----------------------------------------");
        System.out.println("총 " + inventoryList.size() + "개의 재고 항목이 조회되었습니다.");
    }
}