package com.javaweb.funding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.bean.Cert;
import com.javaweb.funding.bean.MemberCert;
import com.javaweb.funding.manager.dao.CertMapper;
import com.javaweb.funding.manager.service.CertService;


@Service
public class CertServiceImpl implements CertService {
	@Autowired
	CertMapper certMapper;

	@Override
	public List<Cert> queryAllCert() {
	
		return certMapper.selectAll();
	}

	@Override
	public List<Cert> queryCertByAccttype(String accttype) {
		
		return certMapper.queryCertByAccttype(accttype);
	}

	@Override
	public void saveMemberCert(List<MemberCert> certimgs) {
		// TODO Auto-generated method stub
		for(MemberCert memberCert : certimgs ){
			certMapper.saveMemberCert(memberCert);
		}
		
	}
	
	
}
