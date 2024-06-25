package com.gaeasoft.project.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotBlank;
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
	
	public Long noticeSeq;
	public String memberId;
	
	@NotBlank(message = "비밀번호를 입력해 주세요.")
    @Size(max = 20, message = "비밀번호는 20자 이하이어야 합니다.")
	public String password;
	
    @NotBlank(message = "제목을 입력해 주세요.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9()\\[\\]{},.\\s]*$", message = "제목에는 특수 문자를 사용할 수 없습니다.")
    @Size(max = 100, message = "제목은 100자 이하이어야 합니다.")
	public String title;
	
    @NotBlank(message = "내용을 입력해 주세요.")
	@Size(max = 1000, message = "내용은 1000자 이하이어야 합니다.")
	public String content;
	
	public Timestamp writeTime;
	public int views;
	public int rowNum;
	public boolean isDeleted;
	
}
