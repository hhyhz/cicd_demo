package com.devops.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.devops.common.anno.Log;
import com.devops.common.controller.SuperController;
import com.devops.entity.SysSetting;
import com.devops.service.ISysSettingService;
/**
 * setting ctl
 */
@Controller
@RequestMapping("/system/setting")
public class SettingController extends SuperController{  
	
	@Autowired private ISysSettingService	sysSettingService;
	
	/**
	 *  query in page
	 */
	@RequiresPermissions("listSetting")
    @RequestMapping("/page")  
    public  String page(Model model){
    	
    	List<SysSetting> list =  sysSettingService.selectList(new EntityWrapper<SysSetting>().orderBy("sort",true));
    	model.addAttribute("list",list);
		return "system/setting/page";
    } 
    
	@RequiresPermissions("doSetting")
    @Log("do edit setting via ajax")
    @RequestMapping("/doSetting")
    public String doSetting(String[] id,String[] sysValue,Model model,RedirectAttributes redirectAttributes){
    	
    	List<SysSetting> sysSettings = new ArrayList<SysSetting>();
    	if(ArrayUtils.isNotEmpty(id)){
    		for(int i=0;i<id.length;i++){
    			SysSetting setting	= new SysSetting();
        		setting.setId(id[i]);
        		setting.setSysValue(sysValue[i]);
        		sysSettings.add(setting);
    		}
    	}
    	sysSettingService.updateBatchById(sysSettings);
    	redirectAttributes.addFlashAttribute("info","OK,update success!");
		return redirectTo("/system/setting/page.html");
		
    }
    
}
