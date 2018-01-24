package com.ep.config;

public class PerfConfig extends AbstractConfig {
    private final static String WEB_MONITOR_KEY = "perf.web.monitor";
    private final static String PERF_SERVICE_URL_KEY = "perf.serviceUrl";
    private final static String MATCHED_KEY = "perf.matched.pattern";
    private final static String MATCH_ANNOTION_KEY = "perf.matched.annotion";
    private final static String EXCLUDED_ANNOTION_KEY = "perf.excluded.annotion";
    private final static String EXCLUDED_KEY = "perf.excluded.pattern";
    private final static String PERF_WEB_LOGFILE_KEY = "perf.web.logfile";
    private final static String PERF_LOGFILE_KEY = "perf.logfile";
    private final static String PERF_LOGFILE_RETAINDAYS_KEY = "perf.log.retainDays";
    private final static String SEND_2_REMOTE_KYE = "perf.send2remote";
    private static String ENABLE_KEY="perf.enable";
    
    public static Boolean isEnabled(){
        return properties.getBoolean(ENABLE_KEY);
    }
    public static boolean monitorWebPerf() {
        return properties.getBoolean(WEB_MONITOR_KEY);
    }

    public static boolean send2Remote() {
        return properties.getBoolean(SEND_2_REMOTE_KYE);
    }

    public static int getLogRetainDays() {
        return properties.getInt(PERF_LOGFILE_RETAINDAYS_KEY,5);
    }

    public static String getAPILogFile() {
        return properties.getString(PERF_WEB_LOGFILE_KEY);
    }

    public static String getLogFile() {
        return properties.getString(PERF_LOGFILE_KEY);
    }

    public static String getServiceUrl() {
        return properties.getString(PERF_SERVICE_URL_KEY);
    }

    public static String getMatchedPattern() {
        return properties.getString(MATCHED_KEY);
    }

    public static String getMatchedAnnotation() {
        return properties.getString(MATCH_ANNOTION_KEY);
    }

    public static String getExcludedPattern() {
        return properties.getString(EXCLUDED_KEY);
    }

    public static String getExcludedAnnotation() {
        return properties.getString(EXCLUDED_ANNOTION_KEY);
    }
}
