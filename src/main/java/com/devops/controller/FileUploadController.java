package com.devops.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.devops.common.controller.SuperController;

/**
 * file upload
 */

@Controller
public class FileUploadController extends SuperController{
	
	public static final Logger logger = Logger.getLogger(FileUploadController.class);
	
	/**
	 * upload 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/file/upload")
	public Map<String, Object> fileUpload( @RequestParam MultipartFile[] file) throws IOException{
		
		List<String> urls = new ArrayList<String>();
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			for(MultipartFile myfile : file){  
			        if(myfile.isEmpty()){  
			        	logger.warn("file not uploaded");  
			        }else{  
			            logger.debug("size: " + myfile.getSize());  
			            logger.debug("type: " + myfile.getContentType());  
			            logger.debug("filename: " + myfile.getName());  
			            logger.debug("originName: " + myfile.getOriginalFilename());  
			            String ext =  FilenameUtils.getExtension(myfile.getOriginalFilename());
			            String reName = RandomStringUtils.randomAlphanumeric(32).toLowerCase() + "."+ ext;
			            String cdate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
			            String realPath = request.getSession().getServletContext().getRealPath("/upload")+ File.separator +cdate; 
			            FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, reName)); 
			            urls.add("/upload/"+cdate+"/"+reName);
			        }  
			    }
			result.put("status", "success");
			result.put("urls",urls);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("status", "error");
			return result;
		}  
	}
}
