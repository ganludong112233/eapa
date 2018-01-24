package com.ep.config;

public class ExceptionConfig extends AbstractConfig {

    private final static String EXCEPTION_LOG_RETAINDAYS = "exception.log.retainDays";
    private final static String EXCEPTION_LOGFILE = "exception.logfile";
    private final static String MATCHED_KEY = "exception.matched.pattern";
    private final static String MATCH_ANNOTION_KEY = "exception.matched.annotion";
    private final static String EXCLUDED_ANNOTION_KEY = "exception.excluded.annotion";
    private final static String EXCLUDED_KEY = "exception.excluded.pattern";
    private final static String SEND_2_REMOTE_KEY = "exception.send2Remote";
    private final static String SERVICE_URL_KEY = "exception.serviceUrl";
    private final static String ENABLE_KEY="exception.enable";
    
    public static Boolean isEnabled(){
        return properties.getBoolean(ENABLE_KEY);
    }

    public static String getServiceUrl() {
        return properties.getString(SERVICE_URL_KEY);
    }

    public static int getLogRetainDays() {
        return properties.getInt(EXCEPTION_LOG_RETAINDAYS,5);
    }

    public static String getLogFile() {
        return properties.getString(EXCEPTION_LOGFILE);
    }

    public static boolean isSend2Remote() {
        return properties.getBoolean(SEND_2_REMOTE_KEY);
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
