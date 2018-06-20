package com.devops.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.devops.entity.SysRoleMenu;

/**
 *
 *
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

	public List<String> selectRoleMenuIdsByUserId(String uid);
	
}