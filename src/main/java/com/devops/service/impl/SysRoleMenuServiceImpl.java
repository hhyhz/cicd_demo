package com.devops.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.devops.entity.SysRoleMenu;
import com.devops.mapper.SysMenuMapper;
import com.devops.mapper.SysRoleMenuMapper;
import com.devops.service.ISysRoleMenuService;

/**
 *
 * SysRoleMenu 
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {
	
	@Autowired private SysMenuMapper sysMenuMapper;
	
	@Override
	public void addAuth(String roleId, String[] menuIds) {
		// TODO Auto-generated method stub
		
		/**
		 * delete old role
		 */
		this.delete(new EntityWrapper<SysRoleMenu>().eq("roleId",roleId));
		/**
		 *  reauth
		 */
		if(ArrayUtils.isNotEmpty(menuIds)){
			for(String menuId : menuIds){
				SysRoleMenu sysRoleMenu2 = new SysRoleMenu();
				sysRoleMenu2.setRoleId(roleId);
				sysRoleMenu2.setMenuId(menuId);
				this.insert(sysRoleMenu2);
			}
		}
	}

	@Override
	public List<SysRoleMenu> selectByRole(String roleId) {
		// TODO Auto-generated method stub
		
		EntityWrapper<SysRoleMenu> ew = new EntityWrapper<SysRoleMenu>();
		ew.addFilter("roleId = {0}", roleId);
		return this.selectList(ew);
		
	}

	@Override
	public Set<String> findMenusByUid(String id) {
		// TODO Auto-generated method stub
		List<String> list =  sysMenuMapper.selectResourceByUid(id);
		return new HashSet<>(list);
	}


}