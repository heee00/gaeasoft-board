package com.gaeasoft.project.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressApiController {
	
    @GetMapping("/search")
    public String searchApiForm() {
        return "addressApi";
    }
	
    @PostMapping("/api")
    public void getAddressApi(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {
    	String currentPage = req.getParameter("currentPage");
        String countPerPage = req.getParameter("countPerPage");
        String resultType = req.getParameter("resultType");
        String confmKey = req.getParameter("confmKey");
        String keyword = req.getParameter("keyword");
        
		// OPEN API 호출 URL 정보 설정
		String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage
				+"&countPerPage="+countPerPage
				+"&keyword="+URLEncoder.encode(keyword,"UTF-8")
				+"&confmKey="+confmKey
				+"&resultType="+resultType;
		
		URL url = new URL(apiUrl);
    	BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
    	StringBuffer sb = new StringBuffer();
    	String tempStr = null;

    	while(true){
    		tempStr = br.readLine();
    		if(tempStr == null) break;
    		sb.append(tempStr);								// 응답결과 JSON 저장
    	}
    	br.close();
    	
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		response.getWriter().write(sb.toString());			// 응답결과 반환
    }
    
}
