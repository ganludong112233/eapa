package com.ep.model;

import java.util.Calendar;
import java.util.TreeMap;

/**
 * if the value of api is not empty, this class represent API performance statistics else it refers
 * method execution statistics
 * 
 * @author yi_liu
 * 
 */
public class PerfStats {
    private long projectId;
    private String env;
    private TreeMap<Integer, String> top5TraceIds; // will create one unique traceId for every call.
    private String ip; // local ip address
    private String api;
    private String className;
    private String methodName;
    private String signature;
    private int callCount;
    private long maxCostTime;
    private long minCostTime;
    @SuppressWarnings("unused")
    private long avgCostTime;
    private long startTime;
    private long endTime;
    private transient int topThresholdCostTime;
    private transient long totalCostTime;

    public static PerfStats createNewStats(PerfInfo perfInfo, int periodMinutes) {
        PerfStats stats = new PerfStats();
        stats.setApi(perfInfo.getApi());
        stats.setCallCount(1);
        stats.setClassName(perfInfo.getClassName());
        stats.setMethodName(perfInfo.getMethodName());
        stats.setSignature(perfInfo.getSignature());
        Period period = calcPerfStatsPeroid(perfInfo.getOccurTime(), periodMinutes);
        stats.setStartTime(period.getStartTime());
        stats.setEndTime(period.getEndTime());
        stats.setMinCostTime(perfInfo.getCostTime());
        stats.setMaxCostTime(perfInfo.getCostTime());
        stats.setTotalCostTime(perfInfo.getCostTime());
        return stats;
    }

    public static Period calcPerfStatsPeroid(long time, int periodMinutes) {
        Calendar periodCal = Calendar.getInstance();
        periodCal.setTimeInMillis(time);
        periodCal.set(Calendar.SECOND, 0);
        periodCal.set(Calendar.MILLISECOND, 0);

        int currMinutesNum = periodCal.get(Calendar.MINUTE) / periodMinutes;
        periodCal.set(Calendar.MINUTE, currMinutesNum * periodMinutes);

        Period period = new Period();
        period.setStartTime(periodCal.getTimeInMillis());

        periodCal.set(Calendar.MINUTE, (currMinutesNum + 1) * periodMinutes);
        period.setEndTime(periodCal.getTimeInMillis());
        return period;
    }

    public void addNewPerf(PerfInfo perfInfo) {
        this.callCount++;
        long costTime = perfInfo.getCostTime();
        this.setTotalCostTime(totalCostTime + costTime);
        if (costTime > this.getMaxCostTime()) {
            this.setMaxCostTime(costTime);
        }

        if (costTime < this.getMinCostTime()) {
            this.setMinCostTime(costTime);
        }

    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public long getMaxCostTime() {
        return maxCostTime;
    }

    public void setMaxCostTime(long maxCostTime) {
        this.maxCostTime = maxCostTime;
    }

    public long getMinCostTime() {
        return minCostTime;
    }

    public void setMinCostTime(long minCostTime) {
        this.minCostTime = minCostTime;
    }

    public long getAvgCostTime() {
        return this.totalCostTime / this.callCount;
    }

    public void setAvgCostTime(long avgCostTime) {
        this.avgCostTime = avgCostTime;
    }

    public TreeMap<Integer, String> getTop5TraceIds() {
        return top5TraceIds;
    }

    public void setTop5TraceIds(TreeMap<Integer, String> top5TraceIds) {
        this.top5TraceIds = top5TraceIds;
    }

    public int getTopThresholdCostTime() {
        return topThresholdCostTime;
    }

    public void setTopThresholdCostTime(int topThresholdCostTime) {
        this.topThresholdCostTime = topThresholdCostTime;
    }

    public long getTotalCostTime() {
        return totalCostTime;
    }

    public void setTotalCostTime(long totalCostTime) {
        this.totalCostTime = totalCostTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
