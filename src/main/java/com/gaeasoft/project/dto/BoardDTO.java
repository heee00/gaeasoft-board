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
	
	@Size(min = 1, max = 20, message = "비밀번호는 1글자 이상, 20글자 이하입니다.")
	public String password;
	
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{1,100}$", message = "제목은 특수문자를 제외한 100자 이하여야 합니다.")
	public String title;
	
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{1,1000}$", message = "내용은 특수문자를 제외한 1000자 이하여야 합니다.")
	public String content;
	
	public Timestamp writeTime;
	public int views;
	public int rowNum;
	
}
