package com.devops.extraUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.devops.config.PathConstant;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.surenpi.jenkins.client.Jenkins;
import com.surenpi.jenkins.client.core.JenkinsInfo;
import com.surenpi.jenkins.client.job.BuildDetail;
import com.surenpi.jenkins.client.job.Job;
import com.surenpi.jenkins.client.job.JobDetails;
import com.surenpi.jenkins.client.job.Jobs;

public class JobUtil {
    private static Jobs jobs;

    public static void main(String[] args) throws Exception{
    	JobUtil.getBuildDetails("atddd",41);
    	//JobUtil.getLogTexts("atddd", 41);
	}
    
    public static void init() throws URISyntaxException
    {
        jobs =  new Jenkins(
				        new URI(PathConstant.JENKINS_SERVER_PATH),
				        PathConstant.JENKINS_SERVER_USER,
				        PathConstant.JENKINS_SERVER_PASSWD).getJobs();
    }
    

    public void list() throws IOException
    {
        List<Job> list = jobs.getAllJobs();
       
    }
    public static void getLogTexts(String name ,Integer buildNum)throws Exception {
    	if(jobs==null) JobUtil.init();
       String logs=	jobs.getLogText(name, buildNum);
       
       System.out.println(logs);
    }
    public static void  batchDel(String name) throws Exception
    {
    	if(jobs==null) JobUtil.init();
    	try {
        jobs.batchDel(name);
    	}catch(Exception e) {
    		Exception e1= new  Exception ("batch delete jenkins job error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw  e1;
    	}
    }
    public  static void createByXml(String name,String xml) throws Exception
    {
    	if(jobs==null) JobUtil.init();
    	try {
        jobs.create(name, xml);
    	}catch(Exception e) {
    		Exception e1=new Exception ("create jenkins job error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw  e1;
    	}

    }

    public static void create(String name) throws Exception
    {
    	if(jobs==null) JobUtil.init();
        jobs.create(name, JobUtil.getXml(name));
    }

    @Async
    public static void  build(String name) throws Exception
    {
    	try {
    	if(jobs==null) JobUtil.init();
        jobs.build(name);
    	}catch(Exception e) {
    		e.printStackTrace();
    		
    	}
    }
    public void getLogText(String name) throws IOException
    {
        jobs.getLogText(name, 1);
    }

    
    public void stop(String name) throws IOException
    {
        jobs.stop(name, 21);
    }

    
    public void buildWithParams() throws IOException
    {
        Map<String, String> params = Collections.singletonMap("a", "a");
        jobs.buildWithParams("free", params);
    }

    
   

    
    public void jenkinsInfo() throws IOException
    {
        JenkinsInfo info = jobs.getAll();
    }

    
    public void getDetails() throws IOException
    {
        JobDetails details = jobs.getDetails("common-devops-server");
    }

    public void getNotExistsJob() throws IOException
    {
        jobs.getDetails(String.valueOf(System.currentTimeMillis()));
    }

    
    public static void getBuildDetails(String name,Integer buildNum) throws Exception
    {
    	if(jobs==null) JobUtil.init();
        BuildDetail buildDetails = jobs.getBuildDetails(name, buildNum);
        System.out.println(buildDetails);
    }

    
    public static void getLastBuildDetails(String name) throws Exception
    {
    	if(jobs==null) JobUtil.init();
        BuildDetail buildDetails = jobs.getLastBuildDetails(name);
        System.out.println(buildDetails.toString());
    }

    
    public static String getXml(String jobName) throws Exception
    {
    	if(jobs==null) JobUtil.init();
      return jobs.getXml(jobName);
    }

    public static final String JOB_XML = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<project>\n" +
            "  <description></description>\n" +
            "  <keepDependencies>false</keepDependencies>\n" +
            "  <properties/>\n" +
            "<definition class=\"org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition\" plugin=\"workflow-cps@2.49\">"+
            "<script></script></definition>"+
            "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
            "  <canRoam>true</canRoam>\n" +
            "  <disabled>false</disabled>\n" +
            "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
            "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
            "  <triggers/>\n" +
            "  <concurrentBuild>false</concurrentBuild>\n" +
            "  <builders/>\n" +
            "  <publishers/>\n" +
            "  <buildWrappers/>\n" +
            "</project>\n";
}