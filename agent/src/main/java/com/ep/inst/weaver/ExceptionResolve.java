package com.ep.inst.weaver;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.ep.config.Configuration;
import com.ep.config.ExceptionConfig;
import com.ep.model.ExceptionInfo;
import com.ep.model.ExceptionMeta;
import com.ep.transport.HttpClientUtil;
import com.ep.util.DateUtil;
import com.ep.util.RuntimeUtil;
import com.ep.util.StringUtil;
import com.ep.util.TimeBasedRollingFile;

public class ExceptionResolve {
    private static long DELAY_MILLS = 50L;
    private static BlockingQueue<ExceptionInfo> exceptionInfos =
            new LinkedBlockingQueue<ExceptionInfo>();
    private static Map<String, ExceptionMeta> collecteMsg =
            new LinkedHashMap<String, ExceptionMeta>();
    private static String serviceUrl = ExceptionConfig.getServiceUrl();

    static {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                String logfilePath = ExceptionConfig.getLogFile();
                int retainDays = ExceptionConfig.getLogRetainDays();
                boolean logException = false;
                boolean send2Remote = ExceptionConfig.isSend2Remote();
                if (!StringUtil.isBlank(logfilePath)) {
                    logException = true;
                }
                TimeBasedRollingFile timeBasedRollingFile =
                        new TimeBasedRollingFile(logfilePath, retainDays);
                while (true) {
                    ExceptionInfo exp = null;
                    try {
                        exp = exceptionInfos.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                    String exceInfoJson = takeJson(exp);
                    if (exceInfoJson != null) {
                        long time = exp.getOccurTime();
                        if (logException) {
                            timeBasedRollingFile.append(DateUtil.format2Str(exp.getOccurTime())+" - "+exp.getErrorMsg().replace("\\n","\n\r"), time);
                            timeBasedRollingFile.append("\r\n", time);
                        }

                        if (send2Remote) {
                            HttpClientUtil.postJson(serviceUrl, exceInfoJson);
                        }
                    }
                }
            }
        }, "EAPA Exception handler");
        thread.setDaemon(true);
        thread.start();
    }

    public static void collecte(Exception e, String className, String methodName, String signature) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        synchronized (collecteMsg) {
            ExceptionMeta exceptionMeta = null;
            String errorInfo = getErrorInfoFromException(e);
            if (collecteMsg.containsKey(threadId)) {
                exceptionMeta = collecteMsg.get(threadId);
                String info = exceptionMeta.getErrorInfo();
                if (errorInfo.equals(info) || errorInfo.contains(info)) {
                    return;
                }
            }
            exceptionMeta = new ExceptionMeta();
            exceptionMeta.setCollectedTime(System.currentTimeMillis());
            exceptionMeta.setErrorInfo(errorInfo);
            ExceptionInfo exceptionInfo =
                    buildExceptionInfo(e, threadId, className, methodName, errorInfo);
            exceptionInfos.add(exceptionInfo);
            collecteMsg.put(threadId, exceptionMeta);
        }
    }

    private static ExceptionInfo buildExceptionInfo(Exception e, String threadId, String className,
            String methodName, String errorInfo) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();

        exceptionInfo.setProjectId(Configuration.getProjectId());
        exceptionInfo.setEnv(Configuration.getServerEnv());
        exceptionInfo.setIp(RuntimeUtil.getLocalIp());
        exceptionInfo.setThreadId(threadId);
        exceptionInfo.setErrorMsg(errorInfo);
        String exception = e.toString();
        if(exception.indexOf(":") > 0){
        	exceptionInfo.setExceptionName(exception.substring(0, exception.indexOf(":")));
        }else{
        	exceptionInfo.setExceptionName(exception);
        }
        exceptionInfo.setHandlerMethod(methodName);
        exceptionInfo.setHandlerClass(className);
        exceptionInfo.setOccurTime(System.currentTimeMillis());

        return exceptionInfo;
    }

    public static String takeJson(ExceptionInfo exp) {
        try {
            String threadId = exp.getThreadId();
            ExceptionMeta exceptionMeta = collecteMsg.get(threadId);
            if ((System.currentTimeMillis() - exceptionMeta.getCollectedTime()) <= DELAY_MILLS) {
                exceptionInfos.add(exp);
                return null;
            }
            collecteMsg.remove(threadId);
            String exceptionJson = exp.toJson();

            return exceptionJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString().replace("\n", "\\n");
        } catch (Exception e2) {
            return "bad getErrorInfoFromException";
        }
    }
}
