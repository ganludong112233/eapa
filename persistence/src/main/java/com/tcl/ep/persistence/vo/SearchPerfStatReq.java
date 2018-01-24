package com.tcl.ep.persistence.vo;

/**
 * @author huan.yang
 *
 */
public class SearchPerfStatReq {
	/**
	 * 项目名称
	 */
	private Long projectId;
	private String projectName;
	/**
	 * 模块名称
	 */
	private String module;

	/**
	 * 开始日期
	 */
	private String startDate;

	/**
	 * 结束日期
	 */
	private String endDate;
	private String env;
	private String ip; // local ip address
	private String api;
	private String className;
	private String methodName;
	private String signature;
	private Integer callCount;
	private Integer avgCostTime;
	private Integer pageNo = 1; // 默认为第一页
	private Integer pageSize = 30; // 默认每页30条

	public String getApi() {
		return api;
	}

	public Integer getAvgCostTime() {
		return avgCostTime;
	}

	public Integer getCallCount() {
		return callCount;
	}

	public String getClassName() {
		return className;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getEnv() {
		return env;
	}

	public String getIp() {
		return ip;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getModule() {
		return module;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getSignature() {
		return signature;
	}

	public String getStartDate() {
		return startDate;
	}

	public Integer getStartItem() {
		if (pageNo <= 1) {
			return 0;
		}
		return (pageNo - 1) * pageSize;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public void setAvgCostTime(Integer avgCostTime) {
		this.avgCostTime = avgCostTime;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
