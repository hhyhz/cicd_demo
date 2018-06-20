package com.devops.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("code_template")
public class Template extends Model<Template> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8108859320047356730L;

	@TableId(type=IdType.AUTO)
	private Integer id;

    private String templateName;
    
    private String gitUrl;
    
    private Date created;

    private Date updated;

    private String desc;
    private String pipelineScript;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}