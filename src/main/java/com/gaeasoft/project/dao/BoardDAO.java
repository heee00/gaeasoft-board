package com.gaeasoft.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gaeasoft.project.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	public List<BoardDTO> findAll();
	public List<BoardDTO> pagingList(Map<String, Integer> pagingParams);
	public int boardCount();
	public BoardDTO findById(Long id);
	public void updateHits(Long id);
	public int save(BoardDTO boardDTO);
	public void delete(Long id) ;
	public void update(BoardDTO boardDTO);
	
}
