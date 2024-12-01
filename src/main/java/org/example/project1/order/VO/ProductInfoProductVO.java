package org.example.project1.order.VO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoProductVO {
    private String code;
    private int product_code;
    private String product_name;
    private String manufacturer_code;
    private int warehouse_id;
    private int price;
    private int stock;
    private String stock_date;

}
