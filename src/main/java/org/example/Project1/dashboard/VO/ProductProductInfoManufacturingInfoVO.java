package org.example.project1.dashboard.VO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
// 랜덤 상품 추천시 사용

public class ProductProductInfoManufacturingInfoVO {
    // Product 테이블 정보
    private int productCode;
    private String productName;

    // Product_info 테이블 정보
    private String itemClassificationCode;
    private int warehouseId;
    private int price;
    private int stock;
    private LocalDate stockDate;

    // Manufacturing 테이블 정보
    private String manufacturerCode;
    private String manufacturerName;
    private String sorting;
    private int licenseNumber;
}
