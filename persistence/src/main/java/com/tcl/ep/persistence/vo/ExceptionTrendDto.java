package com.tcl.ep.persistence.vo;

/**
 * Created by panmin on 16-11-25.
 */
public class ExceptionTrendDto {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 发生异常日期
     */
    private String date;

    /**
     * 异常总数
     */
    private Integer testExceptionDayTotal;

    /**
     * 异常总数
     */
    private Integer proExceptionDayTotal;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTestExceptionDayTotal() {
        return testExceptionDayTotal;
    }

    public void setTestExceptionDayTotal(Integer testExceptionDayTotal) {
        this.testExceptionDayTotal = testExceptionDayTotal;
    }

    public Integer getProExceptionDayTotal() {
        return proExceptionDayTotal;
    }

    public void setProExceptionDayTotal(Integer proExceptionDayTotal) {
        this.proExceptionDayTotal = proExceptionDayTotal;
    }
}
