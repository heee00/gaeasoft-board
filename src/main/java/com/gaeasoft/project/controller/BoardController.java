package com.gaeasoft.project.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;
import com.gaeasoft.project.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 전체 글 목록
	@GetMapping("/")
	public String noticeArticleList(Model model) throws Exception {
		List<BoardDTO> boardList = boardService.noticeArticleList();
		model.addAttribute("boardList", boardList);
		
		return "boardList";
	}
	
	// 게시글 상세 보기
	@GetMapping
	public String noticeArticleDetail(@RequestParam("id") Long id, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													@RequestParam(value = "rowNum", required = false) int rowNum,
													HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.noticeArticleDetail(id, session);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		model.addAttribute("rowNum", rowNum);
		
		return "boardDetail";
	}
	
	// 페이징 포함 목록
	@GetMapping("/paging")
	public String noticePagingList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
												Model model) throws Exception  {
		List<BoardDTO> pagingList = boardService.noticePagingList(page);
		PageDTO pageDTO = boardService.pagingParam(page);
		model.addAttribute("pagingList", pagingList);
		model.addAttribute("paging", pageDTO);
		
		return "pagingList";
	}
	
	// 세션에 저장된 아이디 정보 호출
	@ModelAttribute("loginId")
	public String getLoginId(HttpSession session) {
		return (String) session.getAttribute("loginId");
	}
	
	// 게시글 저장 화면 이동
	@GetMapping("/save")
	public String saveNoticeArticleForm(@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
														Model model) throws Exception {
		model.addAttribute("page", page);
		return "saveBoard";
	}
	
	// 게시글 저장
	@PostMapping("/save")
	public String saveNoticeArticle(@Valid @ModelAttribute BoardDTO boardDTO, 
													@ModelAttribute("loginId") String loginId,
													BindingResult result, Model model) throws Exception {
        boardDTO.setWriter(loginId);
        if (result.hasErrors()) {
        	for (FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }
            return "saveBoard";
        }
        
		int saveResult = boardService.saveNoticeArticle(boardDTO);
		if (saveResult > 0) {
			return "redirect:/board/paging";
		} else {
			return "saveBoard";
		}
	}
	
	// 게시글 수정 화면 이동
	@GetMapping("/update")
	public String updateNoticeArticleForm(@RequestParam("id") Long id, 
															@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
															@RequestParam(value = "rowNum", required = false) int rowNum,
															HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.noticeArticleDetail(id, session);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		model.addAttribute("rowNum", rowNum);
		
		return "updateBoard";
	}
	
	// 게시글 수정
	@PostMapping("/update")
	public String updateNoticeArticle(@ModelAttribute BoardDTO boardDTO) throws Exception {
		boardService.updateNoticeArticle(boardDTO);
	    return "boardDetail";
	}
	
	// 게시글 삭제
	@GetMapping("/delete")
	public String deleteNoticeArticle(@RequestParam("id") Long id, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													Model model) throws Exception {
		boardService.deleteNoticeArticle(id);
		model.addAttribute("page", page);
		return "redirect:/board/paging";
	}
	
}
