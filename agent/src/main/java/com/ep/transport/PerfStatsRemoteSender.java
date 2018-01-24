package com.ep.transport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

import com.alibaba.fastjson.JSON;
import com.ep.config.Configuration;
import com.ep.config.PerfConfig;
import com.ep.hook.ShutdownHook;
import com.ep.hook.ShutdownHookContext;
import com.ep.model.PerfStats;
import com.ep.util.RuntimeUtil;

public class PerfStatsRemoteSender implements Runnable,ShutdownHook {
    private Queue<PerfStats> perfstatsQueue = new LinkedList<PerfStats>();
    private int maxQueueSize = 1000;
    private int batchSize = 50;
    private long checkInterval = 5000000000l; // default 5s
    private String serviceUrl = PerfConfig.getServiceUrl();
    private Boolean shutdownWatcher=false;
    private static PerfStatsRemoteSender instance = new PerfStatsRemoteSender();

    public static PerfStatsRemoteSender getInstance() {
        return instance;
    }

    public PerfStatsRemoteSender() {
        // the unprocessed msg will be lost. to make sure the all the msg will be processed
        // successfully by add shutdownHook.
        Thread thread = new Thread(this, "EAPA Remote Sender Thread");
        ShutdownHookContext.registerHook(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void queuePerfStats(PerfStats stats) {
        if (perfstatsQueue.size() >= maxQueueSize) {
            // if the cached queue have excessed the max length , discard this request
            System.out
                    .println("Error: The performance sender queue is full, discard this request!!!");
            return;
        }
        perfstatsQueue.add(stats);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (shutdownWatcher) {
                if (shutdownWatcher) {
                    break;
                }
                LockSupport.parkNanos(checkInterval);
                if (perfstatsQueue.isEmpty()) {
                    return;
                }
                process();
            }
        }
    }

    public void process(){
        List<PerfStats> statsList = new ArrayList<PerfStats>();
        PerfStats stats = null;
        for (int i = 1; i <= batchSize; i++) {
            stats = perfstatsQueue.poll();
            if (stats == null) {
                break;
            }
            stats.setEnv(Configuration.getServerEnv());
            stats.setProjectId(Configuration.getProjectId());
            stats.setIp(RuntimeUtil.getLocalIp());
            statsList.add(stats);
        }
        if (PerfConfig.send2Remote()) {
            HttpClientUtil.postJson(serviceUrl, JSON.toJSONString(statsList));
        }
    }
    
    @Override
    public void shutdown() {
        shutdownWatcher=true;
        synchronized (shutdownWatcher) {
            while(!perfstatsQueue.isEmpty()){
                process();
            }
        }
    }
}
