package com.gaeasoft.project.dao;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressApiDAOImpl implements AddressApiDAO {
	
	private static final String confmKey = "devU01TX0FVVEgyMDI0MDYxMjE3MDc0ODExNDgzODI=";
	
	// 주소 검색
	public String getAddressApi(HttpServletRequest req, ModelMap model) throws Exception {
		String currentPage = req.getParameter("currentPage");
		String countPerPage = req.getParameter("countPerPage");
		String resultType = req.getParameter("resultType");
		String keyword = req.getParameter("keyword");
		String apiUrl = null;
        
		try {
			apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage
			         +"&countPerPage="+countPerPage
			         +"&keyword="+URLEncoder.encode(keyword,"UTF-8")
			         +"&confmKey="+ confmKey
			         +"&resultType="+resultType;
		} catch (Exception e) {
            throw new Exception("URL 인코딩 실패: " + e.getMessage(), e);
		}
		return apiUrl;
		
	}

}
