package com.gaeasoft.project.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDTO {
	
	private Long id;
	private String boardWriter;
	private String boardPassword;
	private String boardTitle;
	private String boardContent;
	private int boardHits;
	private Timestamp boardWriteTime;
	private int boardRowNum;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public String getBoardPassword() {
		return boardPassword;
	}
	public void setBoardPassword(String boardPassword) {
		this.boardPassword = boardPassword;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getBoardHits() {
		return boardHits;
	}
	public void setBoardHits(int boardHits) {
		this.boardHits = boardHits;
	}
	public Timestamp getBoardWriteTime() {
		return boardWriteTime;
	}
	public void setBoardWriteTime(Timestamp boardWriteTime) {
		this.boardWriteTime = boardWriteTime;
	}
	public int getBoardRowNum() {
		return boardRowNum;
	}
	public void setBoardRowNum(int boardRowNum) {
		this.boardRowNum = boardRowNum;
	}
	
}
