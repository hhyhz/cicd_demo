package com.devops.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.SysRoleMenu;

/**
 *
 *
 */
public interface ISysRoleMenuService extends IService<SysRoleMenu> {
	
	void addAuth(String roleId, String[] menuIds);
	
	List<SysRoleMenu> selectByRole(String roleId);

	Set<String> findMenusByUid(String id);


}