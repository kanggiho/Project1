package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InputManuVO {
    private String manufacturer_code; // 제조업체명
    private String manufacturer_name; // 제조업체 이름
    private String sorting; // 업종
    private int input_num; // 입고번호
    private int product_code; // 자재코드
    private String asking_date; // 입고신청일
    private int warehoused_quantity; // 입고 수량
    private String warehoused_date; // 입고일
}
