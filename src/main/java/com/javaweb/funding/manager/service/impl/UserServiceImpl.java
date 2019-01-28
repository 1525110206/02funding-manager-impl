package com.javaweb.funding.manager.service.impl;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.funding.bean.Permission;
import com.javaweb.funding.bean.Role;
import com.javaweb.funding.bean.User;
import com.javaweb.funding.exception.LoginFailException;
import com.javaweb.funding.manager.dao.UserMapper;
import com.javaweb.funding.manager.service.UserService;
import com.javaweb.funding.util.Const;
import com.javaweb.funding.util.MD5Util;
import com.javaweb.funding.util.Page;
import com.javaweb.funding.vo.Data;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User queryUserLogin(Map<String, Object> map) {

		User user = userMapper.queryUserLogin(map);

		if (user == null) {
			throw new LoginFailException("用户账号或密码不正确");
		}
		return user;
	}

	/*
	 * @Override public Page queryPage(Integer pageno, Integer pageSize) {
	 * 
	 * Page page = new Page(pageno, pageSize);
	 * 
	 * Integer startIndex = page.getStartIndex(); List<User> datas =
	 * userMapper.queryList(startIndex, pageSize); page.setDatas(datas);
	 * 
	 * Integer count = userMapper.queryCount(); page.setTotalSize(count);
	 * 
	 * return page; }
	 */

	@Override
	public int saveUser(User user) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		String createTime = sdf.format(date);
		user.setCreatetime(createTime);
		
		user.setUserpswd(MD5Util.digest(Const.PASSWORD));
		
		return userMapper.insert(user);
	}

	@Override
	public Page queryPage(Map<String, Object> paramMap) {
		Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pageSize"));

		Integer startIndex = page.getStartIndex();
		paramMap.put("startIndex", startIndex);
		List<User> datas = userMapper.queryList(paramMap);
		page.setDatas(datas);

		Integer count = userMapper.queryCount(paramMap);
		page.setTotalSize(count);

		return page;
	}

	@Override
	public User queryById(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateUser(User user) {
		int count = userMapper.updateByPrimaryKey(user);
		return count;
	}

	@Override
	public int deleteUser(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteBatchUser(Integer[] id) {
		int totalCount = 0;
		for (Integer i : id) {
			int count = userMapper.deleteByPrimaryKey(i);
			totalCount += count;
		}
		if(totalCount != id.length){
			throw new RuntimeException("批量删除失败");
		}
		return totalCount;
	}

	@Override
	public List<Role> queryAllRoles() {
		return userMapper.queryAllRole();
	}

	@Override
	public List<Integer> queryRoleIds(Integer id) {
		return userMapper.queryRoleById(id);
	}

	@Override
	public int saveUserRoleRelationship(Integer userid, Data data) {
		return userMapper.saveUserRoleRelationship(userid, data);
	}

	@Override
	public int deleteUserRoleRelationship(Integer userid, Data data) {
		return userMapper.deleteUserRoleRelationship(userid, data);	
	}

	@Override
	public List<Permission> queryPermissionByUserid(Integer id) { 
		return userMapper.queryPermissionByUserid(id);
	}


}
