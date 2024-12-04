package org.example.project1.inout.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminVO {
    private int id; // 사번
    private String password; // 비밀번호
    private String name; // 이름
    private String tel; // 전화번호
}
