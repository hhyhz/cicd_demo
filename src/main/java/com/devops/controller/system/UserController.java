package com.devops.controller.system;

import java.util.List;
import java.util.Map;

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
import com.devops.entity.SysUser;
import com.devops.entity.SysUserRole;
import com.devops.service.ISysDeptService;
import com.devops.service.ISysRoleService;
import com.devops.service.ISysUserRoleService;
import com.devops.service.ISysUserService;
import com.google.common.collect.Lists;
/**
 * user ctl
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends SuperController{  

	@Autowired private ISysUserService sysUserService;
	@Autowired private ISysRoleService sysRoleService;
	@Autowired private ISysUserRoleService sysUserRoleService;
	@Autowired private ISysDeptService sysDeptService;
	
	/**
	 * query in page
	 */
	@RequiresPermissions("listUser")
    @RequestMapping("/list/{pageNumber}")  
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize,String search,Model model){
		if(StringUtils.isNotBlank(search)){
			model.addAttribute("search", search);
		}
    	Page<Map<Object, Object>> page = getPage(pageNumber,pageSize);
    	model.addAttribute("pageSize", pageSize);
    	Page<Map<Object, Object>> pageData = sysUserService.selectUserPage(page, search);
    	model.addAttribute("pageData", pageData);
    	return "system/user/list";
    } 
    /**
     *  to add page
     */
	@RequiresPermissions("addUser")
    @RequestMapping("/add")  
    public  String add(Model model){
    	model.addAttribute("roleList", sysRoleService.selectList(null));
    	model.addAttribute("deptList", sysDeptService.selectList(null));
		return "system/user/add";
    } 
    
    /**
     * do add user
     */
    @Log("add user via ajax")
    @RequiresPermissions("addUser")
    @RequestMapping("/doAdd")  
    @ResponseBody
    public  Rest doAdd(SysUser user,@RequestParam(value="roleId[]",required=false) String[] roleId){
    	
    	sysUserService.insertUser(user,roleId);
    	return Rest.ok();
    }  
    /**
     */
    @Log("delete user via ajax")
    @RequiresPermissions("deleteUser")
    @RequestMapping("/delete")  
    @ResponseBody
    public  Rest delete(String id){
    	sysUserService.delete(id);
    	return Rest.ok();
    }  
    
	/**
	 * edit user
	 */
    @RequestMapping("/edit/{id}")  
    @RequiresPermissions("editUser")
    public  String edit(@PathVariable String id,Model model){
    	SysUser sysUser = sysUserService.selectById(id);
    	
    	List<SysRole> sysRoles = sysRoleService.selectList(null);
    	EntityWrapper<SysUserRole> ew = new EntityWrapper<SysUserRole>();
    	ew.eq("userId ", id);
    	List<SysUserRole> mySysUserRoles = sysUserRoleService.selectList(ew);
    	List<String> myRolds = Lists.transform(mySysUserRoles, input -> input.getRoleId());
    	
    	model.addAttribute("sysUser",sysUser);
    	model.addAttribute("sysRoles",sysRoles);
    	model.addAttribute("myRolds",myRolds);
    	model.addAttribute("deptList", sysDeptService.selectList(null));
    	return "system/user/edit";
    } 
    /**
     * do edit
     */
    @Log("do edit via ajax")
    @RequiresPermissions("editUser")
    @RequestMapping("/doEdit")  
    @ResponseBody
    public  Rest doEdit(SysUser sysUser,@RequestParam(value="roleId[]",required=false) String[] roleId,Model model){
    	sysUserService.updateUser(sysUser,roleId);
    	return Rest.ok();
    } 
    
    /**
     * check name
     */
    @RequestMapping("/checkName")  
    @ResponseBody
    public Rest checkName(String userName){
    	List<SysUser> list = sysUserService.selectList(new EntityWrapper<SysUser>().eq("userName", userName));
    	if(list.size() > 0){
    		return Rest.failure("the name already exists");
    	}
    	return Rest.ok();
    }
    
}
