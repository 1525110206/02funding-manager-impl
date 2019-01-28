package com.javaweb.funding.manager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaweb.funding.bean.Permission;
import com.javaweb.funding.manager.service.PermissionService;
import com.javaweb.funding.util.AjaxResult;

@RequestMapping("/permission")
@Controller
public class PermissionController {
	@Autowired
	PermissionService permissionService;
	
	
	@RequestMapping("/toIndex")
	public String index(){
		return "permission/index";
	}
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "permission/add";
	}
	
	@ResponseBody
	@RequestMapping("/deletePermission")
	public Object deletePermission(Integer id){
		AjaxResult result = new AjaxResult();
		try {
			int count = permissionService.deletePermission(id);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除许可失败");
		}
		
		return result;
	}
	
	
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Map map){
		
		Permission permission = permissionService.getPermission(id);
		
		map.put("permission", permission);
		
		return "permission/update";
	}
	
	
	@ResponseBody
	@RequestMapping("/doUpdate")
	public Object doUpdate(Permission permission){
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			int count = permissionService.updatePermission(permission);
			
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改许可失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/doAdd")
	public Object doAdd(Permission permission){
		
		AjaxResult result = new AjaxResult();
		
		try {
			
			int count = permissionService.savePermission(permission);
			
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("保存数据失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
		AjaxResult result = new AjaxResult();
		
		try {
			List<Permission> root = new ArrayList<Permission>();
			
			//父
			Permission permission = permissionService.getRootPermission();
			
			root.add(permission);
			
			//子
			queryChildren(permission);
			
			result.setSuccess(true);
			result.setData(root);;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取许可树失败");
		}
		
		return result;
	}
	
	private void queryChildren(Permission permission){
		List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
		
		permission.setChildren(children);
		
		for (Permission innerChildren : children) {
			queryChildren(innerChildren);
		}
		
		
	}
	
	
	
	
	/*@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData(){
		AjaxResult result = new AjaxResult();
		
		try {
			List<Permission> root = new ArrayList<Permission>();
			
			//父
			Permission permission = permissionService.getRootPermission();
			
			root.add(permission);
			
			//子
			List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
			
			permission.setChildren(children);
			
			for (Permission child : children) {
				child.setOpen(true);
				List<Permission> innerChildren = permissionService.getChildrenPermissionByPid(child.getId());
				child.setChildren(innerChildren);
			}
			
			result.setSuccess(true);
			result.setData(root);;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取许可树失败");
		}
		
		return result;
	}
	*/
}
