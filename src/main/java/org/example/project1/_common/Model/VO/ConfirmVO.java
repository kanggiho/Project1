package org.example.project1._common.Model.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConfirmVO {
    private int approval_number; // 승인번호
    private int admin_id; // 결재자
}
