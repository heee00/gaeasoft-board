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
public class FileDTO {

	public Long fileSeq;
	public String originFileName;
	public String storedFileName;
	public Timestamp uploadTime;
	public boolean isDeleted;
	public Long noticeSeq;

}
