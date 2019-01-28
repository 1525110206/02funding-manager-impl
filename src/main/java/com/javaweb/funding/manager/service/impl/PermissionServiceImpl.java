package com.javaweb.funding.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.bean.Permission;
import com.javaweb.funding.manager.dao.PermissionMapper;
import com.javaweb.funding.manager.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	PermissionMapper permissionMapper;

	@Override
	public Permission getRootPermission() {
		return permissionMapper.getRootPermission();
	}

	@Override
	public List<Permission> getChildrenPermissionByPid(Integer id) {
		// TODO Auto-generated method stub
		return permissionMapper.getChildrenPermissionByPid(id);
	}

	@Override
	public int savePermission(Permission permission) {
		return permissionMapper.insert(permission);
	}

	@Override
	public int updatePermission(Permission permission) {
		return permissionMapper.updateByPrimaryKey(permission);
	}

	@Override
	public Permission getPermission(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deletePermission(Integer id) {
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Integer> queryPermissionByRoleid(Integer roleid) {
		return permissionMapper.queryPermissionByRoleid(roleid);
	}

	@Override
	public List<Permission> queryAllPermission() {
		return permissionMapper.selectAll();
	}

	
	
	
}
