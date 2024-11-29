package org.example.project1._common.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutputInfoVO {
    private int product_code; // 자재코드
    private int warehouse_id; // 창고번호
    private int user_id; // 주문자명
    private int approval_number; // 승인번호
    private int confirm_id; // 결재자
    private int unit_price; // 단가
    private int count; // 재고수량
    private int incoming_quantity; // 입고량
    private int release_quantity; // 출고량
    private String incoming_date; //입고 날짜
    private String release_date; //출고 날짜
}
