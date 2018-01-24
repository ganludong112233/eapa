package com.tcl.ep.persistence.entity;

import java.util.TreeMap;

public class PerformanceInfo {
	private long startTime;
	private long endTime;
	private long projectId;
	private String env;
	private TreeMap<Integer, String> top5TraceIds; // will create one unique
	// traceId for every call.
	private String localIp; // local ip address
	private String api;
	private String className;
	private String methodName;
	private String signature;
	private int callCount;
	private int maxCostTime;
	private int minCostTime;
	private int avgCostTime;
	private transient int topThresholdCostTime;
	private transient long totalCostTime;

	public String getApi() {
		return api;
	}

	public int getAvgCostTime() {
		return avgCostTime;
	}

	public int getCallCount() {
		return callCount;
	}

	public String getClassName() {
		return className;
	}

	public long getEndTime() {
		return endTime;
	}

	public String getEnv() {
		return env;
	}

	public String getLocalIp() {
		return localIp;
	}

	public int getMaxCostTime() {
		return maxCostTime;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getMinCostTime() {
		return minCostTime;
	}

	public long getProjectId() {
		return projectId;
	}

	public String getSignature() {
		return signature;
	}

	public long getStartTime() {
		return startTime;
	}

	public TreeMap<Integer, String> getTop5TraceIds() {
		return top5TraceIds;
	}

	public int getTopThresholdCostTime() {
		return topThresholdCostTime;
	}

	public long getTotalCostTime() {
		return totalCostTime;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public void setAvgCostTime(int avgCostTime) {
		this.avgCostTime = avgCostTime;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public void setMaxCostTime(int maxCostTime) {
		this.maxCostTime = maxCostTime;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setMinCostTime(int minCostTime) {
		this.minCostTime = minCostTime;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setTop5TraceIds(TreeMap<Integer, String> top5TraceIds) {
		this.top5TraceIds = top5TraceIds;
	}

	public void setTopThresholdCostTime(int topThresholdCostTime) {
		this.topThresholdCostTime = topThresholdCostTime;
	}

	public void setTotalCostTime(long totalCostTime) {
		this.totalCostTime = totalCostTime;
	}
}
