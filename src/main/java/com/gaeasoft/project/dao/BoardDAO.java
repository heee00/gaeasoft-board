package com.gaeasoft.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gaeasoft.project.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	// 전체 글 목록
	public List<BoardDTO> articleList();
	// 게시글 상세 보기
	public BoardDTO articleDetail(Long id);
	// 전체 글 갯수 조회
	public int articleCount();
	// 페이징 포함 목록
	public List<BoardDTO> pagingList(Map<String, Integer> pagingParams);
	// 게시글 조회수 증가
	public void updateViews(Long id);
	// 게시글 저장
	public int saveArticle(BoardDTO boardDTO);
	// 게시글 수정
	public void updateArticle(BoardDTO boardDTO);
	// 게시글 삭제
	public void deleteArticle(Long id) ;
	
}
