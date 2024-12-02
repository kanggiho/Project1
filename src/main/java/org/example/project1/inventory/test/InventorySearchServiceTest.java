package org.example.project1.inventory.test;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class InventorySearchServiceTest {
    public static void main(String[] args) {
        ProductInfoDAO dao = new ProductInfoDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n재고 검색 테스트");
            System.out.println("1. 제품명으로 검색");
            System.out.println("2. 창고 ID로 검색");
            System.out.println("3. 제조업체명으로 검색");
            System.out.println("4. 모든 매개변수로 검색");
            System.out.println("5. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            if (choice == 5) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            try {
                List<ProductInfoProductWarehouseInfoManufacturingVO> results = null;

                switch (choice) {
                    case 1:
                        System.out.print("제품명 입력: ");
                        String productName = scanner.nextLine();
                        results = dao.searchInventory(productName, null, null);
                        break;
                    case 2:
                        System.out.print("창고 ID 입력: ");
                        int warehouseId = scanner.nextInt();
                        results = dao.searchInventory(null, warehouseId, null);
                        break;
                    case 3:
                        System.out.print("제조업체명 입력: ");
                        String manufacturerName = scanner.nextLine();
                        results = dao.searchInventory(null, null, manufacturerName);
                        break;
                    case 4:
                        System.out.print("제품명 입력: ");
                        productName = scanner.nextLine();
                        System.out.print("창고 ID 입력: ");
                        warehouseId = scanner.nextInt();
                        scanner.nextLine(); // 버퍼 비우기
                        System.out.print("제조업체명 입력: ");
                        manufacturerName = scanner.nextLine();
                        results = dao.searchInventory(productName, warehouseId, manufacturerName);
                        break;
                    default:
                        System.out.println("잘못된 선택입니다.");
                        continue;
                }

                if (results != null && !results.isEmpty()) {
                    System.out.println("\n검색 결과:");
                    for (ProductInfoProductWarehouseInfoManufacturingVO vo : results) {
                        System.out.println("제품 코드: " + vo.getProduct_code());
                        System.out.println("제품명: " + vo.getProduct_name());
                        System.out.println("제조업체: " + vo.getManufacturer_name());
                        System.out.println("창고 위치: " + vo.getWarehouse_location());
                        System.out.println("가격: " + vo.getPrice());
                        System.out.println("재고: " + vo.getStock());
                        System.out.println("재고 날짜: " + vo.getStock_date());
                        System.out.println("--------------------");
                    }
                } else {
                    System.out.println("검색 결과가 없습니다.");
                }
            } catch (SQLException e) {
                System.out.println("데이터베이스 오류 발생: " + e.getMessage());
            }
        }

        scanner.close();
    }
}








