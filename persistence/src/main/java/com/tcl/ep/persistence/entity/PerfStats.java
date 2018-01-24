package com.tcl.ep.persistence.entity;

public class PerfStats {
    private Long id;

    private Long projectId;

    private String env;

    private String top5TraceIds;

    private String ip;

    private String api;

    private String className;

    private String methodName;

    private String signature;

    private Integer callCount;

    private Integer maxCostTime;

    private Integer minCostTime;

    private Integer avgCostTime;

    private Long startTime;

    private Long endTime;

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

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env == null ? null : env.trim();
    }

    public String getTop5TraceIds() {
        return top5TraceIds;
    }

    public void setTop5TraceIds(String top5TraceIds) {
        this.top5TraceIds = top5TraceIds == null ? null : top5TraceIds.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api == null ? null : api.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Integer getMaxCostTime() {
        return maxCostTime;
    }

    public void setMaxCostTime(Integer maxCostTime) {
        this.maxCostTime = maxCostTime;
    }

    public Integer getMinCostTime() {
        return minCostTime;
    }

    public void setMinCostTime(Integer minCostTime) {
        this.minCostTime = minCostTime;
    }

    public Integer getAvgCostTime() {
        return avgCostTime;
    }

    public void setAvgCostTime(Integer avgCostTime) {
        this.avgCostTime = avgCostTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}