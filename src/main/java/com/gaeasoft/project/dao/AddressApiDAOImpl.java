package com.gaeasoft.project.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AddressApiDAOImpl implements AddressApiDAO {
	
	private static final String confmKey = "devU01TX0FVVEgyMDI0MDYxMjE3MDc0ODExNDgzODI=";
	
	// 주소 검색
	public String getAddressApi(String currentPage, String countPerPage, String resultType, String keyword) {
		String apiUrl = null;
		BufferedReader br = null;
        
		try {
			apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage="+currentPage
			         +"&countPerPage="+countPerPage
			         +"&keyword="+URLEncoder.encode(keyword,"UTF-8")
			         +"&confmKey="+ confmKey
			         +"&resultType="+resultType;
			
			URL url = new URL(apiUrl);
			
			br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
          	StringBuilder sb = new StringBuilder();
            String tempStr;
             
            while ((tempStr = br.readLine()) != null) {
            	sb.append(tempStr); // 응답결과 JSON 저장
            }
            return sb.toString();
     		
		} catch (Exception e) {
    		return ("{response 데이터 읽기 실패: " + e.getMessage() + "}");
          
        } finally {
        	if (br != null) {
        		try {
        			br.close();
                  
                } catch (Exception e) {
                	return ("{BufferedReader 닫기 실패: " + e.getMessage() + "}");
                }
         	}
        }
    }

}
