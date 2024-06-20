package com.gaeasoft.project.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gaeasoft.project.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sql;

	// 회원가입
	public int joinMember(MemberDTO memberDTO) {
		return sql.insert("Member.joinMember", memberDTO);
	}
	
	// 로그인
	public MemberDTO loginMember(String memberId) {
		return sql.selectOne("Member.loginMember", memberId);
	}

	// 회원 로그인 이메일 조회
	public MemberDTO findByEmail(String loginEmail) {
		return sql.selectOne("Member.findByEmail", loginEmail);
	}

	// 회원 로그인 아이디 조회
	public MemberDTO findById(String loginId) {
		return sql.selectOne("Member.findById", loginId);
	}

	// 회원 수정
	public void updateInfo(MemberDTO memberDTO) {
		sql.update("Member.updateInfo", memberDTO);
	}

}
