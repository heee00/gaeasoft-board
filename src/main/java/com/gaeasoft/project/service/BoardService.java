package com.gaeasoft.project.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;
import com.gaeasoft.project.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	public List<BoardDTO> findAll() {
		return boardRepository.findAll();
	}
	
	int pageLimit = 10;		// 한 페이지당 보여줄 글 갯수
	int blockLimit = 5;		// 하단에 보여줄 페이지 번호 갯수
	
	public List<BoardDTO> pagingList(int page) {
		int pageStart = (page - 1) * pageLimit;
		Map<String, Integer> pagingParams = new HashMap<>();
		pagingParams.put("start", pageStart);
		pagingParams.put("limit", pageLimit);
		List<BoardDTO> pagingList = boardRepository.pagingList(pagingParams);
		
		return pagingList;
	}

	public PageDTO pagingParam(int page) {
        int boardCount = boardRepository.boardCount();														// 전체 글 갯수 조회
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

	public BoardDTO findById(Long id) {
		return boardRepository.findById(id);
	}

	public void updateHits(Long id) {
		boardRepository.updateHits(id);
	}

	public int save(BoardDTO boardDTO) {
		return boardRepository.save(boardDTO);
	}
	
	public void delete(Long id) {
		boardRepository.delete(id);
	}

	public void update(BoardDTO boardDTO) {
		boardRepository.update(boardDTO);
	}

}
