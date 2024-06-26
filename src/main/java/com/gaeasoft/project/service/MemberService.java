package com.gaeasoft.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.gaeasoft.project.dao.BoardDAOImpl;
import com.gaeasoft.project.dao.MemberDAOImpl;
import com.gaeasoft.project.dto.MemberDTO;
import com.gaeasoft.project.util.EncodePassword;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	@Autowired
	private MemberDAOImpl memberDAOImpl;
	@Autowired
	private BoardDAOImpl boardDAOImpl;
	@Autowired
	private Validator validator;

	// 회원가입
    @Transactional
	public int joinMember(MemberDTO memberDTO) {
	    String encodePassword = EncodePassword.encrypt(memberDTO.getPassword().trim());
        memberDTO.setPassword(encodePassword);
        
        String address = memberDTO.getAddress();
        String detailAddress = memberDTO.getDetailAddress();
        memberDTO.setAddress(address + " " + detailAddress);
        
		return memberDAOImpl.joinMember(memberDTO);
	}

	// 로그인
	public boolean loginMember(MemberDTO memberDTO) {
		String password = memberDTO.getPassword().trim();
	    String encodePassword = EncodePassword.encrypt(password);
	    
		MemberDTO loginMember = memberDAOImpl.loginMember(memberDTO.getMemberId());
		
		if (loginMember != null) {
			String loginPassword = loginMember.getPassword().trim();
			
			if (encodePassword.equals(loginPassword)) {
	            return true;
	        }
	    } 
	    return false;
	}
	
	// 회원 로그인 이메일 조회
	public MemberDTO findByEmail(String email) {
		return memberDAOImpl.findByEmail(email);
	}
	
	// 회원 로그인 아이디 조회
	public MemberDTO findById(String memberId) {
		return memberDAOImpl.findById(memberId);
	}

	// 유효성 검사
	public Map<String, List<String>> validateField(MemberDTO memberDTO, String fieldName, String fieldValue) {
        switch (fieldName) {
	        case "name":
	        	memberDTO.setName(fieldValue);
                break;
            case "memberId":
            	memberDTO.setMemberId(fieldValue);
                break;
            case "password":
            	memberDTO.setPassword(fieldValue);
            	break;
            case "email":
            	memberDTO.setEmail(fieldValue);
                break;
            case "address":
            	memberDTO.setAddress(fieldValue);
                break;
            case "detailAddress":
            	memberDTO.setDetailAddress(fieldValue);
                break;
        }

        BindingResult result = new BeanPropertyBindingResult(memberDTO, "memberDTO");
        validator.validate(memberDTO, result);
        
        Map<String, List<String>> errors = getFieldErrors(result);
        
        // 이메일, 아이디 중복 검사
        if ("email".equals(fieldName)) {
            String email = fieldValue;
            boolean emailExists = memberDAOImpl.findByEmail(email) != null;
            if (emailExists) {
                errors.put("email", Collections.singletonList("이미 사용 중인 이메일입니다."));
            }
        } else if ("memberId".equals(fieldName)) {
            String memberId = fieldValue;
            boolean idExists = memberDAOImpl.findById(memberId) != null;
            if (idExists) {
                errors.put("memberId", Collections.singletonList("이미 사용 중인 아이디입니다."));
            }
        }
        return errors;
    }
	
	// 유효성 검사 메세지 호출
    public Map<String, List<String>> getFieldErrors(BindingResult result) {
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage());
        }
        return errors;
    }
	
    // 회원 수정
    @Transactional
 	public void updatePersonalInfo(MemberDTO memberDTO) {
		String password = memberDTO.getPassword().trim();

        if (memberDTO.getPassword() != null && !password.isEmpty()) {
        	String encodePassword = EncodePassword.encrypt(password);
		    memberDTO.setPassword(encodePassword);
        }
        
        String address = memberDTO.getAddress();
        String detailAddress = memberDTO.getDetailAddress();
        memberDTO.setAddress(address + " " + detailAddress);
        
        memberDAOImpl.updateInfo(memberDTO);
 	}
    
    // 회원 탈퇴
    @Transactional
 	public void deleteMember(String memberId) {
 		memberDAOImpl.deleteMember(memberId);
 		boardDAOImpl.deleteMemberArticle(memberId);
 	}
 	
}
