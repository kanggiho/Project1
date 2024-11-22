package org.example.Project1.VO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ordererVO {
    private int UID; // 사용자 식별자
    private String ID; // 아이디
    private String password; // 비밀번호
    private int license; // 사업자등록번호
    private String name; // 이름
    private String tel; // 전화번호
    private String loc; // 사업자등록주소지
}
