package com.gaeasoft.project.dto;

import java.sql.Timestamp;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	
	public Long id;
	public String writer;
	
    @Size(min = 1, max = 20, message = "비밀번호는 20자 이하이어야 합니다.")
	public String password;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$", message = "제목에는 특수 문자를 사용할 수 없습니다.")
    @Size(max = 100, message = "제목은 100자 이하이어야 합니다.")
	public String title;
	
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$", message = "내용에는 특수 문자를 사용할 수 없습니다.")
	@Size(max = 1000, message = "내용은 1000자 이하이어야 합니다.")
	public String content;
	
	public Timestamp writeTime;
	public int views;
	public int rowNum;
	
}
