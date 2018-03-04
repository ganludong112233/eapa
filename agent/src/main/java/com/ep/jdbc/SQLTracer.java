package com.ep.jdbc;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ep.config.Configuration;
import com.ep.config.SQLConfig;
import com.ep.inst.trace.Tracer;
import com.ep.model.SQLPerf;
import com.ep.util.DateUtil;
import com.ep.util.RuntimeUtil;
import com.ep.util.TimeBasedRollingFile;

public class SQLTracer {

    private static TimeBasedRollingFile sqlLog;
    private static ExecutorService exector = Executors.newFixedThreadPool(1);
    static {
        String logFile = SQLConfig.getLogFile();
        if (logFile != null) {
            sqlLog = new TimeBasedRollingFile(logFile, SQLConfig.getLogFileRetainDays());
        }
    }

    public static SQLPerf start(String sql) {
        // format the sql, replace the blank characters like \t,\n
        SQLPerf perf = new SQLPerf();
        sql = sql.replaceAll("\\s+", " ");
        perf.setEnv(Configuration.getServerEnv());
        perf.setIp(RuntimeUtil.getLocalIp());
        perf.setProjectId(Configuration.getProjectId());
        perf.setTraceId(Tracer.getTraceId());
        perf.setSql(sql);
        perf.setStartTime(new Date().getTime());
        return perf;
    }

    public static void end(SQLPerf perf) {
        perf.setEndTime(new Date().getTime());
        process(perf);
    }

    private static void process(final SQLPerf perf) {
        exector.execute(new Runnable() {
            @Override
            public void run() {
                final StringBuilder log = new StringBuilder();
                log.append(perf.getTraceId() + " - ");
                log.append(DateUtil.format2Str(perf.getStartTime()) + " - ");
                log.append(perf.getElapsedTime() + " - ");
                log.append(perf.getSql());
                log.append("\n");
                sqlLog.append(log.toString(), perf.getStartTime());
            }
        });
    }
}
