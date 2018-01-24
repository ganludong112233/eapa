package com.tcl.ep.biz.vo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;

import com.tcl.ep.persistence.entity.Project;

/**
 * 项目信息参数
 */
public class ProjectInfoVo {

	private String projectId;
	private String projectName;
	private String toEmails;
	private String toPhones;
	private String warnException;

	public String getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getToEmails() {
		return toEmails;
	}

	public String getToPhones() {
		return toPhones;
	}

	public String getWarnException() {
		return warnException;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setToEmails(String toEmails) {
		this.toEmails = toEmails;
	}

	public void setToPhones(String toPhones) {
		this.toPhones = toPhones;
	}

	public void setWarnException(String warnException) {
		this.warnException = warnException;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Project voConvertToModel() {
		Project project=new Project();
		BeanUtils.copyProperties(this, project);
		if (StringUtils.isNotBlank(this.getProjectId())) {			
			project.setProjectId(Long.valueOf(this.getProjectId()));
		}
		return project;

	}
	public ProjectInfoVo ModelConvertToVo(Project project) {
		BeanUtils.copyProperties(project, this);
		this.setProjectId(String.valueOf(project.getProjectId()));
		return this;

	}
}
