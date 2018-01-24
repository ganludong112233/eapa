package com.ep.inst.perf;

import java.util.HashMap;
import java.util.Map;

import com.ep.model.PerfInfo;
import com.ep.model.PerfStats;
import com.ep.transport.PerfStatsRemoteSender;

public class PerfoStatsProcessor {

    private Map<String, PerfStats> perfStatses = new HashMap<String, PerfStats>();
    private int periodMinutes;
    private PerfStatsRemoteSender remoteSender = PerfStatsRemoteSender.getInstance();

    public PerfoStatsProcessor(int periodMinutes) {
        this.periodMinutes = periodMinutes;
    }

    public void process(PerfInfo perfInfo) {
        String key = this.getKey(perfInfo);
        PerfStats stats = perfStatses.get(key);
        if (stats == null) {
            stats = PerfStats.createNewStats(perfInfo, periodMinutes);
            perfStatses.put(key, stats);
            return;
        }

        if (perfInfo.getOccurTime() > stats.getEndTime()) {
            // send to remote server
            remoteSender.queuePerfStats(stats);
            // create new stats
            stats = PerfStats.createNewStats(perfInfo, periodMinutes);
            perfStatses.put(key, stats);
            return;
        }

        stats.addNewPerf(perfInfo);
    }

    public String getKey(PerfInfo perfInfo) {
        if (perfInfo.getApi() != null) {
            return perfInfo.getApi();
        }

        return perfInfo.getClassName() + perfInfo.getMethodName() + perfInfo.getSignature();
    }

    public String getKey(PerfStats stats) {
        if (stats.getApi() != null) {
            return stats.getApi();
        }

        return stats.getClassName() + stats.getMethodName() + stats.getSignature();
    }

}
