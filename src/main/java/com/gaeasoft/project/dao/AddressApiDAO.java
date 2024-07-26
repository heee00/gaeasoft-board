package com.gaeasoft.project.dao;

public interface AddressApiDAO {
	
	// 주소 검색
	public String getAddressApi(String currentPage, String countPerPage, String resultType, String keyword);

}
