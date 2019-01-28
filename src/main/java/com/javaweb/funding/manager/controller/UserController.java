package com.javaweb.funding.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;














import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaweb.funding.bean.Role;
import com.javaweb.funding.bean.User;
import com.javaweb.funding.manager.service.UserService;
import com.javaweb.funding.util.AjaxResult;
import com.javaweb.funding.util.Page;
import com.javaweb.funding.util.StringUtil;
import com.javaweb.funding.vo.Data;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		
		return "user/add";
	}
	@RequestMapping("/doAssignRole")
	@ResponseBody
	public Object doAssignRole(Integer userid, Data data){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.saveUserRoleRelationship(userid,data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("分配角色失败");
		}
		return result;
	}
	
	@RequestMapping("/doUnAssignRole")
	@ResponseBody
	public Object doUnAssignRole(Integer userid, Data data){
		AjaxResult result = new AjaxResult();
		try {
			int count = userService.deleteUserRoleRelationship(userid,data);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
			result.setMessage("分配角色失败");
		}
		return result;
	}
	
	@RequestMapping("/assignrole")
	public String assignrole(Integer id,Map map){
		List<Role> roleAll = userService.queryAllRoles();
		
		List<Integer> roleIds = userService.queryRoleIds(id);
		
		List<Role> leftRole = new ArrayList<>();
		List<Role> rightRole = new ArrayList<>();
		
		for (Role r : roleAll) {
			if(roleIds.contains(r.getId())){
				rightRole.add(r);
			}else{
				leftRole.add(r);
			}
		}
		
		map.put("leftRole",leftRole);
		map.put("rightRole", rightRole);
		
		return "user/assignrole";
	}
	
	
	@RequestMapping("/index")
	public String toIndex(){
		
		return "user/index";
	}
	
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Map map){
		User user = userService.queryById(id);
		map.put("user", user);
		return "user/update";
	}
	
	@ResponseBody
	@RequestMapping("/doDeleteBatch")
	public Object doDeleteBatch(Integer[] id){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = userService.deleteBatchUser(id);
			result.setSuccess(count == id.length);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除信息失败");
			e.printStackTrace();
		}
		
		return  result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doDelete")
	public Object doDelete(Integer id){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = userService.deleteUser(id);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除信息失败");
			e.printStackTrace();
		}
		
		return  result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doUpdate")
	public Object doUpdate(User user){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = userService.updateUser(user);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改信息失败");
			e.printStackTrace();
		}
		
		return  result;
	}
	
	
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(User user){
		AjaxResult result = new AjaxResult();
		
		try {
			int count = userService.saveUser(user);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询失败");
			e.printStackTrace();
		}
		
		return  result;
	}
	
	
	@ResponseBody
	@RequestMapping("/doIndex")
	public Object doIndex(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno, 
			@RequestParam(value="pageSize", required=false, defaultValue="10")Integer pageSize, 
			String queryText){
		
		AjaxResult result = new AjaxResult();
		
		try {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("pageno", pageno);
			paramMap.put("pageSize", pageSize);
			if(StringUtil.isNotEmpty(queryText)){
				if(queryText.contains("%")){
					queryText = queryText.replaceAll("%", "\\\\%");
				}
				paramMap.put("queryText", queryText);
			}
			Page page = userService.queryPage(paramMap);
			
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询失败");
			e.printStackTrace();
		}
		return result;
	}
	
	//异步传输
	/*@ResponseBody
	@RequestMapping("/index")
	public Object index(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno, 
			@RequestParam(value="pagesize", required=false, defaultValue="10")Integer pagesize, Map map){
		
		AjaxResult result = new AjaxResult();
		
		try {
			Page page = userService.queryPage(pageno, pagesize);
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询失败");
			e.printStackTrace();
		}
		return result;
	}*/
	
	//同步传输
	/*@RequestMapping("/index")
	public String index(@RequestParam(value="pageno", required=false, defaultValue="1") Integer pageno, 
			@RequestParam(value="pagesize", required=false, defaultValue="10")Integer pagesize, Map map){
		
		Page page = userService.queryPage(pageno, pagesize);
		
		map.put("page", page);
		
		return "user/index";
	}*/
}
