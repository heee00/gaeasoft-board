package com.gaeasoft.project.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaeasoft.project.dto.MemberDTO;
import com.gaeasoft.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	@Autowired
	private MemberService memberService;
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	// 회원가입 화면 이동
	@GetMapping("/joinForm")
	public String joinMemberForm() throws Exception {
		return "joinMember";
	}
	
	// 회원가입
	@PostMapping("/join")
	public String joinMember(@ModelAttribute MemberDTO memberDTO) throws Exception {
		int saveResult = memberService.joinMember(memberDTO);
		log.info(memberDTO.toString());
		
		if (saveResult > 0) {
			return "loginMember";
		} else {
			return "joinMember";
		}
	}
	
	// 로그인 화면 이동
	@GetMapping("/loginForm")
	public String loginMemberForm() {
		return "loginMember";
	}
	
	// 로그인
	@PostMapping("/login")
	@ResponseBody
	public Map<String, String> loginMember(@ModelAttribute MemberDTO memberDTO,
											HttpSession session) throws Exception {
		boolean loginResult = memberService.loginMember(memberDTO);
	    Map<String, String> response = new HashMap<>();
		
	    if (loginResult) {
	        session.setAttribute("loginId", memberDTO.getId());
	        session.setAttribute("viewedArticle", new HashSet<Long>());
	        response.put("status", "success");
	        response.put("message", "로그인되었습니다.");
	    } else {
	        response.put("status", "error");
	        response.put("message", "로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
	    }

	    return response;
	}
	
	// 이메일 중복 검사
	@PostMapping("/emailCheck")
	@ResponseBody
	public String emailCheck(@RequestParam("email") String email) {
		String checkResult = memberService.emailCheck(email);
		return checkResult;
	}
	
	// 아이디 중복 검사
	@PostMapping("/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam("id") String id) {
		String checkResult = memberService.idCheck(id);
		return checkResult;
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
	    session.removeAttribute("viewedArticle");
	    session.invalidate();
	    
	    return "redirect:/member/loginForm";
	}
	
}
