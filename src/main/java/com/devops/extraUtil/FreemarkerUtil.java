package com.devops.extraUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	
	public static String templatePath="e:\\dev_run\\tpl\\";
	
	
public static void main(String[] args) throws Exception{
	Map <String ,Object>data= new HashMap<String ,Object>();
	data.put("pipelineScript","123");
		FreemarkerUtil.getConfigXml(data);
	}
	    public static Template getTemplate(String name){
	    	if(name==null||name.equals(""))
	    		name="config.tpl";
	        try {
	        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
	        config.setDirectoryForTemplateLoading(new File(templatePath)); 
	        Template temp = config.getTemplate(name);
	        return temp;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	  
	    /**
	     * 
	     * @param dataModel 数据模型
	     * @param templateName 输出模版
	     */
	    public static String  getConfigXml(Map<String, Object> dataModel){
	    	 StringWriter swriter = new StringWriter();
	     try {
	    	 FreemarkerUtil.getTemplate(null).process(dataModel, swriter);
	     
	     } catch (TemplateException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }
	    String rt =swriter.toString();
	     System.out.println(rt);
	     return rt;
	    }
	    
	    
	   
}
