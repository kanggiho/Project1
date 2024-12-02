package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OutputRequestVO {
    private int product_code;        // 제품 코드
    private String product_name;     // 제품명
    private String user_name;        // 사용자명
    private int price;               // 단가
    private int release_quantity;    // 출고 수량
    private String release_date;     // 출고 날짜
    private int warehouse_id;        // 창고 ID (추가된 필드)

    // 생성자
    public OutputRequestVO(int product_code, String product_name, String user_name, int price, int release_quantity, String release_date, int warehouse_id) {
        this.product_code = product_code;
        this.product_name = product_name;
        this.user_name = user_name;
        this.price = price;
        this.release_quantity = release_quantity;
        this.release_date = release_date;
        this.warehouse_id = warehouse_id;
    }
}
