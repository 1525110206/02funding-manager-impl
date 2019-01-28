package com.javaweb.funding.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.bean.Advertisement;
import com.javaweb.funding.manager.dao.AdvertisementMapper;
import com.javaweb.funding.manager.service.AdvertService;

@Service
public class AdvertServiceImpl implements AdvertService {
	@Autowired
	AdvertisementMapper advertisementMapper;
	
	@Override
	public int insertAdvert(Advertisement advert) {
		return advertisementMapper.insert(advert);
	}

}
