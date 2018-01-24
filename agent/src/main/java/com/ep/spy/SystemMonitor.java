package com.ep.spy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ep.config.Configuration;
import com.ep.config.SpyConfig;
import com.ep.model.SystemMonitorInfo;
import com.ep.transport.HttpClientUtil;
import com.ep.util.EapaClientConstants;
import com.ep.util.SystemInfoUtil;

/**
 * Created by panmin on 16-11-29.
 */
public class SystemMonitor implements Runnable {
    private static SystemMonitor instance = null;
    private static int count = 0; // 执行次数
    private static List<SystemMonitorInfo> systemInfoList = new ArrayList<>(
            EapaClientConstants.MAX_COUNT);

    long startTime = System.currentTimeMillis();

    private SystemMonitor() {}

    public static SystemMonitor getInstance() {
        if (instance == null) {
            synchronized (SystemMonitor.class) {
                if (instance == null) {
                    instance = new SystemMonitor();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        SystemMonitorInfo systemMonitorInfo;
        while (true) {

            // 1.do something
            systemMonitorInfo = getSystemInfo();
            systemInfoList.add(systemMonitorInfo);
            // 2.重置统计时间点
            startTime = System.currentTimeMillis();
            // 休眠一分钟
            sleep(EapaClientConstants.WAIT_TIME);
            count++;

            if (count == EapaClientConstants.MAX_COUNT) {
                // 1.上报系统数据
                HttpClientUtil.postJson(SpyConfig.getSysServiceUrl(),
                        JSON.toJSONString(systemInfoList));
                // 2.计数重置
                count = 0;
                // 3.列表清空
                systemInfoList.clear();
            }
        }
    }

    /**
     * 获取系统监控信息
     * 
     * @return
     */
    private SystemMonitorInfo getSystemInfo() {
        SystemMonitorInfo result = new SystemMonitorInfo();
        result.setProjectId(Configuration.getProjectId());
        result.setCpuUsage(SystemInfoUtil.cpuUsage());
        result.setMemUsage(SystemInfoUtil.memoryUsage());
        result.setHardDiskIOSpeed(SystemInfoUtil.getHardDiskIOPercent());
        result.setNetworkSpeed(SystemInfoUtil.getNetworkSpeed());
        result.setPartitionUsage(SystemInfoUtil
                .getPartitionUsage(EapaClientConstants.PARTITION_DATA));
        Calendar calendar = Calendar.getInstance();
        result.setCollectTime(calendar.getTimeInMillis());
        return result;
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
