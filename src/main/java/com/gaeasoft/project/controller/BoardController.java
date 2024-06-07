package com.gaeasoft.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String noticeArticleList(Model model) {
		List<BoardDTO> boardList = boardService.noticeArticleList();
		model.addAttribute("boardList", boardList);
		
		return "boardList";
	}
	
	// 게시글 상세 보기
	@GetMapping
	public String noticeArticleDetail(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
//		boardService.updateHits(id);
		BoardDTO boardDTO = boardService.noticeArticleDetail(id);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		
		return "boardDetail";
	}
	
	// 페이징 포함 목록
	@GetMapping("/paging")
	public String noticePagingList(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		List<BoardDTO> pagingList = boardService.noticePagingList(page);
		PageDTO pageDTO = boardService.pagingParam(page);
		model.addAttribute("pagingList", pagingList);
		model.addAttribute("paging", pageDTO);
		
		return "pagingList";
	}
	
	// 게시글 저장 화면 이동
	@GetMapping("/save")
	public String saveNoticeArticleForm(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		model.addAttribute("page", page);
		
		return "saveBoard";
	}
	
	// 게시글 저장
	@PostMapping("/save")
	public String saveNoticeArticle(@ModelAttribute BoardDTO boardDTO, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		int saveResult = boardService.saveNoticeArticle(boardDTO);
		model.addAttribute("page", page);

		if (saveResult > 0) {
			return "redirect:/board/paging";
		} else {
			return "saveBoard";
		}
	}
	
	// 게시글 수정 화면 이동
	@GetMapping("/update")
	public String updateNoticeArticleForm(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		BoardDTO boardDTO = boardService.noticeArticleDetail(id);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		
		return "updateBoard";
	}
	
	// 게시글 수정
	@PostMapping("/update")
	public String updateNoticeArticle(@ModelAttribute BoardDTO boardDTO, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		boardService.updateNoticeArticle(boardDTO);
		BoardDTO dto = boardService.noticeArticleDetail(boardDTO.getId());
		model.addAttribute("board", dto);
		model.addAttribute("page", page);
		
		return "boardDetail";
	}
	
	// 게시글 삭제
	@GetMapping("/delete")
	public String deleteNoticeArticle(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		boardService.deleteNoticeArticle(id);
		model.addAttribute("page", page);
		
		return "redirect:/board/paging";
	}
	
}
