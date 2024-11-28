package org.example.project1._common.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class InputVO {
    private int inputNum;
    private String manufacturerCode;
    private int productCode;
    private String askingDate;
    private String warehousedQuantity;
    private String warehousedDate;
}