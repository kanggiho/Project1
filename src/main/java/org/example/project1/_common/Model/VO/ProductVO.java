package org.example.project1._common.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductVO {
    private int product_code; // 자재코드
    private String product_name; // 자재명
}
