package org.example.project1.inventory.test;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductInfoDAOTest {
    public static void main(String[] args) {
        ProductInfoDAO productInfoDAO = new ProductInfoDAO();
        Scanner scanner = new Scanner(System.in);

        try {
            // 현재 재고 상태 조회
            List<ProductInfoProductVO> inventoryStatusBefore = productInfoDAO.getInventoryStatus();
            System.out.println("현재 재고 상태:");
            printInventoryStatus(inventoryStatusBefore);

            // 사용자로부터 자재명 입력 받기
            System.out.print("재고를 감소시킬 자재명을 입력하세요: ");
            String productName = scanner.nextLine();

            // 사용자로부터 감소시킬 수량 입력 받기
            System.out.print("감소시킬 수량을 입력하세요: ");
            int decreaseQuantity = scanner.nextInt();

            // 재고 감소 실행
            int updatedRows = productInfoDAO.decreaseStockByProductName(productName, decreaseQuantity);
            if (updatedRows > 0) {
                System.out.println("재고 감소 성공: " + productName + ", 감소량: " + decreaseQuantity);
            } else {
                System.out.println("재고 감소 실패. 제품을 찾을 수 없거나 재고가 부족합니다.");
            }

            // 업데이트된 재고 상태 조회
            List<ProductInfoProductVO> inventoryStatusAfter = productInfoDAO.getInventoryStatus();
            System.out.println("재고 감소 후 상태:");
            printInventoryStatus(inventoryStatusAfter);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void printInventoryStatus(List<ProductInfoProductVO> inventoryList) {
        for (ProductInfoProductVO product : inventoryList) {
            System.out.println("코드: " + product.getCode() +
                    ", 이름: " + product.getProduct_name() +
                    ", 재고: " + product.getStock());
        }
    }
}
