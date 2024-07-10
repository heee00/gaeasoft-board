package com.gaeasoft.project.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public interface AddressApiDAO {
	
	// 주소 검색
	public String getAddressApi(HttpServletRequest req, ModelMap model) throws Exception;

}
