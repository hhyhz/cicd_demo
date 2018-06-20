package com.devops.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.devops.common.anno.Log;
import com.devops.common.util.ShiroUtil;
import com.devops.common.util.SpringUtil;
import com.devops.entity.SysLog;
import com.devops.entity.SysUser;
import com.devops.service.ISysLogService;
import com.google.gson.Gson;
/**
 * logging via aop
 * @author Administrator
 *
 */
@Aspect
@Component
public class LogAdvice {
	
	public static final Logger LOG = Logger.getLogger(LogAdvice.class);
	
	@Pointcut("@annotation(com.devops.common.anno.Log)")
	public void controllerAspect() {
		
	}
	/**
	 * 
	 * @param joinPoint
	 */
	@AfterReturning("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Log log =  method.getAnnotation(Log.class);
		SysUser sysUser = ShiroUtil.getSessionUser();
		if(log != null){
			SysLog sysLog  =new SysLog();
			sysLog.setCreateTime(new Date());
			sysLog.setTitle(log.value());
			sysLog.setUserName((sysUser != null )? sysUser.getUserName() : "system");
			sysLog.setUrl(request.getRequestURI().toString());
			sysLog.setParams(new Gson().toJson(request.getParameterMap()));
			SpringUtil.getBean(ISysLogService.class).insert(sysLog);
			LOG.debug("logging:"+sysLog.toString());
		}
	}
}
