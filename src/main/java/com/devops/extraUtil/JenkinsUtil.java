package com.devops.extraUtil;

import java.net.URI;
import java.net.URISyntaxException;

import com.devops.config.PathConstant;
import com.surenpi.jenkins.client.Jenkins;
/**
 * 
 * jenkins api
 */
import com.surenpi.jenkins.client.job.Jobs;
public class JenkinsUtil {
	public static Jenkins server;
	
	public static Jenkins getInstance() {
		if(server==null) {
			try {
				server = new Jenkins(
				        new URI(PathConstant.JENKINS_SERVER_PATH),
				        PathConstant.JENKINS_SERVER_USER,
				        PathConstant.JENKINS_SERVER_PASSWD);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server ;
	}
	public static void main(String[] args) throws Exception {
		Jobs  jbs=new Jobs();
		jbs.create("text", "");
	}
}
