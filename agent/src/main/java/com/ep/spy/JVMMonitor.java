package com.ep.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ep.config.SpyConfig;
import com.ep.model.JVMMonitorInfo;
import com.ep.transport.HttpClientUtil;
import com.ep.util.EapaClientConstants;
import com.ep.util.JVMUtil;

/**
 * Created by panmin on 16-12-19.
 */
public class JVMMonitor implements Runnable {
    private static JVMMonitor instance = null;
    private static int count = 0; // 执行次数
    private static List<JVMMonitorInfo> jvmInfoList =
            new ArrayList<>(EapaClientConstants.MAX_COUNT);

    long startTime = System.currentTimeMillis();

    private JVMMonitor() {}

    public static JVMMonitor getInstance() {
        if (instance == null) {
            synchronized (JVMMonitor.class) {
                if (instance == null) {
                    instance = new JVMMonitor();
                }
            }
        }
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            JVMMonitorInfo jvmMonitorInfo = null;
            // 1.do something
            jvmMonitorInfo = getJVMInfo();
            jvmInfoList.add(jvmMonitorInfo);
            // 2.重置统计时间点
            startTime = System.currentTimeMillis();
            // 休眠一分钟
            sleep(EapaClientConstants.WAIT_TIME);

            count++;
            if (count == EapaClientConstants.MAX_COUNT) {
                // 1.上报系统数据
                HttpClientUtil.postJson(SpyConfig.getJVMServiceUrl(),
                        JSON.toJSONString(jvmInfoList));
                // 2.计数重置
                count = 0;
                // 3.列表清空
                jvmInfoList.clear();
            }
        }
    }

    private JVMMonitorInfo getJVMInfo() {
        JVMMonitorInfo jvmInfo = new JVMMonitorInfo();
        Map<String, Double> memoryInfo = JVMUtil.getMemoryPoolInfo();
        jvmInfo.setSurvivorUsage(memoryInfo.get("survivorUsage").longValue());
        jvmInfo.setEdenUsage(memoryInfo.get("edenUsage").longValue());
        jvmInfo.setOldUsage(memoryInfo.get("oldUsage").longValue());
        jvmInfo.setPermUsage(memoryInfo.get("permUsage").longValue());
        jvmInfo.setHeapUsage(JVMUtil.getHeapUsage());
        jvmInfo.setNonHeapUsage(JVMUtil.getNonHeapUsage());
        jvmInfo.setThread(JVMUtil.getThreadInfo());
        return jvmInfo;
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
