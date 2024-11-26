package org.example.Project1.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class warehouse_infoVO {
    private int warehouse_id; // 창고번호
    private String warehouse_location; // 창고위치
    private String warehouse_temperature; // 창고온도
}
