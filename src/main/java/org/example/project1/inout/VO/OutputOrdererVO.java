package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OutputOrdererVO {
   private int user_id; // 주문자명
   private String license; // 사업자등록번호
   private String name; // 주문자 이름
   private String tel; // 주문자 전화번호
   private int confirm_num; // 승인 번호
   private int confirm_id; // 결재자
   private String status; // 승인여부
   private String release_date; // 출고량
}
