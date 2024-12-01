// package org.example.project1.inventory.test;

// <<<<<<< jino
// import org.example.project1.inventory.DAO.ProductInfoDAO;
// import org.example.project1.inventory.VO.ProductInfoProductWarehouseInfoManufacturingVO;
// =======
// import org.example.project1.inventory.DAO.InventorySearchServiceDAO;
// import org.example.project1.inventory.VO.ProductInfoVO;
// >>>>>>> master

// import java.util.List;
// import java.util.Scanner;
// import java.util.InputMismatchException;


// public class InventorySearchServiceTest {
//     public static void main(String[] args) {
//         InventorySearchServiceDAO inventorySearchService = new InventorySearchServiceDAO();
//         Scanner scanner = new Scanner(System.in);

//         while (true) {
//             System.out.println("\n재고 검색 테스트");
//             System.out.println("1. 제품명으로 검색");
//             System.out.println("2. 창고 ID로 검색");
//             System.out.println("3. 제조업체명으로 검색");
//             System.out.println("4. 모든 매개변수로 검색");
//             System.out.println("5. 종료");
//             System.out.print("선택: ");

//             int choice;
//             try {
//                 choice = scanner.nextInt();
//                 scanner.nextLine(); // 버퍼 비우기
//             } catch (InputMismatchException e) {
//                 System.out.println("올바른 숫자를 입력해주세요.");
//                 scanner.nextLine(); // 잘못된 입력 비우기
//                 continue;
//             }

//             if (choice == 5) {
//                 break;
//             }

//             String productName = null;
//             Integer warehouseId = null;
//             String manufacturerName = null;

//             switch (choice) {
//                 case 1:
//                     System.out.print("제품명 입력: ");
//                     productName = scanner.nextLine();
//                     break;
//                 case 2:
//                     System.out.print("창고 ID 입력: ");
//                     try {
//                         warehouseId = scanner.nextInt();
//                         scanner.nextLine(); // 버퍼 비우기
//                     } catch (InputMismatchException e) {
//                         System.out.println("올바른 창고 ID를 입력해주세요.");
//                         scanner.nextLine(); // 잘못된 입력 비우기
//                         continue;
//                     }
//                     break;
//                 case 3:
//                     System.out.print("제조업체명 입력: ");
//                     manufacturerName = scanner.nextLine();
//                     break;
//                 case 4:
//                     System.out.print("제품명 입력: ");
//                     productName = scanner.nextLine();
//                     System.out.print("창고 ID 입력: ");
//                     try {
//                         warehouseId = scanner.nextInt();
//                         scanner.nextLine(); // 버퍼 비우기
//                     } catch (InputMismatchException e) {
//                         System.out.println("올바른 창고 ID를 입력해주세요.");
//                         scanner.nextLine(); // 잘못된 입력 비우기
//                         continue;
//                     }
//                     System.out.print("제조업체명 입력: ");
//                     manufacturerName = scanner.nextLine();
//                     break;
//                 default:
//                     System.out.println("잘못된 선택입니다.");
//                     continue;
//             }

//             List<ProductInfoVO> results = inventorySearchService.searchInventory(productName, warehouseId, manufacturerName);

//             if (results.isEmpty()) {
//                 System.out.println("검색 결과가 없습니다.");
//             } else {
//                 System.out.println("검색 결과:");
//                 System.out.printf("%-10s %-10s %-20s %-15s %-10s %-10s %-10s%n",
//                         "코드", "제품코드", "제조업체코드", "창고ID", "가격", "재고", "재고일자");
//                 System.out.println("----------------------------------------");
//                 for (ProductInfoVO vo : results) {
//                     System.out.printf("%-10s %-10d %-20s %-15d %-10d %-10d %-10s%n",
//                             vo.getCode(),
//                             vo.getProduct_code(),
//                             vo.getManufacturer_code(),
//                             vo.getWarehouse_id(),
//                             vo.getPrice(),
//                             vo.getStock(),
//                             vo.getStock_date());
//                 }
//             }
//         }

//         scanner.close();
//         System.out.println("프로그램을 종료합니다.");
//     }
// }