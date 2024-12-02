package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InputProductVO {
    private int product_code; // 자재코드
    private String product_name; // 자재명
    private int input_num; // 입고번호
    private String manufacturer_code; // 제조업체 코드
    private String asking_date; // 입고신청일
    private int warehoused_quantity; // 입고 수량
    private String warehoused_date; // 입고일
}
