package org.example.project1._common.Model.VO;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrdererVO {
    private int uid; // 사용자 식별자
    private String id; // 아이디
    private String password; // 비밀번호
    private String license; // 사업자등록번호
    private String name; // 이름
    private String tel; // 전화번호
    private String loc; // 사업자등록주소지
    private String email; // 이메일
    private String grade; // 회원등급
}
