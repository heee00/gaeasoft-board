package com.gaeasoft.project.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.gaeasoft.project.dao.MemberDAOImpl;
import com.gaeasoft.project.dto.MemberDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	@Autowired
	private MemberDAOImpl memberDAOImpl;
	@Autowired
	private Validator validator;

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

	// 유효성 검사
	public Map<String, List<String>> validateField(MemberDTO memberDTO, String fieldName, String fieldValue) {
        switch (fieldName) {
	        case "name":
	        	memberDTO.setName(fieldValue);
                break;
            case "id":
            	memberDTO.setId(fieldValue);
                break;
            case "password":
            	memberDTO.setPassword(fieldValue);
            	break;
            case "email":
            	memberDTO.setEmail(fieldValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name");
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
        } else if ("id".equals(fieldName)) {
            String id = fieldValue;
            boolean idExists = memberDAOImpl.findById(id) != null;
            if (idExists) {
                errors.put("id", Collections.singletonList("이미 사용 중인 아이디입니다."));
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
 	public void updatePersonalInfo(MemberDTO memberDTO) {
 		memberDAOImpl.updateInfo(memberDTO);
 	}
}
