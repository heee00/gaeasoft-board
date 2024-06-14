package com.gaeasoft.project.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	public Long seq;
	public String name;
	public String id;
	public String password;
	public String email;
	public Timestamp joinTime;
	
	@Override
	public String toString() {
		return "MemberDTO [name=" + name + ", id=" + id + ", password=" + password + ", email=" + email + "]";
	}
	
}
