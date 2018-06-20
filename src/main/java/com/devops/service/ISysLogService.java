package com.devops.service;

import com.baomidou.mybatisplus.service.IService;
import com.devops.entity.SysLog;

/**
 *
 * SysLog 
 *
 */
public interface ISysLogService extends IService<SysLog> {

	
	void insertLog(String title,String uname,String url,String parms);


}