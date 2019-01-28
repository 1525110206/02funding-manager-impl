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





import com.javaweb.funding.bean.Permission;
import com.javaweb.funding.manager.service.PermissionService;
import com.javaweb.funding.manager.service.RoleService;
import com.javaweb.funding.util.AjaxResult;
import com.javaweb.funding.util.Page;
import com.javaweb.funding.util.StringUtil;
import com.javaweb.funding.vo.Data;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	
	
	@RequestMapping("/toIndex")
	public String toIndex(){
		return "role/index";
	}
	
	
	@RequestMapping("/assignPermission")
	public String assignPermission(){
		return "role/assignPermission";
	}
	
	@ResponseBody
	@RequestMapping("/loadDataAsync")
	public Object loadDataAsync(Integer roleid){
			List<Permission> root = new ArrayList<Permission>();

			
			List<Permission> childredPermissons =  permissionService.queryAllPermission();
			
			
			//根据角色id查询该角色之前所分配过的许可.
			List<Integer> permissonIdsForRoleid = permissionService.queryPermissionByRoleid(roleid);
			
			
			Map<Integer,Permission> map = new HashMap<Integer,Permission>();//100
			
			for (Permission innerpermission : childredPermissons) {
				map.put(innerpermission.getId(), innerpermission);
				if(permissonIdsForRoleid.contains(innerpermission.getId())){
					innerpermission.setChecked(true);//checked默认是false，这样来回显表单
				}
			}
			
			
			for (Permission permission : childredPermissons) { //100
				//通过子查找父
				//子菜单
				Permission child = permission ; //假设为子菜单
				if(child.getPid() == null ){
					root.add(permission);
				}else{
					//父节点
					Permission parent = map.get(child.getPid());//上面的循环已经把所有的父元素和子元素都放进map集合中了，现在用child.getPid()作为条件获取父元素，再存进集合中
					parent.getChildren().add(child);
				}
			}
			return root ;
	}
	
	@ResponseBody
	@RequestMapping("/doAssignPermission")
	public Object doAssignPermission(Integer roleid, Data datas){
		AjaxResult result = new AjaxResult();
		try {
			int count = roleService.saveRolePermissionRelationship(roleid,datas);
			result.setSuccess(count == datas.getIds().size());
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
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
			Page page = roleService.queryPage(paramMap);
			
			result.setSuccess(true);
			result.setPage(page);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询失败");
			e.printStackTrace();
		}
		return result;
	}
	
}
