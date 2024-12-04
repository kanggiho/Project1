package org.example.project1.dashboard.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
// 인기품목 사용
@Getter // Lombok 어노테이션: 모든 필드에 대한 getter 메소드 자동 생성
@Setter // Lombok 어노테이션: 모든 필드에 대한 setter 메소드 자동 생성
@ToString // Lombok 어노테이션: toString 메소드 자동 생성
public class ProductOutputInfoVO {
    private int productCode; // 제품 코드
    private String productName; // 제품 이름
    private int warehouseId; // 창고 ID
    private int userId; // 사용자 ID
    private int confirmNum; // 확인 번호
    private int confirmId; // 확인자 ID
    private String status; // 상태 (예: 대기중, 승인, 거절)
    private int unitPrice; // 단가
    private int releaseQuantity; // 출고 수량
    private LocalDate releaseDate; // 출고 날짜
}