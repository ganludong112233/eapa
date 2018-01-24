package com.ep.util;

/**
 * Created by panmin on 16-11-29.
 */
public interface EapaClientConstants {
    int MAX_COUNT = 10; //统计10次后上报
    int WAIT_TIME = 60000; //上报间隔等待时间,默认60秒

    String PARTITION_DATA = "/data"; //data所在分区路劲
}
