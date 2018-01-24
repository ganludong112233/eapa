package com.tcl.ep.persistence.entity;

public class TracedPerfInfo {
    private String traceId; // will create one unique traceId for every call.
    // pi
    private long projectId;
    // env
    private String env;
    // ip
    private String ip; // local ip address
    // cn
    private String className;
    // mn
    private String methodName;
    // s
    private String signature;
    // api
    private String api;
    // ot
    private long occurTime;
    // ct
    private long costTime;

    public TracedPerfInfo() {

    }

    public TracedPerfInfo(String rowKey) {
        String[] rowKeyData = rowKey.split("|");
        this.traceId = rowKeyData[0];
        this.occurTime = Long.parseLong(rowKeyData[1]);
    }

    public String getRowKey() {
        if (traceId == null || occurTime == 0) {
            throw new RuntimeException("TraceId and Occurtime should not be null");
        }
        return traceId + "|" + occurTime;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public long getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(long occurTime) {
        this.occurTime = occurTime;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

}
