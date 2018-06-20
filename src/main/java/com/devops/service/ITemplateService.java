package com.devops.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.Template;

/**
 *
 */
public interface ITemplateService extends IService<Template> {
	
	
	public void insertTemplate(Template tpl)throws Exception ;
	public void updateTemplate(Template tpl) ;
	void delete(Integer id);
	Page<Map<Object, Object>> selectTemplatePage(Page<Map<Object, Object>> page, String search);
}