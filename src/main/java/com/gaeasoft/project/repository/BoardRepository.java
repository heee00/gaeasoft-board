package com.gaeasoft.project.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gaeasoft.project.dao.BoardDAO;
import com.gaeasoft.project.dto.BoardDTO;

@Repository
public class BoardRepository implements BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sql;

	public List<BoardDTO> findAll() {
		return sql.selectList("Board.findAll");
	}
	
	public List<BoardDTO> pagingList(Map<String, Integer> pagingParams) {
		return sql.selectList("Board.pagingList", pagingParams);
	}

	public int boardCount() {
		return sql.selectOne("Board.boardCount");
	}

	public BoardDTO findById(Long id) {
		return sql.selectOne("Board.findById", id);
	}

	public void updateHits(Long id) {
		sql.update("Board.updateHits", id);
	}
	
	public int save(BoardDTO boardDTO) {
		return sql.insert("Board.save", boardDTO);
	}

	public void delete(Long id) {
		sql.delete("Board.delete", id);
	}

	public void update(BoardDTO boardDTO) {
		sql.update("Board.update", boardDTO);
	}

}
