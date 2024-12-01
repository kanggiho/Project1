package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductInfoProductVO {
    private String code;                 // 코드
    private int product_code;            // 제품 코드
    private String product_name;         // 제품명
    private String manufacturer_code;    // 제조사 코드
    private int warehouse_id;            // 창고 ID
    private int price;                   // 가격
    private int stock;                   // 재고
    private String stock_date;           // 재고 날짜

    // 기본 생성자 및 기타 필요한 메서드가 있을 수 있습니다.
}
