package com.devops.extraUtil;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devops.config.PathConstant;



/** 
 * @Description 

 */
public class HttpClientUtil {
    
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    
    public  static String token=PathConstant.TOKEN_GITEE;
    public static void main(String[] args) throws Exception{
    
	
    	HttpClientUtil.createGiteeRepo("123dsf");

	}
    
    public static void  createGiteeRepo(String repoName) throws Exception{
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", repoName));
			params.add(new BasicNameValuePair("access_token", HttpClientUtil.token));
			hc.executeByPOST(PathConstant.API_CREATE_REPO, params);
    	}catch (Exception e) {
    		Exception e1= new  Exception ("gitee repo create error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw e1;
    	}
    }
    
    public static void  deleteGiteeRepo(String repoName) throws Exception{
    	try {
	    	HttpClientUtil hc=new HttpClientUtil();
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", repoName));
			params.add(new BasicNameValuePair("owner", "szjack"));
			params.add(new BasicNameValuePair("access_token", HttpClientUtil.token));
			hc.executeByDelete(PathConstant.API_DELETE_REPO+repoName, params);
    	}catch (Exception e) {
    		Exception e1= new  Exception ("gitee repo delete error:"+e.getMessage());
    		e1.setStackTrace(e.getStackTrace());
    		throw e1;
    	}
    }
    /**
     * 初始化HttpClient
     */
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    
    /**
     * POST方式调用
     * 
     * @param url
     * @param params 参数为NameValuePair键值对对象
     * @return 响应字符串
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public void executeByPOST(String url, List<NameValuePair> params) throws Exception {
        HttpPost post = new HttpPost(url);
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36"; 
        post.setHeader("User-Agent", userAgent);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
                post.setEntity(new UrlEncodedFormEntity(params));
            }
        
            responseJson = httpClient.execute(post, responseHandler);
            
            System.out.println("HttpClient POST请求结果：" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            System.out.println("HttpClient POST请求异常：" + e.getMessage());
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            httpClient.close();
        }
        return ;
    }
    
    /**
     * Get方式请求
     * 
     * @param url 带参数占位符的URL，例：http://ip/User/user/center.aspx?_action=GetSimpleUserInfo&codes={0}&email={1}
     * @param params 参数值数组，需要与url中占位符顺序对应
     * @return 响应字符串
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public String executeByGET(String url, Object[] params) throws Exception {
        String messages = MessageFormat.format(url, params);
        HttpGet get = new HttpGet(messages);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET请求结果：" + responseJson);
            
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
            throw e;
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
            throw e;
        }
        finally {
            httpClient.close();
        }
        return responseJson;
    }
    
    
   
    /**
     * @param url
     * @return
     * @throws Exception 
     */
    public String executeByGET(String url) throws Exception {
        HttpGet get = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            responseJson = httpClient.execute(get, responseHandler);
            log.info("HttpClient GET请求结果：" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("HttpClient GET请求异常：" + e.getMessage());
        }
        finally {
            httpClient.close();
        }
        return responseJson;
    }
    
 
    /**
     * delete方式调用
     * 
     * @param url
     * @param params 参数为NameValuePair键值对对象
     * @return 响应字符串
     * @throws Exception 
     * @throws java.io.UnsupportedEncodingException
     */
    public void executeByDelete(String url, List<NameValuePair> params) throws Exception {
    	HttpDeleteWithBody httpdelete = new HttpDeleteWithBody(url);  
        String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36"; 
        httpdelete.setHeader("User-Agent", userAgent);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseJson = null;
        try {
            if (params != null) {
            	httpdelete.setEntity(new UrlEncodedFormEntity(params));
            }
        
            responseJson = httpClient.execute(httpdelete, responseHandler);
            
            System.out.println("HttpClient POST请求结果：" + responseJson);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        }
        catch (IOException e) {
           throw e;
        }
        finally {
            httpClient.close();
        }
        return ;
    }
    
    class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";
        public String getMethod() { return METHOD_NAME; }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }
        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }
        public HttpDeleteWithBody() { super(); }
    }

}
