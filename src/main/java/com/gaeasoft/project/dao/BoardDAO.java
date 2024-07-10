package com.gaeasoft.project.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.FileDTO;

@Mapper
public interface BoardDAO {

	// 전체 글 목록
	public List<BoardDTO> articleList();
	// 게시글 상세 보기
	public BoardDTO articleDetail(Long noticeSeq);
	// 전체 글 갯수 조회
	public int articleCount(String startDate, String endDate, String keyword, String option);
	// 페이징 포함 목록
	public List<BoardDTO> pagingList(Map<String, Object> pagingParams);
	// 게시글 조회수 증가
	public void updateViews(Long noticeSeq);
	// 게시글 저장
	public int saveArticle(BoardDTO boardDTO);
	// 파일 저장
	public int saveFile(FileDTO fileDTO);
	// 파일 목록
	public List<FileDTO> fileList(Long noticeSeq);
	// 원본 파일명 호출
	public String getOriginalFileName(String storedFileName);
	// 게시글 수정
	public void updateArticle(BoardDTO boardDTO);
	// 게시글 삭제
	public void deleteArticle(Long noticeSeq) ;
	// 게시글 삭제 배치
	public void deleteBatchedArticle(LocalDateTime deleteDay);
	// 회원 탈퇴
	public void deleteMemberArticle(String memberId);
	// 회원이 작성한 게시글 삭제
	public void deleteBatchedMemberArticles(LocalDateTime withdrawalDay);
	
}
