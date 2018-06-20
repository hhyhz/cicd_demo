package com.devops.controller.projs;



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
import com.devops.entity.JenkinsJob;
import com.devops.entity.Template;
import com.devops.service.ITemplateService;

@Controller
@RequestMapping("/projects/template")
public class TemplateController extends SuperController{  

	@Autowired private ITemplateService templateService;
	
	/**
	 * page query 
	 */
    @RequestMapping("/list/{pageNumber}")  
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize,String search,Model model){
		if(StringUtils.isNotBlank(search)){
			model.addAttribute("search", search);
		}
    	Page<Map<Object, Object>> page = getPage(pageNumber,pageSize);
    	model.addAttribute("pageSize", pageSize);
    	Page<Map<Object, Object>> pageData = templateService.selectTemplatePage(page, search);
    	model.addAttribute("pageData", pageData);
    	return "projects/template/list";
    } 
    /**
     *  add template page
     */
	@RequiresPermissions("addTemplate")
    @RequestMapping("/add")  
    public  String add(Model model){
		return "projects/template/add";
    } 
    
    /**
     * do add via Ajax
     */
    @Log("createtemplate")
    @RequiresPermissions("addTemplate")
    @RequestMapping("/doAdd") 
    @ResponseBody
    public  Rest doAdd(Template tpl){
    	
    	try {
			templateService.insertTemplate(tpl);
		} catch (Exception e) {
			return Rest.failure(e.getMessage());
		}
    	return Rest.ok();
    }  
    /**
     * delete via ajax
     */
    @Log("delete template via ajax")
    @RequiresPermissions("deleteTemplate")
    @RequestMapping("/delete")  
    @ResponseBody
    public  Rest delete(Integer id){
    	templateService.delete(id);
    	return Rest.ok();
    }  
    
	/**
	 * to edit page
	 */
    @RequestMapping("/edit/{id}")  
    @RequiresPermissions("editTemplate")
    public  String edit(@PathVariable String id,Model model){
    	Template tpl =templateService.selectById(id);
    	model.addAttribute("tpl", tpl);
    	return "projects/template/edit";
    } 
    /**
     * do edit via ajax
     */
    @Log("edit template")
    @RequiresPermissions("editTemplate")
    @RequestMapping("/doEdit")  
    @ResponseBody
    public  Rest doEdit(Template tpl,Model model){
    	templateService.updateTemplate(tpl);
    	return Rest.ok();
    } 
    
    /**
     * check name
     */
    @RequestMapping("/checkName")  
    @ResponseBody
    public Rest checkName(String templateName){
    	List<Template> list = templateService.selectList(new EntityWrapper<Template>().eq("templateName", templateName));
    	if(list.size() > 0){
    		return Rest.failure("模板名已存在");
    	}
    	return Rest.ok();
    }
    
}
