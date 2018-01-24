package com.tcl.ep.persistence.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ExceptionSearchReq {
	
	private Long projectId;
	private String exceptionName;
	private Long startDate;
	private Long endDate;
	private String module;
	private String env;
	private String errorMsg;
	private String ip;
	private Integer pageNo = 1; // 默认为第一页
    private Integer pageSize = 30; // 默认每页30条
    public Integer getStartItem() {
        if (pageNo <= 1) {
            return 0;
        }
        return (pageNo - 1) * pageSize;
    }
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getStartDate() {
		return startDate;
	}
	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}
	public Long getEndDate() {
		return endDate;
	}
	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getExceptionName() {
		return exceptionName;
	}
	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
