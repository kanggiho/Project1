package org.example.Project1.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class confirmVO {
    private int approval_number; // 승인번호
    private int admin_id; // 결재자
}
