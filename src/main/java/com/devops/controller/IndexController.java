package com.devops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * index ctl
* @ClassName: IndexController
*
 */
@Controller
@RequestMapping("/")
public class IndexController {  
	
    @RequestMapping({"","/","index"})  
    public  String index(Model model){
		return "index";
    }  
}
