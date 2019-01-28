package com.javaweb.funding.manager.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.manager.dao.TestDao;
import com.javaweb.funding.manager.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	@Autowired
	private TestDao testDao;
	
	@Override
	public void insert() {
		Map map = new HashMap();
		map.put("id", 1);
		map.put("name","zhangsan");
		testDao.insert(map);
	}

}
