package com.gaeasoft.project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> getAddressApi(HttpServletRequest req, ModelMap model) {
         String currentPage = req.getParameter("currentPage");
         String countPerPage = req.getParameter("countPerPage");
         String resultType = req.getParameter("resultType");
         String confmKey = req.getParameter("confmKey");
         String keyword = req.getParameter("keyword");
         String callback = req.getParameter("callback");
         String apiUrl = null;
         
         // OPEN API 호출 URL 정보 설정
         try {
        	 apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage
	                     +"&countPerPage="+countPerPage
	                     +"&keyword="+URLEncoder.encode(keyword,"UTF-8")
	                     +"&confmKey="+confmKey
	                     +"&resultType="+resultType;
         
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                     .body(callback  + "{URL 인코딩 실패: " + e.getMessage() + "}");
         }
         
         try {
        	 URL url = new URL(apiUrl);
        	 
        	 try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                 StringBuilder sb = new StringBuilder();
        		 String tempStr;
        		 
        		 while((tempStr = br.readLine()) != null) {
        			 sb.append(tempStr); // 응답결과 JSON 저장
        		 }
        		 
        		 return ResponseEntity.ok()
                         .header("Content-Type", "application/json")
                         .body(callback + "(" + sb.toString() + ");"); // 응답결과 반환
        	 
        	 } catch (IOException e) {
                 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body(callback +"{response 데이터 읽기 실패: " + e.getMessage() + "}");
             }

         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(callback +"{URL 호출 실패: " + e.getMessage() + "}");
         }
 	}
    
}
