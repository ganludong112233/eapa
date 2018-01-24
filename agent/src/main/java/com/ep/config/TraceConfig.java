package com.ep.config;

import java.util.Set;

import com.ep.util.StringUtil;

public class TraceConfig extends AbstractConfig {

    private final static String TRACEID_ENTRANCE = "traceId.entrance";

    public static Set<String> getTranceEntrances() {
        String inputString = properties.getString(TRACEID_ENTRANCE);
        return StringUtil.split2Set(inputString, ",");
    }
}
