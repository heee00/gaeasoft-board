package com.gaeasoft.project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;
import com.gaeasoft.project.service.BoardService;

@Controller
@RequestMapping("/board")
@Validated
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 전체 글 목록
	@GetMapping("/List")
	public String noticeArticleList(Model model) throws Exception {
		List<BoardDTO> boardList = boardService.noticeArticleList();
		model.addAttribute("boardList", boardList);
		
		return "boardList";
	}
	
	// 게시글 상세 보기
	@GetMapping("/viewDetail")
	public String viewNoticeArticleDetail(@RequestParam("id") Long id, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													@RequestParam(value = "rowNum", required = false) int rowNum,
													HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.viewNoticeArticleDetail(id, session);
		session.setAttribute("boardPassword", boardDTO.getPassword());
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		model.addAttribute("rowNum", rowNum);
		
		return "boardDetail";
	}
	
	// 페이징 포함 목록
	@GetMapping("/pagingList")
	public String noticePagingList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
												Model model) throws Exception  {
		List<BoardDTO> pagingList = boardService.noticePagingList(page);
		PageDTO pageDTO = boardService.setPagingParam(page);
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
	@GetMapping("/saveArticleForm")
	public String saveNoticeArticleForm(@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
														Model model) throws Exception {
		model.addAttribute("page", page);
		return "saveBoard";
	}
	
	// 게시글 저장
	@PostMapping("/saveArticle")
    public String saveNoticeArticle(@Valid @ModelAttribute BoardDTO boardDTO,
                                               @ModelAttribute("loginId") String loginId,
                                               BindingResult result) throws Exception {
        boardDTO.setWriter(loginId);

        if (result.hasErrors()) {
            boardService.getFieldErrors(result);
            return "saveBoard";
        }

        int saveResult = boardService.saveNoticeArticle(boardDTO);
		if (saveResult > 0) {
			return "redirect:/board/pagingList";
		} else {
			return "saveBoard";
		}
    }
	
	// 유효성 검사
	@PostMapping("/validateField")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> validateField(@RequestBody Map<String, String> requestParams) {
        String fieldName = requestParams.get("fieldName");
        String fieldValue = requestParams.get("fieldValue");
        BoardDTO boardDTO = new BoardDTO();
        
        Map<String, List<String>> errors = boardService.validateField(boardDTO, fieldName, fieldValue);
        return ResponseEntity.ok(errors);
    }

	// 게시글 수정 화면 이동
	@GetMapping("/updateArticleForm")
	public String updateNoticeArticleForm(@RequestParam("id") Long id, 
															@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
															@RequestParam(value = "rowNum", required = false) int rowNum,
															HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.viewNoticeArticleDetail(id, session);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		model.addAttribute("rowNum", rowNum);
		
		return "updateBoard";
	}
	
	// 게시글 수정
	@PostMapping("/updateArticle")
	public String updateNoticeArticle(@Valid @ModelAttribute BoardDTO boardDTO,
													BindingResult result) throws Exception {
		
		if (result.hasErrors()) {
            boardService.getFieldErrors(result);
            return "updateBoard";
        } else {
			boardService.updateNoticeArticle(boardDTO);
		    return "boardDetail";
        }
	}
	
	// 게시글 삭제
	@GetMapping("/deleteArticle")
	public String deleteNoticeArticle(@RequestParam("id") Long id, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													Model model) throws Exception {
		boardService.deleteNoticeArticle(id);
		model.addAttribute("page", page);
		return "redirect:/board/pagingList";
	}
	
}
