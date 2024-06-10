package com.gaeasoft.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaeasoft.project.dao.BoardDAOImpl;
import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAOImpl boardDAOImpl;

	// 전체 글 목록
	public List<BoardDTO> noticeArticleList() {
		return boardDAOImpl.articleList();
	}
	
	// 게시글 상세 보기
	public BoardDTO noticeArticleDetail(Long id) {
//		updateHits(id);
		return boardDAOImpl.articleDetail(id);
	}
	
	int pageLimit = 10;		// 한 페이지당 보여줄 글 갯수
	int blockLimit = 5;		// 하단에 보여줄 페이지 번호 갯수
	
	// 페이징 변수 설정
	public PageDTO pagingParam(int page) {
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

}
