package com.devops.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.devops.common.controller.SuperController;
import com.devops.entity.SysUser;
import com.devops.service.ISysLogService;
import com.google.code.kaptcha.servlet.KaptchaExtend;
/**
 * login ctl
 */
@Controller
@RequestMapping("/login")
public class LoginController extends SuperController{  
	/**
	 * log service
	 */
	@Autowired private ISysLogService sysLogService;
	
	/**
	 * to login page
	 */
	@RequestMapping
	public String login(Model model){
		return "login";
	}
	
	/**
	 * do login 
	 */
    @RequestMapping(value = "/doLogin",method=RequestMethod.POST)  
    public  String doLogin(String userName,String password, String captcha,String return_url,RedirectAttributesModelMap model){
		
    	Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		
		 if (!currentUser.isAuthenticated()) {
	          // token.setRememberMe(true);
	            try {
	                currentUser.login(token);
	            } catch (UnknownAccountException uae) {
	            	
	            	model.addFlashAttribute("error", "unknow user");
	            	return redirectTo("/login");
	            } catch (IncorrectCredentialsException ice) {
	            	model.addFlashAttribute("error", "password error");
	            	return redirectTo("/login");
	            } catch (LockedAccountException lae) {
	            	model.addFlashAttribute("error", "account locked");
	            	return redirectTo("/login");
	            }
	            catch (AuthenticationException ae) {
	                //unexpected condition?  error?
	            	model.addFlashAttribute("error", "server error");
	            	return redirectTo("/login");
	            }
	        }
		/**
		 * logging 
		 */
		 Subject subject = SecurityUtils.getSubject();
		 SysUser sysUser = (SysUser) subject.getPrincipal();
		 sysLogService.insertLog("login success",sysUser.getUserName(),request.getRequestURI(),"");
		 return redirectTo("/");
    }  
	
    /**
     * validate code
     */
    @RequestMapping("/captcha")
	@ResponseBody
    public  void captcha() throws ServletException, IOException{
		KaptchaExtend kaptchaExtend =  new KaptchaExtend();
		kaptchaExtend.captcha(request, response);
    }
}
