package com.ep.inst.perf;

import java.util.Date;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import com.ep.config.Configuration;
import com.ep.config.PerfConfig;
import com.ep.hook.ShutdownHook;
import com.ep.hook.ShutdownHookContext;
import com.ep.inst.trace.Tracer;
import com.ep.model.PerfInfo;
import com.ep.util.DateUtil;
import com.ep.util.ExceptionUtil;
import com.ep.util.FileUtil;
import com.ep.util.RuntimeUtil;
import com.ep.util.TimeBasedRollingFile;

public class PerfUtil{
    private static ThreadLocal<Stack<Long>> startTimeStack = new ThreadLocal<Stack<Long>>();
    private static LinkedBlockingQueue<PerfInfo> perfInfos = new LinkedBlockingQueue<PerfInfo>();
    private final static PerfoStatsProcessor statsProcessor = new PerfoStatsProcessor(5);
    
    static{
        //start the perf log processor
        new PerfProcessor();
    }
    
    public static void recordStartTime() {
        Stack<Long> stack = startTimeStack.get();
        if (stack == null) {
            stack = new Stack<Long>();
            startTimeStack.set(stack);
        }
        stack.push(System.currentTimeMillis());
    }

    /**
     * Noticed that: all of the methods of PerfUtil class are static class be loaded by
     * AppClassLoader, So can't reference the other class (such as tomcat/springmvc) as these
     * classed loaded by standard/webappclassloder
     * 
     * @param args
     * @param className
     * @param methodName
     * @param signature
     */
    public static void recordEndTime(String className, String methodName, String signature) {
        long endTime = System.currentTimeMillis();
        long startTime = ((Stack<Long>) startTimeStack.get()).pop();
        long timeElapsed = endTime - startTime;

        PerfInfo pi = new PerfInfo();
        pi.setClassName(className);
        pi.setMethodName(methodName);
        pi.setSignature(signature);
        pi.setCostTime(timeElapsed);
        pi.setOccurTime(new Date().getTime());
        pi.setTraceId(Tracer.getTraceId());
         
        perfInfos.add(pi);

    }

    public static void recordEndTime(String api) {
        long endTime = System.currentTimeMillis();
        long startTime = ((Stack<Long>) startTimeStack.get()).pop();
        long timeElapsed = endTime - startTime;

        PerfInfo pi = new PerfInfo();
        pi.setApi(api);
        pi.setCostTime(timeElapsed);
        pi.setOccurTime(new Date().getTime());
        pi.setTraceId(Tracer.getTraceId());
        pi.setExceptionMark(ExceptionUtil.isHappenedExecption()? 1 : 0);
        perfInfos.add(pi);
    }
    
    
    static class PerfProcessor  implements Runnable,ShutdownHook{
        private TimeBasedRollingFile webRollingFile = null;
        private TimeBasedRollingFile defaultRollingFile = null;
        private Boolean shutdownWatcher=false;

        private String getWEBPerfLogFilePath() {
            String webRollingFilePath = PerfConfig.getAPILogFile();
            if (webRollingFilePath.startsWith("/")) {
                return webRollingFilePath;
            }

            return FileUtil.getDefaultLogDirectory() + FileUtil.getFileName(webRollingFilePath);
        }
        
        private String getDefaultPerfLogFilePath() {
            String defaultRollingFilePath = PerfConfig.getLogFile();
            if (defaultRollingFilePath.startsWith("/")) {
                return defaultRollingFilePath;
            }

            return FileUtil.getDefaultLogDirectory() + FileUtil.getFileName(defaultRollingFilePath);
        }
        
        public PerfProcessor() {
            int retainDays = PerfConfig.getLogRetainDays();

            String webRollingFilePath = getWEBPerfLogFilePath();
            webRollingFile = new TimeBasedRollingFile(webRollingFilePath, retainDays);

            String defaultRollingFilePath = getDefaultPerfLogFilePath();
            if (defaultRollingFilePath.equals(webRollingFilePath)) {
                defaultRollingFile = webRollingFile;
            } else {
                defaultRollingFile = new TimeBasedRollingFile(defaultRollingFilePath, retainDays);
            }

            Thread thread = new Thread(this, "Perf Log handler Thread");
            // the unprocessed msg will be lost. to make sure the all the msg will be processed
            // successfully by add shutdownHook.
            ShutdownHookContext.registerHook(this);
            thread.setDaemon(true);
            thread.start();
        }
        
        @Override
        public void shutdown() {
            shutdownWatcher = true;
            synchronized (shutdownWatcher) {
                while (!perfInfos.isEmpty()) {
                    hanldLog(perfInfos.poll());
                }
            }
        }
        
        @Override
        public void run() {
            while (true) {
                synchronized (shutdownWatcher) {
                    if (shutdownWatcher) {
                        break;
                    }
                    try {
                        hanldLog(perfInfos.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        
        public void hanldLog(PerfInfo perfInfo) {
            perfInfo.setProjectId(Configuration.getProjectId());
            perfInfo.setEnv(Configuration.getServerEnv());
            perfInfo.setIp(RuntimeUtil.getLocalIp());
            perist2File(perfInfo);
            stats(perfInfo);
        }

        private void stats(PerfInfo perfInfo) {
            statsProcessor.process(perfInfo);
        }
        
        private  void perist2File(PerfInfo perfInfo) {
        	StringBuilder sb = new StringBuilder();
            if (perfInfo.getApi() == null) {
                sb.append(DateUtil.format2Str(perfInfo.getOccurTime()));
                sb.append(" - ");
                sb.append(perfInfo.getIp());
                sb.append(" - ");
                sb.append(perfInfo.getEnv());
                sb.append(" - ");
                sb.append(perfInfo.getProjectId());
                sb.append(" - ");
                sb.append(perfInfo.getTraceId());
                sb.append(" - ");
                sb.append(perfInfo.getClassName());
                sb.append(" - ");
                sb.append(perfInfo.getMethodName());
                sb.append(" - ");
                sb.append(perfInfo.getCostTime());
                
                defaultRollingFile.append(sb.toString(), perfInfo.getOccurTime());
                defaultRollingFile.append("\n", perfInfo.getOccurTime());
            } else {
                sb.append(DateUtil.format2Str(perfInfo.getOccurTime()));
                sb.append(" - ");
                sb.append(perfInfo.getIp());
                sb.append(" - ");
                sb.append(perfInfo.getEnv());
                sb.append(" - ");
                sb.append(perfInfo.getProjectId());
                sb.append(" - ");
                sb.append(perfInfo.getTraceId());
                sb.append(" - ");
                sb.append(perfInfo.getExceptionMark());
                sb.append(" - ");
                sb.append(perfInfo.getApi());
                sb.append(" - ");
                sb.append(perfInfo.getCostTime());
               
                webRollingFile.append(sb.toString(), perfInfo.getOccurTime());
                webRollingFile.append("\n", perfInfo.getOccurTime());
            }
        }
    }
}




