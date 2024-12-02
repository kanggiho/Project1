package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutputAdminVO {
    private int confirm_id;
    private int confirm_num;
    private String name;
    private String tel;
    private int user_id;
    private String status;
    private String release_date;
}
