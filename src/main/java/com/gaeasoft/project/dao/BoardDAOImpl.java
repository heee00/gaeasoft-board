package com.gaeasoft.project.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.FileDTO;

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
	public int articleCount(String startDate, String endDate, String searchKeyword, String searchOption) {
		Map<String, Object> params = new HashMap<>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
	    params.put("searchKeyword", searchKeyword);
	    params.put("searchOption", searchOption);
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
	
	// 파일 저장
	public int saveFile(FileDTO fileDTO) {
		return sql.insert("Board.saveFile", fileDTO);
	}
	
	// 파일 목록
	public List<FileDTO> fileList(Long noticeSeq) {
		return sql.selectList("Board.fileList", noticeSeq);
	}
	
	// 원본 파일명 호출
	public String getOriginalFileName(String storedFileName) {
	    return sql.selectOne("Board.getOriginalFileName", storedFileName);
	}
	
	// 게시글 수정
	public void updateArticle(BoardDTO boardDTO) {
		sql.update("Board.updateArticle", boardDTO);
	}

	// 게시글 삭제
	public void deleteArticle(Long noticeSeq) {
		sql.update("Board.deleteArticle", noticeSeq);
	}

	// 게시글 삭제 배치
	public void deleteBatchedArticle(LocalDateTime deleteDay) {
		sql.update("Board.deleteBatchedArticle", deleteDay);
	}

    // 회원 탈퇴
	public void deleteMemberArticle(String memberId) {
		sql.update("Board.deleteMemberArticle", memberId);
	}

	// 회원이 작성한 게시글 삭제
	public void deleteBatchedMemberArticles(LocalDateTime withdrawalDay) {
		sql.delete("Board.deleteBatchedMemberArticles", withdrawalDay);
	}

}
