package org.example.project1.inventory.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductInfoProductWarehouseInfoManufacturingVO {
    private String code;
    private int product_code;
    private String product_name;
    private String manufacturer_code;
    private String manufacturer_name;
    private int warehouse_id;
    private String warehouse_location;
    private int price;
    private int stock;
    private String stock_date;
}


