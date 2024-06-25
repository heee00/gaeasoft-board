package com.gaeasoft.project.service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.gaeasoft.project.dao.BoardDAOImpl;
import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.FileDTO;
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
	
   // 게시글 조회 확인
    private boolean viewedArticleCheck(Long noticeSeq, Set<Long> viewedArticle) {
    	// 내가 봤던 게시글 List에 해당 seq가 일치한지 비교
        for (Long viewedNoticeSeq : viewedArticle) {
            if (viewedNoticeSeq.equals(noticeSeq)) {
                return true;
            }
        }
        return false;
    }

	// 게시글 상세 보기
    @Transactional
	public BoardDTO viewNoticeArticleDetail(Long noticeSeq, HttpSession session) {
		Set<Long> viewedArticle = (Set<Long>) session.getAttribute("viewedArticle");

		try {
	        if (!viewedArticleCheck(noticeSeq, viewedArticle)) {
		    	session.setAttribute("viewedArticle", viewedArticle);
	        	updateViews(noticeSeq);
	            viewedArticle.add(noticeSeq);
	        }
	            
	        BoardDTO boardDTO = boardDAOImpl.articleDetail(noticeSeq);
	        List<FileDTO> fileList = boardDAOImpl.fileList(noticeSeq);
	        boardDTO.setFileList(fileList);

	        return boardDTO;
	    
		} catch (Exception e) {
			e.printStackTrace();
	        return boardDAOImpl.articleDetail(noticeSeq);
	    }
	}
	
	int pageLimit = 10;		// 한 페이지당 보여줄 글 갯수
	int blockLimit = 5;		// 하단에 보여줄 페이지 번호 갯수
	
	// 페이징 변수 설정
	public PageDTO setPagingParam(int page, String startDate, String endDate, String searchKeyword, String searchOption) {
        int boardCount = boardDAOImpl.articleCount(startDate, endDate, searchKeyword, searchOption);
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
		pageDTO.setSearchKeyword(searchKeyword);
        pageDTO.setSearchOption(searchOption);
		
		return pageDTO;
	}
	
	// 페이징 포함 목록
    @Transactional(readOnly = true)
	public List<BoardDTO> noticePagingList(int page, String startDate, String endDate, String searchKeyword, String searchOption) {
		int pageStart = (page - 1) * pageLimit;
		Map<String, Object> pagingParams = new HashMap<>();
		pagingParams.put("start", pageStart);
		pagingParams.put("limit", pageLimit);
		pagingParams.put("startDate", startDate);
		pagingParams.put("endDate", endDate);
		pagingParams.put("searchKeyword", searchKeyword);
        pagingParams.put("searchOption", searchOption);
        pagingParams.put("isDeleted", false);
		List<BoardDTO> pagingList = boardDAOImpl.pagingList(pagingParams);

	    return pagingList;
	}
    
    // 게시글 갯수 세기
    public int articleCount(String startDate, String endDate, String searchKeyword, String searchOption) {
    	return boardDAOImpl.articleCount(startDate, endDate, searchKeyword, searchOption);
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
    @Transactional
	public void updateViews(Long noticeSeq) {
		boardDAOImpl.updateViews(noticeSeq);
	}
	
	// 게시글 저장
    @Transactional
	public int saveNoticeArticle(BoardDTO boardDTO, List<MultipartFile> files) {
    	int saveResult = boardDAOImpl.saveArticle(boardDTO);

    	String savePath = "/WEB-INF/files/";
		File uploadDir = new File(savePath);
	    if (!uploadDir.exists()) {
	    	uploadDir.mkdirs();
	    }
	     
	    Long noticeSeq = boardDTO.getNoticeSeq();

	    if (files != null && !files.isEmpty()) {
	        for (MultipartFile multipartFile : files) {
	            if (!multipartFile.isEmpty()) {
	                try {
	                    // 파일 저장
	                    String storedFileName = saveFile(multipartFile, savePath);

	                    // DB에 저장
	                    FileDTO fileDTO = new FileDTO();
	                    fileDTO.setNoticeSeq(noticeSeq);
	                    fileDTO.setStoredFileName(storedFileName);
	                    fileDTO.setOriginFileName(multipartFile.getOriginalFilename());
	                    boardDAOImpl.saveFile(fileDTO);

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
         return saveResult;
	}
    
    // 파일 저장 이름 설정
    public String saveFile(MultipartFile multipartFile, String savePath) throws Exception {
    	// 원본 파일명에서 확장자 추출
        String originalFileName = multipartFile.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // 난수 파일명 생성
        String fileName = UUID.randomUUID().toString();
        String storedFileName = fileName + fileExtension;

        // 파일 저장
        File file = new File(savePath + File.separator + storedFileName);
        multipartFile.transferTo(file);

        return storedFileName;
    }
    
    // 파일 목록
	public List<FileDTO> fileList(Long noticeSeq) {
		return boardDAOImpl.fileList(noticeSeq);
	}
	
	// 원본 파일명 호출
	public String getOriginalFileName(String storedFileName) {
		return boardDAOImpl.getOriginalFileName(storedFileName);
	}
    
	// 게시글 수정
    @Transactional
	public void updateNoticeArticle(BoardDTO boardDTO) {
		boardDAOImpl.updateArticle(boardDTO);
	}
	
	// 게시글 삭제
    @Transactional
	public void deleteNoticeArticle(Long noticeSeq) {
		boardDAOImpl.deleteArticle(noticeSeq);
	}
	
	// 게시글 삭제 배치
	public void deleteBatchedNoticeArticle(LocalDateTime deleteDay) {
		boardDAOImpl.deleteBatchedArticle(deleteDay);
	}

}
