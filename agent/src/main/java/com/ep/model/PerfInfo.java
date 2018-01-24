package com.ep.model;

public class PerfInfo extends BaseInfo {
    private String className;
    private String methodName;
    private String signature;
    private String api;
    private Long occurTime;
    private long costTime;
    private Integer exceptionMark; // 0 未发生异常，1 发生异常 

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public Long getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Long occurTime) {
        this.occurTime = occurTime;
    }

	public Integer getExceptionMark() {
		return exceptionMark;
	}

	public void setExceptionMark(Integer exceptionMark) {
		this.exceptionMark = exceptionMark;
	}

}
