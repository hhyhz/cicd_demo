package com.devops.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.devops.config.PathConstant;
import com.devops.entity.Template;
import com.devops.extraUtil.GitUtil;
import com.devops.extraUtil.HttpClientUtil;
import com.devops.extraUtil.JenkinsUtil;
import com.devops.mapper.TemplateMapper;
import com.devops.service.ITemplateService;
import com.surenpi.jenkins.client.Jenkins;
import com.surenpi.jenkins.client.job.Jobs;

/**
 *
 * job
 *
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements ITemplateService {
	protected final Logger       logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired private TemplateMapper TemplateMapper;
	
	@Override
	@Transactional
	public void insertTemplate(Template tpl) throws Exception{
		// TODO Auto-generated method stub
		tpl.setCreated(new Date());
		
		
		if(tpl.getGitUrl()==null ||tpl.getGitUrl().isEmpty()) {
			HttpClientUtil.createGiteeRepo(tpl.getTemplateName());
			tpl.setGitUrl(PathConstant.GIT_URI+tpl.getTemplateName());
		}
		
		TemplateMapper.insert(tpl);
		GitUtil.gitClone(tpl.getTemplateName());
		
	}

	@Override
	public void updateTemplate(Template tpl) {
		// TODO Auto-generated method stub
		tpl.setUpdated(new Date());
		TemplateMapper.updateById(tpl);
		 
	}

	@Override
	public Page<Map<Object, Object>> selectTemplatePage(Page<Map<Object, Object>> page, String search) {
		// TODO Auto-generated method stub
		page.setRecords(baseMapper.selectTemplateList(page, search));
//		try {
//			Jobs jobs =JenkinsUtil.getInstance().getJobs();
//			 List<Job> list = jobs.getAllJobs();
//		        for(Job job : list)
//		        {
//		        	logger.error(job.getName());
//		        }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return page;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		this.deleteById(id);
		TemplateMapper.delete(new EntityWrapper<Template>().addFilter("id = {0}", id));
	}


}