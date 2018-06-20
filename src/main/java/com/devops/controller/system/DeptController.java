package com.devops.controller.system;

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
import com.devops.entity.SysDept;
import com.devops.service.ISysDeptService;
/**
 * department Controller
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends SuperController{  

	@Autowired private ISysDeptService sysDeptService;
	
	/**
	 * page query
	 */
	@RequiresPermissions("listDept")
    @RequestMapping("/list/{pageNumber}")  
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize, String search,Model model){
    	
		Page<SysDept> page = getPage(pageNumber,pageSize);
		model.addAttribute("pageSize", pageSize);
		// 查询分页
		EntityWrapper<SysDept> ew = new EntityWrapper<SysDept>();
		if(StringUtils.isNotBlank(search)){
			ew.like("deptName",search);
			model.addAttribute("search",search);
		}
		Page<SysDept> pageData = sysDeptService.selectPage(page, ew);
		model.addAttribute("pageData", pageData);
		return "system/dept/list";
    } 
    
    /**
     * add 
     */
	@RequiresPermissions("addDept")
    @RequestMapping("/add")  
    public  String add(Model model){
		return "system/dept/add";
    } 
    
    /**
     * do add
     */
	@RequiresPermissions("addDept")
    @Log("do create dept via ajax")
    @RequestMapping("/doAdd")  
	@ResponseBody
    public  Rest doAdd(SysDept dept,String[] roleId){
    	
    	sysDeptService.insert(dept);
		return Rest.ok();
    }  
    /**
     * delete  department
     */
	@RequiresPermissions("deleteDept")
    @Log("delete department")
    @RequestMapping("/delete")  
    @ResponseBody
    public  Rest delete(String id){
    	sysDeptService.deleteById(id);
    	return Rest.ok();
    }  
    
	/**
	 * edit 
	 */
	@RequiresPermissions("editDept")
    @RequestMapping("/edit/{id}")  
    public  String edit(@PathVariable String id,Model model){
    	SysDept dept = sysDeptService.selectById(id);
    	
    	model.addAttribute("dept",dept);
    	return "system/dept/edit";
    } 
    /**
     * do edit
     */
	@RequiresPermissions("editDept")
    @Log("edit department")
    @RequestMapping("/doEdit")  
	@ResponseBody
    public  Rest doEdit(SysDept dept,Model model){
    	sysDeptService.updateById(dept);
    	return Rest.ok();
    } 
	
}
