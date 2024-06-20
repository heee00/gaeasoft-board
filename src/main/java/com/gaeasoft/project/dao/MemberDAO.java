package com.gaeasoft.project.dao;

import org.apache.ibatis.annotations.Mapper;

import com.gaeasoft.project.dto.MemberDTO;

@Mapper
public interface MemberDAO {
	
	// 회원가입
	public int joinMember(MemberDTO memberDTO);
	// 로그인
	public MemberDTO loginMember(String memberId);
	// 회원 로그인 이메일 조회
	public MemberDTO findByEmail(String loginEmail);
	// 회원 로그인 아이디 조회
	public MemberDTO findById(String loginId);
	// 회원 수정
	public void updateInfo(MemberDTO memberDTO);

}
