package com.javaweb.funding.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaweb.funding.bean.Cert;
import com.javaweb.funding.manager.service.CertService;
import com.javaweb.funding.manager.service.CerttypeService;
import com.javaweb.funding.util.AjaxResult;


@Controller
@RequestMapping("/certtype")
public class CerttypeController {
	@Autowired
	private CertService certService;

	@Autowired
	private CerttypeService certtypeService;
	@RequestMapping("/index")
	public String index(Map<String, Object> map){
		
		List<Cert> queryAllCert = certService.queryAllCert();
		
		map.put("allCert", queryAllCert);
		
		List<Map<String, Object>> certAccttypeList = certtypeService.queryCertAccttype();
		
		map.put("certAccttypeList", certAccttypeList);
		
		return "certtype/index";
	}
	
	@ResponseBody
	@RequestMapping("/deleteAcctTypeCert")
	public Object deleteAcctTypeCert(Integer certid, String accttype){
		AjaxResult result = new AjaxResult();
		
		try{
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			
			int count = certtypeService.deleteAcctTypeCert(paramMap);
			
			result.setSuccess(true);
		} catch( Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
		
	}
	
	@ResponseBody
	@RequestMapping("/insertAcctTypeCert")
	public Object insertAcctTypeCert(Integer certid, String accttype){
		AjaxResult result = new AjaxResult();
		
		try{
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("certid", certid);
			paramMap.put("accttype", accttype);
			
			int count = certtypeService.insertAcctTypeCert(paramMap);
			
			result.setSuccess(true);
		} catch( Exception e){
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	
}
