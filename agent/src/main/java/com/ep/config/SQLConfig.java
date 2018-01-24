package com.ep.config;

public class SQLConfig extends AbstractConfig {
    private final static String SQL_MONITOR_KEY = "sql.monitor";
    private final static String SQL_LOGFILE_KEY = "sql.logfile";
    private final static String SQL_LOGFILE_RETAILDAYS_KEY = "sql.logfile.retainDays";

    public static Boolean isMonitorEnabled() {
        return properties.getBoolean(SQL_MONITOR_KEY);
    }

    public static String getLogFile() {
        return properties.getString(SQL_LOGFILE_KEY);
    }

    public static int getLogFileRetainDays() {
        return properties.getInt(SQL_LOGFILE_RETAILDAYS_KEY, 5);
    }
}
