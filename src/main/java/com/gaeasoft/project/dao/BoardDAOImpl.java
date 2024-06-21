package com.gaeasoft.project.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gaeasoft.project.dto.BoardDTO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	// 전체 글 목록
	public List<BoardDTO> articleList() {
		return sql.selectList("Board.articleList");
	}
	
	// 게시글 상세 보기
	public BoardDTO articleDetail(Long noticeSeq) {
		return sql.selectOne("Board.articleDetail", noticeSeq);
	}
	
	// 전체 글 갯수 조회
	public int articleCount(String keyword, String option) {
		Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("option", option);
		return sql.selectOne("Board.articleCount", params);
	}
	
	// 페이징 포함 목록
	public List<BoardDTO> pagingList(Map<String, Object> pagingParams) {
		return sql.selectList("Board.pagingList", pagingParams);
	}

	// 검색 포함 목록
	public List<BoardDTO> searchList(Map<String, Object> pagingParams) {
		return sql.selectList("Board.searchList", pagingParams);
	}
	
	// 게시글 조회수 증가
	public void updateViews(Long noticeSeq) {
		sql.update("Board.updateViews", noticeSeq);
	}
	
	// 게시글 저장
	public int saveArticle(BoardDTO boardDTO) {
		return sql.insert("Board.saveArticle", boardDTO);
	}
	
	// 게시글 수정
	public void updateArticle(BoardDTO boardDTO) {
		sql.update("Board.updateArticle", boardDTO);
	}

	// 게시글 삭제
	public void deleteArticle(Long noticeSeq) {
		sql.delete("Board.deleteArticle", noticeSeq);
	}

	// 게시글 삭제 배치
    @Override
    @Transactional
	public void deleteBatchedArticle(LocalDateTime deleteDay) {
		sql.delete("Board.deleteBatchedArticle", deleteDay);
	}

}
