package com.devops.service;

import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.JenkinsJob;
import com.devops.entity.Template;

/**
 *
 * SysUser 
 *
 */
public interface IJobService extends IService<JenkinsJob> {
	
	
	public void insertJob(JenkinsJob job,Template tpl)throws Exception ;
	public void updateJob(JenkinsJob job) ;
	void delete(Integer id,String jobName)throws Exception;
	void build(JenkinsJob job)throws Exception;
	public void doBuild(JenkinsJob job);
	Page<Map<Object, Object>> selectJobList(Page<Map<Object, Object>> page, Map<String ,Object> map);
}