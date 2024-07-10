package com.gaeasoft.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.gaeasoft.project.dao.AddressApiDAOImpl;

@Service
public class AddressApiService {
	
	@Autowired
	private AddressApiDAOImpl addressDAOImpl;
	
	public ResponseEntity<String> getAddressApi(HttpServletRequest req, ModelMap model) {
        String apiUrl = null;        
		BufferedReader br = null;
		String callback = req.getParameter("callback");
		
		try {
			apiUrl = addressDAOImpl.getAddressApi(req, model);
			URL url = new URL(apiUrl);
        	
			br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
          	StringBuilder sb = new StringBuilder();
            String tempStr;
             
            while ((tempStr = br.readLine()) != null) {
            	sb.append(tempStr); // 응답결과 JSON 저장
            }
            
     		return ResponseEntity.ok()
     						.header("Content-Type", "application/json")
     						.body(callback + "(" + sb.toString() + ");"); // 응답결과 반환
     		 
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        					.body(callback + "{response 데이터 읽기 실패: " + e.getMessage() + "}");
          
        } finally {
        	if (br != null) {
        		try {
        			br.close();
                  
                } catch (Exception e) {
                	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                					.body(callback +"{BufferedReader 닫기 실패: " + e.getMessage() + "}");
                }
         	}
        }
    }

}
