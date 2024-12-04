package org.example.project1.dashboard.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
//오늘 입고상품 조회시 사용
@Getter
@Setter
@ToString
public class InputProductWarehouseInfoVO {
    // input 테이블 정보
    private int inputNum;
    private String manufacturerCode;
    private LocalDate askingDate;
    private int warehousingQuantity;
    private LocalDate warehousingDate;

    // product 테이블 정보
    private int productCode;
    private String productName;

    // warehouse_info 테이블 정보
    private int warehouseId;
    private String warehouseName;
    private String warehouseLocation;
    private String warehouseTemperature;
}
