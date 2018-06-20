package com.devops.extraUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.transport.SshTransport;

import com.devops.config.PathConstant;
import com.jcraft.jsch.Session;
/**
 * jgit api 
 *
 */
public class GitUtil {
	  public static void main(String[] args)throws Exception {
		  GitUtil.cloneAndPush( "zuul-demo", "newojb");
	}  
	  /**
	   * clone and push
	   */
	  public static Boolean cloneAndPush(String oldName,String repoName) throws Exception{
		 GitUtil.gitClone(oldName);
		  GitUtil.gitPushOtherRemote(oldName, repoName);
		  return true;
	  }
	 
	  /**
	   * 克隆url 到本地目录
	   */
	  public static Boolean gitClone(String repoName) throws Exception{
		   CloneCommand cc = Git.cloneRepository().setURI(PathConstant.GIT_URI+repoName+".git");
		   cc.setDirectory(new File(PathConstant.GIT_LOCAL_PATH_BASE+repoName)).call();
	
		  return true;
	  }
	 /** 
	  * 推送本地代码到远程地址库
	  */
	  public static Boolean gitPushOtherRemote (String oldRepoName ,String repoName )throws Exception {
		  org.eclipse.jgit.transport.SshSessionFactory sshSessionFactory = new org.eclipse.jgit.transport.JschConfigSessionFactory() {
			
			@Override
			protected void configure(Host hc, Session session) {
				// TODO Auto-generated method stub
				 session.setConfig("StrictHostKeyChecking", PathConstant.GIT_SSH_PATH);
			}
			};
		  FileRepositoryBuilder builder = new FileRepositoryBuilder();
		  
			Repository repository = builder.setGitDir(new File(PathConstant.GIT_LOCAL_PATH_BASE+oldRepoName+"\\.git"))
			  .readEnvironment() // scan environment GIT_* variables
			  .findGitDir() // scan up the file system tree
			  .build();
			Git git = new Git(repository);
			PushCommand ps=git.push();
			//ps.setCredentialsProvider( new UsernamePasswordCredentialsProvider( "@163.com", "" ) );
			//ps.setCredentialsProvider( new UsernamePasswordCredentialsProvider( "token", "Gwh7LiFSY23qeiWJyesh" ) );
			//
			ps.setRemote(PathConstant.GIT_GITEE_AUTHOR_USER+repoName+".git");
			//ps.setRemote("https://gitee.com/szbrad/fffff.git");
			ps.setTransportConfigCallback( new org.eclipse.jgit.api.TransportConfigCallback() {
				  @Override
				  public void configure( org.eclipse.jgit.transport.Transport transport ) {
					   SshTransport sshTransport = ( SshTransport )transport;

				    sshTransport.setSshSessionFactory( sshSessionFactory );
				  }
				} );
			ps.call();
			git.close();
		  return true;
	  }
	 
	    /**
	     * 新建一个分支并同步到远程仓库
	     * @param branchName
	     * @throws IOException
	     * @throws GitAPIException
	     */
	    public static String newBranch(String branchName,String gitUri){
	        String newBranchIndex = "refs/heads/"+branchName;
	        String gitPathURI = "";
	        Git git;
	        
	        try {
	        	git= Git.cloneRepository()
	        	  .setURI(PathConstant.GIT_AUTHOR_PRE +gitUri) //"https://github.com/eclipse/jgit.git" )
	        	  .call();    
	            //检查新建的分支是否已经存在，如果存在则将已存在的分支强制删除并新建一个分支
	            List<Ref> refs = git.branchList().call();
	            for (Ref ref : refs) {
	                if (ref.getName().equals(newBranchIndex)) {
	                    System.out.println("Removing branch before");
	                    git.branchDelete().setBranchNames(branchName).setForce(true)
	                            .call();
	                    break;
	                }
	            }            
	            //新建分支
	            Ref ref = git.branchCreate().setName(branchName).call();
	            //推送到远程
	            git.push().add(ref).call();
	            gitPathURI = PathConstant.GIT_AUTHOR_PRE + " " + "feature/" + branchName;
	        }catch (GitAPIException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        
	        return gitPathURI;                
	    }
	    
	
	    
	   
	    
	    public static void deleteFolder(File file){
	        if(file.isFile() || file.list().length==0){
	            file.delete();
	        }else{
	            File[] files = file.listFiles();
	            for(int i=0;i<files.length;i++){
	                deleteFolder(files[i]);
	                files[i].delete();
	            }
	        }
	    }
	    
	 
}
