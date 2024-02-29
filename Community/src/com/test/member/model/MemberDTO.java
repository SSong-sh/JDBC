package com.test.member.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//DTO, Data Transfer Object
//- 계층간 데이터를 전달하는 용도

//lombok이 자동으로 만들어줌 > 반복적인 코드를 줄일 수 있음
@Data //getter + setter + toString 
public class MemberDTO {
	
	private String id;
	private String pw;
	

}
