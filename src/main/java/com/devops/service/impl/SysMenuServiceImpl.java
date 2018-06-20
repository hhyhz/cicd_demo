package com.devops.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.devops.entity.SysMenu;
import com.devops.entity.vo.TreeMenu;
import com.devops.entity.vo.TreeMenuAllowAccess;
import com.devops.mapper.SysMenuMapper;
import com.devops.mapper.SysRoleMenuMapper;
import com.devops.service.ISysMenuService;
import com.google.common.collect.Lists;

/**
 *
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
	@Autowired private SysMenuMapper sysMenuMapper;
	@Autowired private SysRoleMenuMapper sysRoleMenuMapper;
	

	@Cacheable(value = "permissionCache", key = "#uid")
	@Override
	public List<String> selectMenuIdsByuserId(String uid) {
		// TODO Auto-generated method stub
		return sysMenuMapper.selectMenuIdsByuserId(uid);
	}

	
	@Cacheable(value = "menuCache", key = "#uid")
	@Override
	public List<TreeMenu> selectTreeMenuByUserId(String uid) {
		// TODO Auto-generated method stub
		List<String> menuIds = sysRoleMenuMapper.selectRoleMenuIdsByUserId(uid);
		return selectTreeMenuByMenuIdsAndPid(menuIds, "0");
	}
	
	@Override
	public List<TreeMenu> selectTreeMenuByMenuIdsAndPid(final List<String> menuIds,String pid) {
		// TODO Auto-generated method stub
		EntityWrapper<SysMenu> ew = new EntityWrapper<SysMenu>();
		ew.orderBy("sort", true);
		ew.eq("pid", pid);
		ew.in("id", menuIds.size() > 0 ? menuIds : Lists.newArrayList(RandomStringUtils.randomNumeric(30)));
		List<SysMenu> sysMenus = this.selectList(ew);
		List<TreeMenu> treeMenus = new ArrayList<TreeMenu>();
		for(SysMenu sysMenu : sysMenus){
			TreeMenu treeMenu = new TreeMenu();
			treeMenu.setSysMenu(sysMenu);
			if(sysMenu.getDeep() < 2){
				treeMenu.setChildren(selectTreeMenuByMenuIdsAndPid(menuIds,sysMenu.getId()));
			}
			treeMenus.add(treeMenu);
		}
		return treeMenus;
	}
	
	
	@Override
	public List<TreeMenuAllowAccess> selectTreeMenuAllowAccessByMenuIdsAndPid(
			final List<String> menuIds, String pid) {
		// TODO Auto-generated method stub
		EntityWrapper<SysMenu> ew = new EntityWrapper<SysMenu>();
		ew.orderBy("sort", true);
		ew.eq("pid", pid);
		List<SysMenu> sysMenus = this.selectList(ew);
		
		List<TreeMenuAllowAccess> treeMenuAllowAccesss = new ArrayList<TreeMenuAllowAccess>();
		for(SysMenu sysMenu : sysMenus){
			TreeMenuAllowAccess treeMenuAllowAccess = new TreeMenuAllowAccess();
			treeMenuAllowAccess.setSysMenu(sysMenu);
			if(menuIds.contains(sysMenu.getId())){
				treeMenuAllowAccess.setAllowAccess(true);
			}
			/**
			 *sub node
			 */
			if(sysMenu.getDeep() < 3){
				treeMenuAllowAccess.setChildren(selectTreeMenuAllowAccessByMenuIdsAndPid(menuIds,sysMenu.getId()));
			}
			treeMenuAllowAccesss.add(treeMenuAllowAccess);
		}
		return treeMenuAllowAccesss;
	}

}