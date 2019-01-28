package com.javaweb.funding.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.bean.RolePermission;
import com.javaweb.funding.bean.User;
import com.javaweb.funding.manager.dao.RoleMapper;
import com.javaweb.funding.manager.service.RoleService;
import com.javaweb.funding.util.Page;
import com.javaweb.funding.vo.Data;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleMapper roleMapper;

	@Override
	public Page queryPage(Map<String, Object> paramMap) {
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pageSize"));

		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		List<User> datas = roleMapper.queryList(paramMap);
		page.setDatas(datas);

		Integer count = roleMapper.queryCount(paramMap);
		page.setTotalSize(count);

		return page;
	}

	@Override
	public int saveRolePermissionRelationship(Integer roleid, Data datas) {
		roleMapper.deleteRolePermissionRelationship(roleid);
		
		int totalCount = 0;
		List<Integer> ids = datas.getIds();
		for(Integer permissionid : ids){
			RolePermission rp = new RolePermission();
			rp.setRoleid(roleid);
			rp.setPermissionid(permissionid);
			int count = roleMapper.insertRolePermission(rp);
			totalCount += count;
		}
		
		return totalCount;
	}
}
