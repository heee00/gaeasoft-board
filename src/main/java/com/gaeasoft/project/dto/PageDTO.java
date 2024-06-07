package com.gaeasoft.project.dto;

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
public class PageDTO {
	
	public int page;			// 현재 페이지
	public int maxPage;		// 전체 필요한 페이지 갯수
	public int startPage;	// 현재 페이지 기준 시작 페이지 값
	public int endPage;		// 현재 페이지 기준 마지막 페이지 값
	
}
