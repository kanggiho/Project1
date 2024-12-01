package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductInfoVO {
    private String code; // 분류코드
    private int product_code; // 자재코드
    private String manufacturer_code; // 제조업체
    private int warehouse_id; // 창고번호
    private int price; // 단가
    private int stock; // 재고수량
    private String stock_date; // 입고예정일
}
