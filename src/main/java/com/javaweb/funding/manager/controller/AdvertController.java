package com.javaweb.funding.manager.controller;

import java.io.File;
import java.util.UUID;








import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javaweb.funding.bean.Advertisement;
import com.javaweb.funding.bean.User;
import com.javaweb.funding.manager.service.AdvertService;
import com.javaweb.funding.util.AjaxResult;
import com.javaweb.funding.util.Const;



@Controller
@RequestMapping("/advert")
public class AdvertController {
	@Autowired
	AdvertService advertService;
	
	@RequestMapping("/index")
	public String index(){
		return "advert/index";
	}
	
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "advert/add";
	}
	
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(HttpServletRequest request, Advertisement advert, HttpSession session){
		
		AjaxResult result = new AjaxResult(); 
		
		try {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
			
			MultipartFile mfile = mreq.getFile("advpic");
			
			String name = mfile.getOriginalFilename();//java.jpg
			String extname = name.substring(name.lastIndexOf("."));//.jpg
			String iconpath = UUID.randomUUID().toString()+extname;//23222.jpg
			ServletContext servletContext = session.getServletContext();
			String realpath = servletContext.getRealPath("/pics");
			String path = realpath+"\\adv\\"+iconpath;
			mfile.transferTo(new File(path));
			User user = (User)session.getAttribute(Const.LOGIN_USER);
			
			advert.setUserid(user.getId());
			advert.setStatus("1");
			advert.setIconpath(iconpath);
			
			int count = advertService.insertAdvert(advert);
			
			result.setSuccess(count == 1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);		             
		}
		return result;
	}
	
}
