package com.gaeasoft.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gaeasoft.project.dto.BoardDTO;
import com.gaeasoft.project.dto.PageDTO;
import com.gaeasoft.project.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/")
	public String findAll(Model model) {
		List<BoardDTO> boardList = boardService.findAll();
		model.addAttribute("boardList", boardList);
		
		return "boardList";
	}
	
	@GetMapping("/paging")
	public String paging(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		List<BoardDTO> pagingList = boardService.pagingList(page);
		PageDTO pageDTO = boardService.pagingParam(page);
		model.addAttribute("pagingList", pagingList);
		model.addAttribute("paging", pageDTO);
		
		return "pagingList";
	}
	
	@GetMapping
	public String findById(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		boardService.updateHits(id);
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		
		return "boardDetail";
	}
	
	@GetMapping("/save")
	public String save(@RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		model.addAttribute("page", page);
		
		return "saveBoard";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute BoardDTO boardDTO, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		int saveResult = boardService.save(boardDTO);
		model.addAttribute("page", page);

		if (saveResult > 0) {
			return "redirect:/board/paging";
		} else {
			return "saveBoard";
		}
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		boardService.delete(id);
		model.addAttribute("page", page);
		
		return "redirect:/board/paging";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		BoardDTO boardDTO = boardService.findById(id);
		model.addAttribute("board", boardDTO);
		model.addAttribute("page", page);
		
		return "updateBoard";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute BoardDTO boardDTO, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) {
		boardService.update(boardDTO);
		BoardDTO dto = boardService.findById(boardDTO.getId());
		model.addAttribute("board", dto);
		model.addAttribute("page", page);
		
		return "boardDetail";
	}
	
}
