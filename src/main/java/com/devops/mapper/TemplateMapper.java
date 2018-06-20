package com.devops.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.devops.entity.Template;

/**
 *
 */
public interface TemplateMapper extends BaseMapper<Template> {

	List<Map<Object, Object>> selectTemplateList(Page<Map<Object, Object>> page, @Param("search") String search);
}