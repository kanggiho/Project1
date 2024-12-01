package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutputRequestVO {
    private int product_code;      // 제품 코드
    private String product_name;   // 제품명
    private String user_name;      // 주문자명
    private int price;             // 가격
    private int release_quantity;  // 출고량
    private String release_date;   // 출고 날짜

    public OutputRequestVO(int product_code, String product_name, String user_name, int price, int release_quantity, String release_date) {
        this.product_code = product_code;
        this.product_name = product_name;
        this.user_name = user_name;
        this.price = price;
        this.release_quantity = release_quantity;
        this.release_date = release_date;
    }
}
