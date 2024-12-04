package org.example.project1.dashboard.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutputProductVO {
    private String productCode;       // product_code
    private int releaseQuantity;      // 출고 수량
    private String status;            // 출고 상태
    private String productName;       // 상품 이름
}
