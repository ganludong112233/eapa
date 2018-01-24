package com.ep.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RuntimeUtil {
    private static long processId = -1;
    private static String localIp;

    static {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        processId = Long.parseLong(name.split("@")[0]);
    }

    public static long getProceeId() {
        return processId;
    }

    public static long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static String getLocalIp() {
        if (localIp == null) {
            try {
                // get the IP address from /etc/hosts file
                localIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return localIp;
    }

    public static long getIntIpAddr() {
        String ip = getLocalIp();
        if (ip == null) {
            ip = "0.0.0.0";
        }
        String[] arrayIP = ip.split("\\.");
        int sip1 = Integer.parseInt(arrayIP[0]);
        int sip2 = Integer.parseInt(arrayIP[1]);
        int sip3 = Integer.parseInt(arrayIP[2]);
        int sip4 = Integer.parseInt(arrayIP[3]);
        return sip1 + sip2 + sip3 + sip4;
    }
}
