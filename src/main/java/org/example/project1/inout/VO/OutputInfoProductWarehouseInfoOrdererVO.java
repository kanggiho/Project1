package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutputInfoProductWarehouseInfoOrdererVO {
    private int product_code; // 자재코드
    private String product_name; // 자재명
    private String warehouse_name; // 창고이름
    private String id; // 아이디
    private String orderer_name; // 주문자 이름
    private int unit_price; // 단가
    private int release_quantity; // 발주량
    private int confirm_num; // 승인번호
    private String release_date; // 발주날짜
    private int confirm_id; // 결재자
    private String status; // 승인여부
}
