package com.javaweb.funding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.manager.dao.AccountTypeCertMapper;
import com.javaweb.funding.manager.service.CerttypeService;

@Service
public class CerttypeServiceImpl implements CerttypeService{
	
	@Autowired
	AccountTypeCertMapper accountTypeCertMapper;

	@Override
	public List<Map<String, Object>> queryCertAccttype() {
		// TODO Auto-generated method stub
		return accountTypeCertMapper.queryCertAccttype();
	}

	@Override
	public int deleteAcctTypeCert(Map<String, Object> paramMap) {
		
		return accountTypeCertMapper.deleteAcctTypeCert(paramMap);
	}

	@Override
	public int insertAcctTypeCert(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return accountTypeCertMapper.insertAcctTypeCert(paramMap);
	}
}
