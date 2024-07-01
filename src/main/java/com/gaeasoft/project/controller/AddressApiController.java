package com.gaeasoft.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressApiController {
	
	@PostMapping("/api")
	public void getAddressApi(HttpServletRequest req, ModelMap model, HttpServletResponse response) throws Exception {
    	String currentPage = req.getParameter("currentPage");
        String countPerPage = req.getParameter("countPerPage");
        String resultType = req.getParameter("resultType");
        String confmKey = req.getParameter("confmKey");
        String keyword = req.getParameter("keyword");
        String callback = req.getParameter("callback");
        String apiUrl = null;
        
        // OPEN API 호출 URL 정보 설정
        try {
            apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage
                    + "&countPerPage=" + countPerPage
                    + "&keyword=" + URLEncoder.encode(keyword, "UTF-8")
                    + "&confmKey=" + confmKey
                    + "&resultType=" + resultType;
       
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "API URL 생성 실패");
            return;
        }
        
        try {
            URL url = new URL(apiUrl);

            // URL Connection을 통해 데이터 읽기
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                StringBuilder sb = new StringBuilder();
                String tempStr;

                while ((tempStr = br.readLine()) != null) {
                    sb.append(tempStr); // 응답결과 JSON 저장
                }

                // JSONP 응답 설정
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/javascript");
                String jsonpResponse = callback + "(" + sb.toString() + ");";
                response.getWriter().write(jsonpResponse); // 응답결과 반환
           
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "API response 데이터 읽기 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "API URL 호출 실패");
        }
	}
	
}
