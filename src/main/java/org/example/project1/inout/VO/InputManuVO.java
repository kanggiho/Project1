package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class InputManuVO {
    private String manufacturer_code;
    private String manufacturer_name;
    private String sorting;
    private int input_num;
    private int product_code;
    private String asking_date;
    private int warehoused_quantity;
    private String warehoused_date;
}
