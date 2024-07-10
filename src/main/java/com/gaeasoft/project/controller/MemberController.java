package com.gaeasoft.project.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaeasoft.project.dto.MemberDTO;
import com.gaeasoft.project.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Validated
public class MemberController {
	
	@Autowired
	private MemberService memberService;
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	// 회원가입 화면 이동
	@GetMapping("/joinForm")
	public String joinMemberForm() throws Exception {
		return "joinMember";
	}
	
	// 회원가입
	@PostMapping("/join")
	public String joinMember(@Valid @ModelAttribute MemberDTO memberDTO,
											BindingResult result) throws Exception {
		String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
		log.info("회원가입: " + methodName + "-" + memberDTO.toString());
		
		if (result.hasErrors()) {
			memberService.getFieldErrors(result);
            return "joinMember";
        }
		
		int saveResult = memberService.joinMember(memberDTO);
		
		if (saveResult > 0) {
			return "redirect:/member/loginForm";
		} else {
			return "joinMember";
		}
	}
	
	// 유효성 검사
	@PostMapping("/validateField")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> validateField(@RequestBody Map<String, String> requestParams) throws Exception {
        String fieldName = requestParams.get("fieldName");
        String fieldValue = requestParams.get("fieldValue");
        MemberDTO memberDTO = new MemberDTO();
        
        Map<String, List<String>> errors = memberService.validateField(memberDTO, fieldName, fieldValue);
        return ResponseEntity.ok(errors);
    }
	
	// 로그인 화면 이동
	@GetMapping("/loginForm")
	public String loginMemberForm() throws Exception {
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
	        session.setAttribute("loginId", memberDTO.getMemberId());
	        session.setAttribute("viewedArticle", new HashSet<Long>());
	        response.put("status", "success");
	        response.put("message", "로그인되었습니다.");
	    } else {
	        response.put("status", "error");
	        response.put("message", "로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
	    }

	    return response;
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) throws Exception {
	    session.removeAttribute("viewedArticle");
	    session.invalidate();
	    
	    return "redirect:/member/loginForm";
	}
	
	// 회원 정보 보기
	@GetMapping("/viewPersonalInfo")
	public String viewPersonalInfo(@RequestParam("memberId") String memberId,
															HttpSession session, Model model) throws Exception {
		
		memberId = (String) session.getAttribute("loginId");
		MemberDTO memberDTO = memberService.findById(memberId);
		model.addAttribute("member", memberDTO);
		
		return "memberDetail";
	}
	
	// 회원 정보 수정 화면 이동
	@GetMapping("/updatePersonalInfoForm")
	public String updatePersonalInfoForm(@RequestParam("memberId") String memberId,
															HttpSession session, Model model) throws Exception {
		memberId = (String) session.getAttribute("loginId");
		MemberDTO memberDTO = memberService.findById(memberId);
		model.addAttribute("member", memberDTO);
		
		return "updateMember";
	}
	
	// 회원 정보 수정
	@PostMapping("/updatePersonalInfo")
	public String updatePersonalInfo(@Valid @ModelAttribute MemberDTO memberDTO,
													HttpSession session, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			memberService.getFieldErrors(result);
			return "updateMember";
		} else {
			String loginId = (String) session.getAttribute("loginId");
			memberDTO.setMemberId(loginId);
			memberService.updatePersonalInfo(memberDTO);
			return "memberDetail";
		}
	}
	
	 /*
     *  회원 탈퇴
     *  바로 삭제가 아닌 삭제 여부 플래그 상태 변경 ( 0 -> 1)
     */
	@GetMapping("/deleteMember")
	public String deleteMember(String memberId) throws Exception {
		memberService.deleteMember(memberId);
	    return "redirect:/member/loginForm";
	}
	
}
