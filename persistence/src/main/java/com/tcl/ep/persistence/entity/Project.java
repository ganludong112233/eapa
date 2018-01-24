package com.tcl.ep.persistence.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Project {
    private Long id;

    private Long projectId;

    private String projectName;

    private String toEmails;

    private String toPhones;

    private String warnException;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getToEmails() {
        return toEmails;
    }

    public void setToEmails(String toEmails) {
        this.toEmails = toEmails;
    }

    public String getToPhones() {
        return toPhones;
    }

    public void setToPhones(String toPhones) {
        this.toPhones = toPhones;
    }

    public String getWarnException() {
        return warnException;
    }

    public void setWarnException(String warnException) {
        this.warnException = warnException;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
   	public String toString() {
   		return ReflectionToStringBuilder.toString(this);
   	}
}