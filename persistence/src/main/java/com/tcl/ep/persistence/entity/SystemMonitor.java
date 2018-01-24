package com.tcl.ep.persistence.entity;

/**
 * Created by panmin on 16-11-29.
 */
public class SystemMonitor {
    private Long projectId;
    private String projectName;
    private String cpuUsage;
    private String memUsage;
    private String partitionUsage;
    private String networkSpeed;
    private String hardDiskIOSpeed;
    private Long collectTime;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(String memUsage) {
        this.memUsage = memUsage;
    }

    public String getPartitionUsage() {
        return partitionUsage;
    }

    public void setPartitionUsage(String partitionUsage) {
        this.partitionUsage = partitionUsage;
    }

    public String getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed(String networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public String getHardDiskIOSpeed() {
        return hardDiskIOSpeed;
    }

    public void setHardDiskIOSpeed(String hardDiskIOSpeed) {
        this.hardDiskIOSpeed = hardDiskIOSpeed;
    }

    public Long getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Long collectTime) {
        this.collectTime = collectTime;
    }
}
