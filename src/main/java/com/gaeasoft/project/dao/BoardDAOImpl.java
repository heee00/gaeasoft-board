package com.gaeasoft.project.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	public BoardDTO articleDetail(Long id) {
		return sql.selectOne("Board.articleDetail", id);
	}
	
	// 전체 글 갯수 조회
	public int articleCount() {
		return sql.selectOne("Board.articleCount");
	}
	
	// 페이징 포함 목록
	public List<BoardDTO> pagingList(Map<String, Integer> pagingParams) {
		return sql.selectList("Board.pagingList", pagingParams);
	}

	// 게시글 조회수 증가
	public void updateViews(Long id) {
		sql.update("Board.updateViews", id);
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
	public void deleteArticle(Long id) {
		sql.delete("Board.deleteArticle", id);
	}

}
