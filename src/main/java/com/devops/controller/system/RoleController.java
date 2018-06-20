package com.devops.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.devops.common.anno.Log;
import com.devops.common.bean.Rest;
import com.devops.common.controller.SuperController;
import com.devops.entity.SysRole;
import com.devops.entity.SysRoleMenu;
import com.devops.entity.SysUser;
import com.devops.entity.SysUserRole;
import com.devops.entity.vo.TreeMenuAllowAccess;
import com.devops.service.ISysMenuService;
import com.devops.service.ISysRoleMenuService;
import com.devops.service.ISysRoleService;
import com.devops.service.ISysUserRoleService;
import com.devops.service.ISysUserService;
import com.google.common.collect.Lists;
/**
 * role ctl
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends SuperController{  

	@Autowired private ISysRoleService sysRoleService;
	@Autowired private ISysUserRoleService sysUserRoleService;
	@Autowired private ISysUserService sysUserService;
	@Autowired private ISysMenuService sysMenuService;
	@Autowired private ISysRoleMenuService sysRoleMenuService;
	
	@RequiresPermissions("listRole")
    @RequestMapping("/list/{pageNumber}")  
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize, String search,Model model){
    	
		Page<SysRole> page = getPage(pageNumber,pageSize);
		page.setOrderByField("createTime");
		page.setAsc(false);
		model.addAttribute("pageSize",pageSize);
		// 查询分页
		EntityWrapper<SysRole> ew = new EntityWrapper<SysRole>();
		if(StringUtils.isNotBlank(search)){
			ew.like("roleName",search);
			model.addAttribute("search",search);
		}
		Page<SysRole> pageData = sysRoleService.selectPage(page, ew);
		model.addAttribute("pageData", pageData);
		return "system/role/list";
    } 
    
	@RequiresPermissions("addRole")
    @RequestMapping("/add")  
    public  String add(Model model){
		return "system/role/add";
    } 
	@RequiresPermissions("addRole")
    @Log("add role")
    @RequestMapping("/doAdd")  
	@ResponseBody
    public  Rest doAdd(SysRole role){
    	role.setCreateTime(new Date());
    	sysRoleService.insert(role);
		return Rest.ok();

    }  
	@RequiresPermissions("deleteRole")
    @Log("delete role")
    @RequestMapping("/delete")  
    @ResponseBody
    public  Rest delete(String id){
    	sysRoleService.deleteById(id);
    	return Rest.ok();
    }  

    /**
     * batch delete role
     */
	@RequiresPermissions("deleteBatchRole")
    @Log("batch delete role")
    @RequestMapping("/deleteBatch")  
    @ResponseBody
    public Rest deleteBatch(@RequestParam("id[]") List<String> ids){
    	sysRoleService.deleteBatchIds(ids);
    	return Rest.ok();
    }  
    
    /**
     *to edit page
     */
	@RequiresPermissions("editRole")
    @RequestMapping("/edit/{id}")  
    public  String edit(@PathVariable String id,Model model){
    	SysRole sysRole = sysRoleService.selectById(id);
    	model.addAttribute(sysRole);
    	return "system/role/edit";
    } 
    
    /**
     *  do
     */
	@RequiresPermissions("editRole")
    @Log("do edit role via ajax")
    @RequestMapping("/doEdit")  
	@ResponseBody
    public  Rest doEdit(SysRole sysRole,Model model){
    	sysRoleService.updateById(sysRole);
    	return Rest.ok();
    } 
    
    /**
     * role
     */
	@RequiresPermissions("authRole")
    @RequestMapping("/auth/{id}")  
    public  String auth(@PathVariable String id,Model model){
    	
    	SysRole sysRole = sysRoleService.selectById(id);
    	
    	if(sysRole == null){
    		throw new RuntimeException("该角色不存在");
    	}
    	
    	List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.selectList(new EntityWrapper<SysRoleMenu>().eq("roleId", id));
    	List<String> menuIds = Lists.transform(sysRoleMenus,input -> input.getMenuId());
    	
    	List<TreeMenuAllowAccess> treeMenuAllowAccesses = sysMenuService.selectTreeMenuAllowAccessByMenuIdsAndPid(menuIds, "0");

    	model.addAttribute("sysRole", sysRole);
    	model.addAttribute("treeMenuAllowAccesses", treeMenuAllowAccesses);
    	
    	return "system/role/auth";
    } 
    
    /**
     * auth
     */
	@RequiresPermissions("authRole")
    @Log("do auth via ajax")
    @RequestMapping("/doAuth")  
	@ResponseBody
    public  Rest doAuth(String roleId,@RequestParam(value="mid[]",required=false) String[] mid){
    	sysRoleMenuService.addAuth(roleId,mid);
    	return Rest.ok("OK,auth success wait a minute  ~");
    } 
	
	/**
	 * get all of user belong to the role 
	 */
	@RequestMapping("/getUsers")  
	public String getUsers(String roleId,Model model){
		
		List<SysUserRole> sysUserRoles = sysUserRoleService.selectList(new EntityWrapper<SysUserRole>().eq("roleId", roleId));
		
		List<String> userIds = Lists.transform(sysUserRoles,input -> input.getUserId());
		
		List<SysUser> users  = new ArrayList<SysUser>();
		
		if(userIds.size() > 0){
			EntityWrapper<SysUser> ew = new EntityWrapper<SysUser>();
			ew.in("id", userIds);
			users= sysUserService.selectList(ew);
		}
		
		model.addAttribute("users",users);  
		return "system/role/users";
	}
	
	/**
	 * get user count of the role 
	 */
	@RequestMapping("/getCount")  
	@ResponseBody
	public String getCount(String roleId){
		
		int count =  sysUserRoleService.selectCount(new EntityWrapper<SysUserRole>().addFilter("roleId = {0}", roleId));
		return String.valueOf(count);
	}
	
}
