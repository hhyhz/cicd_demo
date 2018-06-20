package com.devops.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.devops.common.util.ShiroUtil;
import com.devops.entity.SysUser;
import com.devops.entity.SysUserRole;
import com.devops.mapper.SysUserMapper;
import com.devops.mapper.SysUserRoleMapper;
import com.devops.service.ISysUserService;

/**
 *
 * SysUser 
 *
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	@Autowired private SysUserMapper userMapper;
	
	@Autowired private SysUserRoleMapper userRoleMapper;
	
	@Override
	public void insertUser(SysUser user, String[] roleIds) {
		// TODO Auto-generated method stub
		user.setCreateTime(new Date());
    	user.setPassword(ShiroUtil.md51024Pwd(user.getPassword(), user.getUserName()));
    	userMapper.insert(user);
		//bind role
		if(ArrayUtils.isNotEmpty(roleIds)){
			for(String rid : roleIds){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(user.getId());
				sysUserRole.setRoleId(rid);
				userRoleMapper.insert(sysUserRole);
			}
		}
		
	}

	@Override
	public void updateUser(SysUser sysUser, String[] roleIds) {
		// TODO Auto-generated method stub
		sysUser.setPassword(null);
		//更新用户
		userMapper.updateById(sysUser);
		//删除已有权限
		userRoleMapper.delete(new EntityWrapper<SysUserRole>().eq("userId",sysUser.getId()));
		//重新绑定角色
		if(ArrayUtils.isNotEmpty(roleIds)){
			for(String rid : roleIds){
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(sysUser.getId());
				sysUserRole.setRoleId(rid);
				userRoleMapper.insert(sysUserRole);
			}
		}
	}

	@Override
	public Page<Map<Object, Object>> selectUserPage(Page<Map<Object, Object>> page, String search) {
		// TODO Auto-generated method stub
		page.setRecords(baseMapper.selectUserList(page, search));
		return page;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.deleteById(id);
		userRoleMapper.delete(new EntityWrapper<SysUserRole>().addFilter("userId = {0}", id));
	}


}