package com.gaeasoft.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaeasoft.project.dao.AddressApiDAOImpl;

@Service
public class AddressApiService {
	
	@Autowired
	private AddressApiDAOImpl addressDAOImpl;
	
	public String getAddressApi(String currentPage, String countPerPage, String resultType, String keyword)  {
		return addressDAOImpl.getAddressApi(currentPage, countPerPage, resultType, keyword);
	}
	
}
