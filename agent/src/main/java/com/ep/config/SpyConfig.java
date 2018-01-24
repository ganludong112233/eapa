package com.ep.config;

public class SpyConfig extends AbstractConfig {

    private final static String SPY_SYS_SERVICEURL = "spy.sys.serviceUrl";
    private final static String SPY_JVM_SERVICEURL = "spy.jvm.serviceUrl";

    public static String getSysServiceUrl() {
        return properties.getString(SPY_SYS_SERVICEURL);
    }

    public static String getJVMServiceUrl() {
        return properties.getString(SPY_JVM_SERVICEURL);
    }
}
