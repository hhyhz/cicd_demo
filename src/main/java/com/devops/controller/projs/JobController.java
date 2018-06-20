package com.devops.controller.projs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.devops.extraUtil.WorkFlowUtil;
import com.devops.service.IJobService;
import com.devops.service.ITemplateService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/projects/job")
public class JobController extends SuperController{  
	
	

	@Autowired private IJobService jobService;
	@Autowired private ITemplateService templateService;
	/**
	 * page query
	 */
    @RequestMapping("/list/{pageNumber}")  
    public  String list(@PathVariable Integer pageNumber,@RequestParam(defaultValue="15") Integer pageSize,String search,String daterange,Model model)throws Exception{
    	
    	Map <String ,Object > searchMap=new HashMap <String ,Object>();
    	if(StringUtils.isNotBlank(search)){
    		searchMap.put("search", search);
    		model.addAttribute("search", search);
			
		}
		//date query
				if(StringUtils.isNotBlank(daterange)){
					model.addAttribute("daterange", daterange);
					SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
					String[] dateranges = StringUtils.split(daterange, "-");
				    Date start=	sDateFormat.parse(dateranges[0].trim().replaceAll("/","-") );
				    Date end=	sDateFormat.parse(dateranges[1].trim().replaceAll("/","-") );
				    searchMap.put("start", start);
				    searchMap.put("end", end);
				}
		Page<Map<Object, Object>> page = getPage(pageNumber,pageSize);
    	model.addAttribute("pageSize", pageSize);
    	Page<Map<Object, Object>> pageData = jobService.selectJobList(page, searchMap);
    	model.addAttribute("pageData", pageData);
    	return "projects/jobs/list";
    }
    @Log("build... jenkinsjob")
    @RequiresPermissions("buildJob")
    @RequestMapping("/build/{id}")  
    @ResponseBody
    public  Rest build( @PathVariable Integer id){
		JenkinsJob job=jobService.selectById(id);
    	try {
			jobService.build(job);
			jobService.doBuild(job);
		} catch (Exception e) {
			return Rest.failure(e.getMessage());
		}
    	return Rest.ok();
    } 
    
    
    /**
     * add 
     */
	@RequiresPermissions("addJob")
    @RequestMapping("/add")  
    public  String add(Model model){
		
		model.addAttribute("tplList", templateService.selectList(null));
		return "projects/jobs/add";
    } 
    
    /**
     *  do add via Ajax
     */
    @Log("create jenkinsjob")
    @RequiresPermissions("addJob")
    @RequestMapping("/doAdd")  
    @ResponseBody
    public  Rest doAdd(JenkinsJob job){
    	Template tpl=templateService.selectById(job.getTplId());
    	try {
			jobService.insertJob(job,tpl);
		} catch (Exception e) {
			return Rest.failure(e.getMessage());
		}
    	return Rest.ok();
    }  
    /**
     * delete job
     */
    @Log("delte jenkinsjob")
    @RequiresPermissions("deleteJob")
    @RequestMapping("/delete")  
    @ResponseBody
    public  Rest delete(Integer id){
    	try {
    		JenkinsJob job =jobService.selectById(id);
			jobService.delete(id,job.getJobName());
		} catch (Exception e) {
			 Rest.failure(e.getMessage());
		}
    	return Rest.ok();
    }  
    
	/**
	 * edit job
	 */
    @RequestMapping("/edit/{id}")  
    @RequiresPermissions("editJob")
    public  String edit(@PathVariable String id,Model model){
    
    	JenkinsJob job =jobService.selectById(id);
    	model.addAttribute("job", job);
    	return "projects/jobs/edit";
    } 
    /**
     * do edit
     */
    @Log("edit jenkinsjob")
    @RequiresPermissions("editJob")
    @RequestMapping("/doEdit")  
    @ResponseBody
    public  Rest doEdit(JenkinsJob job,Model model){
    	jobService.updateJob(job);
    	return Rest.ok();
    } 
    
    /**
     *  check name
     */
    @RequestMapping("/checkName")  
    @ResponseBody
    public Rest checkName(String jobName){
    	List<JenkinsJob> list = jobService.selectList(new EntityWrapper<JenkinsJob>().eq("jobName", jobName));
    	if(list.size() > 0){
    		return Rest.failure("project exist!");
    	}
    	return Rest.ok();
    }
    
    @RequestMapping("/buildInfo/{id}")  
    @ResponseBody
    public  String buildInfo(@PathVariable Integer id)throws Exception{
    	JenkinsJob job =jobService.selectById(id);
    	return WorkFlowUtil.last(job.getJobName());
    	
    } 
    /**
     * get pipeline script
     */
    @RequestMapping("/json")
    @ResponseBody
    public Rest json(Integer id){
    	EntityWrapper<Template> ew = new EntityWrapper<Template>();
    	ew.addFilter("id = {0} ", id);
    	List<Template> list = templateService.selectList(ew);
    	
    	List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
    	for(Template m : list){
    		Map<String, Object> map = Maps.newHashMap();
    		map.put("id", m.getId());
    		map.put("script",m.getPipelineScript());
    		listMap.add(map);
    	}
    	return Rest.okData(listMap);
    } 
    
}
