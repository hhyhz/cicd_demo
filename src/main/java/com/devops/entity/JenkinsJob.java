package com.devops.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.devops.config.PathConstant;

@TableName("projects_jobs")
public class JenkinsJob extends Model<JenkinsJob> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4158440120924087219L;
	@TableId(type=IdType.AUTO)
	private Integer id;

    private String jobName;

    private String desc;
    
    private Integer tplId;
    
    private String gitUrl;
    
    private Date created;

    private Date updated;
    private Date lastBuildDate;
    private Integer jobStatus;

    private String pipelineScript;

    public Integer buildNum;
    public Integer curBuildNum;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getPipelineScript() {
        return pipelineScript;
    }

    public void setPipelineScript(String pipelineScript) {
        this.pipelineScript = pipelineScript == null ? null : pipelineScript.trim();
    }
    
    public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getGitUrl() {
		return gitUrl;
	}

	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}

	
	
	public Integer getTplId() {
		return tplId;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}
	


	public Integer getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(Integer buildNum) {
		this.buildNum = buildNum;
	}

	public Integer getCurBuildNum() {
		return curBuildNum;
	}

	public void setCurBuildNum(Integer curBuildNum) {
		this.curBuildNum = curBuildNum;
	}

	public Date getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(Date lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}