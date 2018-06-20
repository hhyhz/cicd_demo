package com.devops.controller.system;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devops.common.controller.SuperController;
/**
 * Monitor ctl
 */
@Controller
@RequestMapping("/system/monitor")
public class MonitorController extends SuperController{  
	
	/**
	 * list
	 */
	@RequiresPermissions("monitorList")
    @RequestMapping("/list")  
    public  String list(Model model){
		return "system/monitor/list";
    } 
}
