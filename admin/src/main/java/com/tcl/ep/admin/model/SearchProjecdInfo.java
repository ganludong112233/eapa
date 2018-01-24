package com.tcl.ep.admin.model;

import com.tcl.ep.persistence.entity.Project;

/**
 * Created by panmin on 16-11-23.
 */
public class SearchProjecdInfo {
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 警告异常
     */
    private String toEmails;
    /**
     * 查询类型 :projectName,toEmails
     */
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project convertToProjcet(){
        Project project = new Project();
        project.setProjectName(this.projectName);
        project.setToEmails(this.toEmails);
        return project;
    }
}
