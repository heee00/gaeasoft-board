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
public class MemberDTO {
	
	public Long seq;
	public String name;
	public String id;
	public String password;
	public String email;
	public Timestamp joinTime;

}
