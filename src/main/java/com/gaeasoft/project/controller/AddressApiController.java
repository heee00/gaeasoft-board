package com.gaeasoft.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaeasoft.project.service.AddressApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressApiController {
	
	@Autowired
	private AddressApiService apiService;
	
	@PostMapping("/api")
    public ResponseEntity<String> getAddressApi(HttpServletRequest req) {
		String currentPage = req.getParameter("currentPage");
		String countPerPage = req.getParameter("countPerPage");
		String resultType = req.getParameter("resultType");
		String keyword = req.getParameter("keyword");
		String callback = req.getParameter("callback");
		
		String address = apiService.getAddressApi(currentPage, countPerPage, resultType, keyword);
		String response = callback + "(" + address + ");";
		
		return ResponseEntity.ok()
				.header("Content-Type","application/json")
				.body(response);
 	}
	
}
