package com.gaeasoft.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import com.gaeasoft.project.dao.BoardDAOImpl;
import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAOImpl boardDAOImpl;
	@Autowired
	private Validator validator;

	// 전체 글 목록
	public List<BoardDTO> noticeArticleList() {
		return boardDAOImpl.articleList();
	}
	
	// 게시글 상세 보기
	public BoardDTO viewNoticeArticleDetail(Long id, HttpSession session) {
		Set<Long> viewedArticle = (Set<Long>) session.getAttribute("viewedArticle");
	    
	    if (viewedArticle == null) {
	    	viewedArticle = new HashSet<>();
	    	session.setAttribute("viewedArticle", viewedArticle);
	    }
	    
	    try {
	    	boolean viewed = false;
	    	for (Long viewedId : viewedArticle) {
	    		if (viewedId.equals(id)) {
	    			viewed = true;
	    			break;
	    		}
	    	}
	    		
	    	if (!viewed) {
	    		// 예외 발생을 유도하기 위해 임의로 에러를 발생시킴
	    		// throw new RuntimeException("조회수 업데이트 중 에러 발생");
	    		updateViews(id);
	    		viewedArticle.add(id);
	    	}
	        return boardDAOImpl.articleDetail(id);
	        
	    } catch (Exception e) {
	        System.err.println("에러 발생: " + e.getMessage());
	        return boardDAOImpl.articleDetail(id);
	    }
	}
	
	int pageLimit = 10;		// 한 페이지당 보여줄 글 갯수
	int blockLimit = 5;		// 하단에 보여줄 페이지 번호 갯수
	
	// 페이징 변수 설정
	public PageDTO setPagingParam(int page) {
		int boardCount = boardDAOImpl.articleCount();															// 전체 글 갯수 조회
		int maxPage = (int) (Math.ceil((double) boardCount / pageLimit));							// 전체 페이지 갯수 계산
		int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;	// 시작 페이지 값 계산
		int endPage = startPage + blockLimit - 1;																  	// 끝 페이지 값 계산
		
		if (endPage > maxPage) {
			endPage = maxPage;
		}
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.setPage(page);
		pageDTO.setMaxPage(maxPage);
		pageDTO.setStartPage(startPage);
		pageDTO.setEndPage(endPage);
		
		return pageDTO;
	}
	
	// 페이징 포함 목록
	public List<BoardDTO> noticePagingList(int page) {
		int pageStart = (page - 1) * pageLimit;
		Map<String, Integer> pagingParams = new HashMap<>();
		pagingParams.put("start", pageStart);
		pagingParams.put("limit", pageLimit);
		List<BoardDTO> pagingList = boardDAOImpl.pagingList(pagingParams);
		
		return pagingList;
	}
	
	// 유효성 검사
	public Map<String, List<String>> validateField(BoardDTO boardDTO, String fieldName, String fieldValue) {
        switch (fieldName) {
            case "password":
                boardDTO.setPassword(fieldValue);
                break;
            case "title":
                boardDTO.setTitle(fieldValue);
                break;
            case "content":
                boardDTO.setContent(fieldValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid field name");
        }

        BindingResult result = new BeanPropertyBindingResult(boardDTO, "boardDTO");
        validator.validate(boardDTO, result);

        if (result.hasErrors()) {
            return getFieldErrors(result);
        } else {
            return Collections.emptyMap();
        }
    }

	// 유효성 검사 메세지 호출
    public Map<String, List<String>> getFieldErrors(BindingResult result) {
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage());
        }
        return errors;
    }

	// 게시글 조회수 증가
	public void updateViews(Long id) {
		boardDAOImpl.updateViews(id);
	}
	
	// 게시글 저장
	public int saveNoticeArticle(BoardDTO boardDTO) {
		return boardDAOImpl.saveArticle(boardDTO);
	}
	
	// 게시글 수정
	public void updateNoticeArticle(BoardDTO boardDTO) {
		boardDAOImpl.updateArticle(boardDTO);
	}
	
	// 게시글 삭제
	public void deleteNoticeArticle(Long id) {
		boardDAOImpl.deleteArticle(id);
	}

	// 게시글 삭제 배치
	public void deleteBatchedNoticeArticle(LocalDateTime deleteDay) {
		boardDAOImpl.deleteBatchedArticle(deleteDay);
	}

}
