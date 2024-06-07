package com.gaeasoft.project.dto;

import java.sql.Timestamp;

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
	public String password;
	public String title;
	public String content;
	public Timestamp writeTime;
	public int views;
	public int rowNum;
	
}
