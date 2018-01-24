package com.ep.model;

import java.util.Map;

/**
 * Created by panmin on 16-12-19.
 */
public class JVMMonitorInfo {
    private double heapUsage;
    private double nonHeapUsage;
    private double survivorUsage;
    private double edenUsage;
    private double oldUsage;
    private double permUsage;
    private MonitorThread threadInfo;

    public double getHeapUsage() {
        return heapUsage;
    }

    public void setHeapUsage(double heapUsage) {
        this.heapUsage = heapUsage;
    }

    public double getNonHeapUsage() {
        return nonHeapUsage;
    }

    public void setNonHeapUsage(double nonHeapUsage) {
        this.nonHeapUsage = nonHeapUsage;
    }

    public double getSurvivorUsage() {
        return survivorUsage;
    }

    public void setSurvivorUsage(double survivorUsage) {
        this.survivorUsage = survivorUsage;
    }

    public double getEdenUsage() {
        return edenUsage;
    }

    public void setEdenUsage(double edenUsage) {
        this.edenUsage = edenUsage;
    }

    public double getOldUsage() {
        return oldUsage;
    }

    public void setOldUsage(double oldUsage) {
        this.oldUsage = oldUsage;
    }

    public double getPermUsage() {
        return permUsage;
    }

    public void setPermUsage(double permUsage) {
        this.permUsage = permUsage;
    }

    public MonitorThread getThreadInfo() {
        return threadInfo;
    }

    public void setThread(Map<String, Object> threadInfo) {
        MonitorThread monitorThread = new MonitorThread(threadInfo.get("objectName").toString(),
                (Integer) threadInfo.get("threadCount"),
                (Integer) threadInfo.get("peakThreadCount"),
                (Long) threadInfo.get("totalStartedThreadCount"),
                (Integer) threadInfo.get("daemonThreadCount"));
        this.threadInfo = monitorThread;
    }

    @Override
    public String toString() {
        return "JVMMonitorInfo{" +
                "heapUsage=" + heapUsage +
                ", nonHeapUsage=" + nonHeapUsage +
                ", survivorUsage=" + survivorUsage +
                ", edenUsage=" + edenUsage +
                ", oldUsage=" + oldUsage +
                ", permUsage=" + permUsage +
                ", threadInfo=" + threadInfo +
                '}';
    }

    private static class MonitorThread {
        String objectName;  //端口名称
        int threadCount;
        int peakThreadCount;
        long totalStartedThreadCount;
        int daemonThreadCount;

        public MonitorThread(String objectName, int threadCount, int peakThreadCount, long totalStartedThreadCount, int daemonThreadCount) {
            this.objectName = objectName;
            this.threadCount = threadCount;
            this.peakThreadCount = peakThreadCount;
            this.totalStartedThreadCount = totalStartedThreadCount;
            this.daemonThreadCount = daemonThreadCount;
        }

        @Override
        public String toString() {
            return "MonitorThread{" +
                    "objectName='" + objectName + '\'' +
                    ", threadCount=" + threadCount +
                    ", peakThreadCount=" + peakThreadCount +
                    ", totalStartedThreadCount=" + totalStartedThreadCount +
                    ", daemonThreadCount=" + daemonThreadCount +
                    '}';
        }
    }
}
