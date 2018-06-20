package com.devops.extraUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import com.devops.config.PathConstant;
import com.surenpi.jenkins.client.Jenkins;
import com.surenpi.jenkins.client.workflow.Stage;
import com.surenpi.jenkins.client.workflow.WfWithDetails;
import com.surenpi.jenkins.client.workflow.Workflows;

/**
 */
public class WorkFlowUtil
{
    private static Workflows workflows;
    private final String jobName = "hello";

    public static void main(String[] args) throws Exception{
    	WorkFlowUtil.last("atddd");
	}
      
    public static void init() throws URISyntaxException
    { 
        workflows =new Jenkins(
		        new URI(PathConstant.JENKINS_SERVER_PATH),
		        PathConstant.JENKINS_SERVER_USER,
		        PathConstant.JENKINS_SERVER_PASSWD).getWorkflows();
    }
  
    public void create() throws IOException
    {
        WfWithDetails wfDesc = workflows.getWfDescribe("common-1", 1);
        System.out.println(wfDesc);

        Stage a = workflows.getWfNodeDescribe("common-1", 1, 6);
        System.out.println(a);
    }

    public static  String last(String name) throws Exception
    {
    	if(workflows==null) WorkFlowUtil.init();
        WfWithDetails desc = workflows.last(name);

       return desc.toString();
    }

    public void getWfDescribe() throws IOException
    {
        WfWithDetails desc = workflows.getWfDescribe("common", 233);
        System.out.println(desc);
    }
}