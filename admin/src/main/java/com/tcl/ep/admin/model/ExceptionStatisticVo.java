package com.tcl.ep.admin.model;

import com.tcl.ep.persistence.vo.ExceptionModuleStatisticDto;

import java.util.List;

/**
 * Created by panmin on 16-11-24.
 */
public class ExceptionStatisticVo {
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目对应的各模块异常统计列表
     */
    private List<ExceptionModuleStatisticDto> exceptModStatisticList;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<ExceptionModuleStatisticDto> getExceptModStatisticList() {
        return exceptModStatisticList;
    }

    public void setExceptModStatisticList(List<ExceptionModuleStatisticDto> exceptModStatisticList) {
        this.exceptModStatisticList = exceptModStatisticList;
    }
}
