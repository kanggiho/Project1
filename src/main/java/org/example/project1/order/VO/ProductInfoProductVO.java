package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductInfoProductVO {
    private String code;
    private int product_code;
    private String product_name;
    private String manufacturer_code;
    private int warehouse_id;
    private int price;
    private int stock;
    private String stock_date;

    // 기존 생성자, getters, setters 등이 이미 정의되어 있다고 가정

    public void setStock(int stock) {
        this.stock = stock;
    }
}
