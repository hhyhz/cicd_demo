package com.devops.service;

import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.SysUserRole;

/**
 *
 *
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
	
	Set<String> findRolesByUid(String uid);
}