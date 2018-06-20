package com.devops.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.SysUser;

/**
 *
 *
 */
public interface ISysUserService extends IService<SysUser> {
	
	Page<Map<Object, Object>> selectUserPage(Page<Map<Object, Object>> page, String search);
	
	void insertUser(SysUser user, String[] roleId);
	void updateUser(SysUser sysUser, String[] roleId);
	void delete(String id);

}