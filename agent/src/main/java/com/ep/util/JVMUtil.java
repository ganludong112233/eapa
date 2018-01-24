package com.ep.util;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by panmin on 16-12-19.
 */
public class JVMUtil {

    /**
     * Java 虚拟机的内存系统
     */
    public static double getHeapUsage() {
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        MemoryUsage heap = mem.getHeapMemoryUsage();
        return heap.getUsed() * 100 / heap.getCommitted();
    }

    /**
     * Java 非堆内存
     */
    public static double getNonHeapUsage() {
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage nonHeapMemory = memory.getNonHeapMemoryUsage();
        return nonHeapMemory.getUsed() * 100 / nonHeapMemory.getCommitted();
    }

    /**
     * Java 虚拟机在其上运行的操作系统
     */
    public static Map<String, Object> getSystemInfo() {
        Map<String, Object> result = new HashMap<String, Object>();
        java.lang.management.OperatingSystemMXBean op = ManagementFactory.getOperatingSystemMXBean();
        result.put("architecture", op.getArch());
        result.put("processors", op.getAvailableProcessors());
        result.put("name", op.getName());
        result.put("version", op.getVersion());
        result.put("systemLoadAverage", op.getSystemLoadAverage());
        return result;
    }

    /**
     * Java 虚拟机的类加载系统
     */
    public static Map<String, Object> getClassLoadingInfo() {
        Map<String, Object> result = new HashMap<>();
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        result.put("totalLoadedClassCount", classLoadingMXBean.getTotalLoadedClassCount());
        result.put("loadedClassCount", classLoadingMXBean.getLoadedClassCount());
        result.put("unloadedClassCount", classLoadingMXBean.getUnloadedClassCount());
        return result;
    }

    /**
     * Java 虚拟机的线程系统
     */
    public static Map<String, Object>  getThreadInfo() {
        Map<String, Object> result = new HashMap<String, Object>();
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        result.put("objectName", thread.getObjectName());
        result.put("threadCount", thread.getThreadCount());
        result.put("peakThreadCount", thread.getPeakThreadCount());
        result.put("totalStartedThreadCount", thread.getTotalStartedThreadCount());
        result.put("daemonThreadCount", thread.getDaemonThreadCount());
        return result;
    }

    /**
     * java 虚拟机中的内存池
     */
    public static Map<String, Double> getMemoryPoolInfo() {
        Map<String, Double> result = new HashMap<>();
        List<MemoryPoolMXBean> memoryPoolMXBean = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean memory : memoryPoolMXBean) {
            if (null == memory.getUsage()) {
                continue;
            }
            long committed = memory.getUsage().getCommitted();
            long used = memory.getUsage().getUsed();
            double usage = (double) used * 100 / committed;
            if ("PS Survivor Space".equals(memory.getName())) {
                result.put("survivorUsage", usage);
            } else if ("PS Eden Space".equals(memory.getName())) {
                result.put("edenUsage", usage);
            } else if ("PS Old Gen".equals(memory.getName())) {
                result.put("oldUsage", usage);
            } else if ("PS Perm Gen".equals(memory.getName())) {
                result.put("permUsage", usage);
            }
        }
        return result;
    }

    /**
     * Java 虚拟机中的垃圾回收器信息。
     */
    public static List<Map<String, Object>> getGarbageCollectorInfo() {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        List<GarbageCollectorMXBean> gc = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean GarbageCollectorMXBean : gc) {
            map = new HashMap<String, Object>();
            map.put("name", GarbageCollectorMXBean.getName());
            map.put("collectionCount", GarbageCollectorMXBean.getCollectionCount());
            map.put("collectionTime", GarbageCollectorMXBean.getCollectionTime());
            resultList.add(map);
        }
        return resultList;
    }
}
