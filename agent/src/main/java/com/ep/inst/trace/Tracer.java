package com.ep.inst.trace;

import com.ep.util.RuntimeUtil;

/**
 * IP+PROCESS+THREAD+COUNTER
 * 
 * IP: 4 bytes PROCESS: 6 bytes THREAD: 4 bytes COUNTER: 10 bytes
 * 
 * @author yi_liu
 * 
 */
public class Tracer {
    static ThreadLocal<String> traceIds = new ThreadLocal<String>();
    static ThreadLocal<Long> counter = new ThreadLocal<Long>();
    static long processId = RuntimeUtil.getProceeId();

    public static String generateTraceId() {
        Long counterValue = counter.get();
        if (counterValue == null) {
            counterValue = 1l;
        } else {
            counterValue = counterValue + 1;
        }
        counter.set(counterValue);
        StringBuilder sb = new StringBuilder();
        sb.append(normolizenNumber(RuntimeUtil.getIntIpAddr(), 4));
        sb.append(normolizenNumber(processId, 6));
        sb.append(normolizenNumber(RuntimeUtil.getThreadId(), 4));
        sb.append(normolizenNumber(counterValue, 10));
        traceIds.set(sb.toString());
        return sb.toString();
    }

    public static String getTraceId() {
        String traceId = traceIds.get();
        // if (traceId == null) {
        // traceId = generateTraceId();
        // }
        return traceId;
    }

    public static void removeTraceId() {
        traceIds.remove();
    }

    public static String normolizenNumber(long value, int bytes) {
        char[] result = new char[bytes];
        String str = String.valueOf(value);
        int diff = bytes - str.length();
        for (int idx = bytes - 1; idx >= 0; idx--) {
            if (idx + 1 <= diff) {
                result[idx] = '0';
            } else {
                result[idx] = str.charAt(idx - diff);
            }

        }
        return new String(result);
    }
}
