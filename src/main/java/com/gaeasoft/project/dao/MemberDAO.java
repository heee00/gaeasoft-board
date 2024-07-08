package com.gaeasoft.project.dao;

import java.time.LocalDateTime;

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
	// 회원 탈퇴
	public void deleteMember(String memberId);
	// 회원이 작성한 게시글 삭제
	public void deleteBatchedMemberArticles(LocalDateTime withdrawalDay);
	// 회원 탈퇴 배치
	public void deleteBatchedMember(LocalDateTime withdrawalDay);
	
}
