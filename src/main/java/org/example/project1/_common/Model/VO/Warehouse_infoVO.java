package org.example.project1._common.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Warehouse_infoVO {
    private int warehouse_id; // 창고번호
    private String warehouse_location; // 창고위치
    private String warehouse_temperature; // 창고온도
}
