package org.example.project1.inventory.test;

import org.example.project1.inventory.DAO.ProductInfoDAO;
import org.example.project1.inventory.VO.ProductInfoProductVO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InventoryInfoUpdate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductInfoDAO dao = new ProductInfoDAO();

        try {
            // 재고 현황 표시
            displayInventoryStatus(dao);

            // 사용자로부터 자재명 입력 받기
            System.out.println("업데이트할 자재명을 입력하세요:");
            String productName = scanner.nextLine();

            // 자재명으로 제품 정보 찾기
            ProductInfoProductVO product = findProductByName(dao, productName);

            if (product == null) {
                System.out.println("해당 자재명의 제품을 찾을 수 없습니다.");
                return;
            }

            // 수정 옵션 메뉴 출력 및 처리
            updateProductInfo(scanner, dao, product);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    /**
     * 재고 현황을 표시하는 메서드
     * @param dao ProductInfoDAO 객체
     * @throws SQLException SQL 예외 발생 시
     */
    private static void displayInventoryStatus(ProductInfoDAO dao) throws SQLException {
        List<ProductInfoProductVO> inventoryList = dao.getInventoryStatus();
        System.out.println("현재 재고 현황:");
        System.out.println("코드 | 제품코드 | 제품명 | 제조업체코드 | 창고ID | 가격 | 재고 | 입고예정일");
        for (ProductInfoProductVO item : inventoryList) {
            System.out.printf("%s | %d | %s | %s | %d | %d | %d | %s%n",
                    item.getCode(), item.getProduct_code(), item.getProduct_name(),
                    item.getManufacturer_code(), item.getWarehouse_id(),
                    item.getPrice(), item.getStock(), item.getStock_date());
        }
        System.out.println();
    }

    /**
     * 자재명으로 제품을 찾는 메서드
     * @param dao ProductInfoDAO 객체
     * @param productName 찾을 제품의 자재명
     * @return 찾은 ProductInfoProductVO 객체, 없으면 null
     * @throws SQLException SQL 예외 발생 시
     */
    private static ProductInfoProductVO findProductByName(ProductInfoDAO dao, String productName) throws SQLException {
        List<ProductInfoProductVO> inventoryList = dao.getInventoryStatus();
        for (ProductInfoProductVO item : inventoryList) {
            if (item.getProduct_name().equalsIgnoreCase(productName)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 제품 정보를 업데이트하는 메서드
     * @param scanner 사용자 입력을 위한 Scanner 객체
     * @param dao ProductInfoDAO 객체
     * @param product 업데이트할 제품 정보
     * @throws SQLException SQL 예외 발생 시
     */
    private static void updateProductInfo(Scanner scanner, ProductInfoDAO dao, ProductInfoProductVO product) throws SQLException {
        System.out.println("수정할 항목을 선택하세요:");
        System.out.println("1. 가격 수정");
        System.out.println("2. 입고예정일 수정");
        System.out.println("3. 창고 ID 수정");
        System.out.println("4. 가격과 입고예정일 수정");
        System.out.println("5. 가격과 창고 ID 수정");
        System.out.println("6. 입고예정일과 창고 ID 수정");
        System.out.println("7. 모든 정보 수정");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                System.out.println("새 가격을 입력하세요:");
                int price = Integer.parseInt(scanner.nextLine());
                dao.updatePrice(product.getCode(), product.getProduct_code(), price);
                break;
            case 2:
                System.out.println("새 입고예정일을 입력하세요 (YYYY-MM-DD):");
                String stockDate = scanner.nextLine();
                dao.updateStockDate(product.getCode(), product.getProduct_code(), stockDate);
                break;
            case 3:
                System.out.println("새 창고 ID를 입력하세요:");
                int warehouseId = Integer.parseInt(scanner.nextLine());
                dao.updateWarehouseId(product.getCode(), product.getProduct_code(), warehouseId);
                break;
            case 4:
                System.out.println("새 가격을 입력하세요:");
                price = Integer.parseInt(scanner.nextLine());
                System.out.println("새 입고예정일을 입력하세요 (YYYY-MM-DD):");
                stockDate = scanner.nextLine();
                dao.updatePriceAndStockDate(product.getCode(), product.getProduct_code(), price, stockDate);
                break;
            case 5:
                System.out.println("새 가격을 입력하세요:");
                price = Integer.parseInt(scanner.nextLine());
                System.out.println("새 창고 ID를 입력하세요:");
                warehouseId = Integer.parseInt(scanner.nextLine());
                dao.updatePriceAndWarehouseId(product.getCode(), product.getProduct_code(), price, warehouseId);
                break;
            case 6:
                System.out.println("새 입고예정일을 입력하세요 (YYYY-MM-DD):");
                stockDate = scanner.nextLine();
                System.out.println("새 창고 ID를 입력하세요:");
                warehouseId = Integer.parseInt(scanner.nextLine());
                dao.updateStockDateAndWarehouseId(product.getCode(), product.getProduct_code(), stockDate, warehouseId);
                break;
            case 7:
                System.out.println("새 가격을 입력하세요:");
                price = Integer.parseInt(scanner.nextLine());
                System.out.println("새 입고예정일을 입력하세요 (YYYY-MM-DD):");
                stockDate = scanner.nextLine();
                System.out.println("새 창고 ID를 입력하세요:");
                warehouseId = Integer.parseInt(scanner.nextLine());
                dao.updateAll(product.getCode(), product.getProduct_code(), price, stockDate, warehouseId);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

}