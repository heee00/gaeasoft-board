package com.gaeasoft.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaeasoft.project.dao.MemberDAOImpl;
import com.gaeasoft.project.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	@Autowired
	private MemberDAOImpl memberDAOImpl;

	// 회원가입
	public int joinMember(MemberDTO memberDTO) {
		return memberDAOImpl.joinMember(memberDTO);
	}

	// 로그인
	public boolean loginMember(MemberDTO memberDTO) {
		MemberDTO loginMember = memberDAOImpl.loginMember(memberDTO);
		
		if (loginMember != null && memberDTO.getPassword().equals(loginMember.getPassword())) {
			return true;
		} else {
			return false;
		}
	}
	
	// 회원 로그인 이메일 조회
	public MemberDTO findByEmail(String email) {
		return memberDAOImpl.findByEmail(email);
	}
	
	// 회원 로그인 아이디 조회
	public MemberDTO findById(String id) {
		return memberDAOImpl.findById(id);
	}

	// 이메일 중복 검사
	public String emailCheck(String email) {
		MemberDTO memberDTO = memberDAOImpl.findByEmail(email);
		
		if (memberDTO == null) {
			return "true";
		} else {
			return "false";
		}
	}

	// 아이디 중복 검사
	public String idCheck(String id) {
		MemberDTO memberDTO = memberDAOImpl.findById(id);
		
		if (memberDTO == null) {
			return "true";
		} else {
			return "false";
		}
	}
	
}
