package org.example.project1.dashboard.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
//오늘 입고상품 조회시 사용
@Getter
@Setter
@ToString
public class InputProductProductInfoVO {
    // input 테이블 정보
    private int inputNum;
    private String manufacturerCode;
    private LocalDate askingDate;
    private int warehousingQuantity;
    private LocalDate warehousingDate;

    // product 테이블 정보
    private int productCode;
    private String productName;

    // product_info 테이블 정보
    private String code; // 분류코드
    private int product_code; // 자재코드
    private String manufacturer_code; // 제조업체
    private int warehouse_id; // 창고번호
    private int price; // 단가
    private int stock; // 재고수량
    private String stock_date; // 입고예정일
}
