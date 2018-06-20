package com.devops.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.devops.entity.JenkinsJob;

/**
 *
 */
public interface JobMapper extends BaseMapper<JenkinsJob> {

	List<Map<Object, Object>> selectJobList(Page<Map<Object, Object>> page, Map<String ,Object> map);
	
}