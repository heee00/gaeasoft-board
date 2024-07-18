package com.gaeasoft.project.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

public interface AddressApiDAO {
	
	// 주소 검색
	public ResponseEntity<String> searchAddress(HttpServletRequest req);

}
