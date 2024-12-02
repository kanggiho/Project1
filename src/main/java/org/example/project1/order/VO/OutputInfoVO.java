package org.example.project1.order.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OutputInfoVO {
        private int product_code;          // 제품 코드
        private int warehouse_id;          // 창고 ID
        private int user_id;               // 사용자 ID
        private int confirm_num;           // 확인 번호
        private Integer confirm_id;        // 확인 ID (nullable)
        private String status;             // 상태
        private int unit_price;            // 단가
        private int release_quantity;      // 출고 수량
        private LocalDate release_date;    // 출고 날짜

        // 기본 생성자, 매개변수 생성자 등 필요한 생성자 추가
        public OutputInfoVO() {}

        public OutputInfoVO(int product_code, int warehouse_id, int user_id, int confirm_num, Integer confirm_id,
                            String status, int unit_price, int release_quantity, LocalDate release_date) {
                this.product_code = product_code;
                this.warehouse_id = warehouse_id;
                this.user_id = user_id;
                this.confirm_num = confirm_num;
                this.confirm_id = confirm_id;
                this.status = status;
                this.unit_price = unit_price;
                this.release_quantity = release_quantity;
                this.release_date = release_date;
        }
}
