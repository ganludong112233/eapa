package com.tcl.ep.persistence.vo;

/**
 * Created by panmin on 16-11-24.
 */
public class ExceptionModuleStatisticDto {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 模块名称
     */
    private String module;

    /**
     * 测试环境异常总数
     */
    private Integer testExceptionTotal;

    /**
     * 正式环境异常总数
     */
    private Integer proExceptionTotal;

    /**
     * 异常总数
     */
    private Integer exceptionTotal;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getTestExceptionTotal() {
        return testExceptionTotal;
    }

    public void setTestExceptionTotal(Integer testExceptionTotal) {
        this.testExceptionTotal = testExceptionTotal;
    }

    public Integer getProExceptionTotal() {
        return proExceptionTotal;
    }

    public void setProExceptionTotal(Integer proExceptionTotal) {
        this.proExceptionTotal = proExceptionTotal;
    }

    public Integer getExceptionTotal() {
        return exceptionTotal;
    }

    public void setExceptionTotal(Integer exceptionTotal) {
        this.exceptionTotal = exceptionTotal;
    }
}
