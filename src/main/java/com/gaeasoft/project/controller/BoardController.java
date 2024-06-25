package com.gaeasoft.project.controller;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

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
	public String viewNoticeArticleDetail(@RequestParam("noticeSeq") Long noticeSeq, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													@RequestParam(value = "rowNum", required = false) int rowNum,
													HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.viewNoticeArticleDetail(noticeSeq, session);
		session.setAttribute("boardPassword", boardDTO.getPassword());
		model.addAttribute("board", boardDTO);
		model.addAttribute("fileList", boardDTO.getFileList());
		model.addAttribute("page", page);
		model.addAttribute("rowNum", rowNum);
		
		return "boardDetail";
	}
	
	// 파일 다운로드
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam("storedFileName") String storedFileName) {
	    // 저장된 파일 경로
	    String filePath = "/WEB-INF/files/" + storedFileName;
	    File file = new File(filePath);

	    if (!file.exists()) {
	        throw new RuntimeException("File not found");
	    }

	    try {
	        Path path = Paths.get(filePath);
	        Resource resource = new UrlResource(path.toUri());
	        String originalFileName = boardService.getOriginalFileName(storedFileName);

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\"")
	                .body(resource);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("File download error");
	    }
	}
	
	// 페이징 포함 목록
	@GetMapping("/pagingList")
	public String noticePagingList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
												@RequestParam(value = "startDate", required = false) 	@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
									            @RequestParam(value = "endDate", required = false) 	@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
												 @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
									             @RequestParam(value = "searchOption", required = false) String searchOption,
												Model model) throws Exception  {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String start = (startDate != null) ? startDate.format(formatter) : null;
	    String end = (endDate != null) ? endDate.format(formatter) : null;
	
	    List<BoardDTO> pagingList = boardService.noticePagingList(page, start, end, searchKeyword, searchOption);
	    PageDTO pageDTO = boardService.setPagingParam(page, start, end, searchKeyword, searchOption);
	    int boardCount = boardService.articleCount(start, end, searchKeyword, searchOption);
	    
	    if (boardCount > 0) {
	        if (boardCount <= page * 10) {
	            pageDTO.setEndPage(page);
	        } else {
	            pageDTO.setEndPage(pageDTO.getMaxPage());
	        }
	    } else {
	        pageDTO.setEndPage(1);
	    }

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
            									@RequestParam(value = "files", required = false) List<MultipartFile> files,
                                               @ModelAttribute("loginId") String loginId,
                                               BindingResult result) throws Exception {
        boardDTO.setMemberId(loginId);

        if (result.hasErrors()) {
            boardService.getFieldErrors(result);
            return "saveBoard";
        }

        int saveResult = boardService.saveNoticeArticle(boardDTO, files);
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
	public String updateNoticeArticleForm(@RequestParam("noticeSeq") Long noticeSeq, 
															@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
															@RequestParam(value = "rowNum", required = false) int rowNum,
															HttpSession session, Model model) throws Exception {
		BoardDTO boardDTO = boardService.viewNoticeArticleDetail(noticeSeq, session);
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
	public String deleteNoticeArticle(@RequestParam("noticeSeq") Long noticeSeq, 
													@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
													Model model) throws Exception {
		boardService.deleteNoticeArticle(noticeSeq);
		model.addAttribute("page", page);
		return "redirect:/board/pagingList";
	}
	
}
