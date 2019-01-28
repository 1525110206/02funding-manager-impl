package com.javaweb.funding.manager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javaweb.funding.util.AjaxResult;
import com.javaweb.funding.util.Page;

@Controller
@RequestMapping("/process")
public class ProcessController {
	@Autowired
	private RepositoryService repositoryService;
	
	
	@RequestMapping("/showing")
	public String showing(){
		return "process/showing";
	}
	
	@RequestMapping("/index")
	public String index(){
		return "process/index";
	}
	
	@ResponseBody
	@RequestMapping("/showimgProDef")
	public void showimgProDef(String id, HttpServletResponse response) throws IOException{
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
		
		InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
		
		ServletOutputStream outputStream = response.getOutputStream();
		
		IOUtils.copy(resourceAsStream, outputStream);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno,
			@RequestParam(value="pagesize", required = false, defaultValue="10") Integer pageSize){
		AjaxResult result = new AjaxResult();
		try {
			Page page = new Page(pageno, pageSize);
			
			ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
			//查询流程定义集合数据，可能出现了自关联，导致Jackson组件无法将集合序列化为JSON串
			List<ProcessDefinition> listPage = processDefinitionQuery.listPage(page.getStartIndex(), pageSize);
			
			List<Map<String, Object>> mylistPage = new ArrayList<>();
			
			for(ProcessDefinition processDefinition : listPage){
				
				Map<String, Object> pd = new HashMap<>();
				pd.put("id", processDefinition.getId());
				pd.put("name", processDefinition.getName());
				pd.put("key", processDefinition.getKey());
				pd.put("version", processDefinition.getVersion());
				mylistPage.add(pd);
			}
			
			
			Long totalsize = processDefinitionQuery.count();
			
			page.setDatas(mylistPage);
			
			page.setTotalSize(totalsize.intValue());
			
			result.setPage(page);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询流程定义失败");
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(String id){
		AjaxResult result = new AjaxResult();
		try {
			ProcessDefinition processDefition = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
			
			repositoryService.deleteDeployment(processDefition.getDeploymentId(),true);
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("部署失败");
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/deploy")
	public Object deploy(HttpServletRequest request){
		AjaxResult result = new AjaxResult();
		try {
			
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
			
			MultipartFile multipartFile = multipartHttpServletRequest.getFile("processDefFile");
			
			repositoryService.createDeployment().addInputStream(multipartFile.getOriginalFilename(), multipartFile.getInputStream()).deploy();
			
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("部署失败");
			e.printStackTrace();
		}
		return result;
	}
	
	
}
