package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class InputVO {
    private int inputNum; //입고 벊호
    private String manufacturerCode; //제조업체 코드
    private int productCode; //물품 코드
    private String askingDate; //입고신청일
    private int warehousedQuantity; //입고 수량
    private String warehousedDate; //입고일
}