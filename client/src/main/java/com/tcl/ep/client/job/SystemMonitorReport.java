package com.tcl.ep.client.job;

import com.alibaba.fastjson.JSON;
import com.tcl.ep.client.constant.EapaClientConstants;
import com.tcl.ep.client.model.SystemMonitorInfo;
import com.tcl.ep.client.util.ClientConfiguration;
import com.tcl.ep.client.util.HttpClientUtil;
import com.tcl.ep.client.util.SystemInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by panmin on 16-11-29.
 */
public class SystemMonitorReport implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SystemMonitorReport.class);
    private static int count = 0;  //执行次数
    private static List<SystemMonitorInfo> systemInfoList = new ArrayList<>(EapaClientConstants.MAX_COUNT);

    long startTime = System.currentTimeMillis();

    @Override
    public void run() {
        SystemMonitorInfo systemMonitorInfo;
        while (true) {

            // 1.do something
            systemMonitorInfo = getSystemInfo();
            systemInfoList.add(systemMonitorInfo);
            // 2.重置统计时间点
            startTime = System.currentTimeMillis();
            //休眠一分钟
            sleep(EapaClientConstants.WAIT_TIME);
            count++;

            if (count == EapaClientConstants.MAX_COUNT) {
                try {
                    // 1.上报系统数据
                    HttpClientUtil.postJson(ClientConfiguration.getSysReportUrl(), JSON.toJSONString(systemInfoList));
                    // 2.计数重置
                    count = 0;
                    // 3.列表清空
                    systemInfoList.clear();
                } catch (IOException e) {
                    log.error("report system info exception.");
                }
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
        result.setProjectId(ClientConfiguration.getProjectId());
        result.setCpuUsage(SystemInfoUtil.cpuUsage());
        result.setMemUsage(SystemInfoUtil.memoryUsage());
        result.setHardDiskIOSpeed(SystemInfoUtil.getHardDiskIOPercent());
        result.setNetworkSpeed(SystemInfoUtil.getNetworkSpeed());
        result.setPartitionUsage(SystemInfoUtil.getPartitionUsage(EapaClientConstants.PARTITION_DATA));
        Calendar calendar =Calendar.getInstance();
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
