package com.gaeasoft.project.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gaeasoft.project.dao.AddressApiDAOImpl;

@Service
public class AddressApiService {
	
	@Autowired
	private AddressApiDAOImpl addressDAOImpl;
	
	public ResponseEntity<String> getAddressApi(HttpServletRequest req) {
		return addressDAOImpl.searchAddress(req);
	}
	
}
