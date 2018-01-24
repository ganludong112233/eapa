package com.tcl.ep.client.localcache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionInfoLocalRepository {
	 /** 日志. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionInfoLocalRepository.class);

    private static LinkedBlockingQueue<Object> LOCAL_QUEUE = new LinkedBlockingQueue<Object>(10);
    /**
     * 将信息放入仓库.
     * 
     * @param obj
     * @return
     */
    public static void offer(Object obj) {
        try {
            LOCAL_QUEUE.offer(obj, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error("插入队列失败,obj= " + resolveObj(obj));
            LOGGER.error("入队失败,obj=" + obj, e);
        }

    }

    /**
     * 出队.
     * <p>
     * 如果队列为空，等待5秒，仍为空,返回<code>null</code>
     * </p>
     * 
     * @see  java.util.concurrent.LinkedBlockingQueue#poll(long, TimeUnit)
     * @return
     */
    public static Object poll() {
        try {
        	Object object=LOCAL_QUEUE.poll(5, TimeUnit.SECONDS);
            return object;
        } catch (InterruptedException e) {
            LOGGER.error("出队失败", e);
            return null;
        }
    }
    /**
     * 
     * 
     * @param obj
     * @return
     */
    private static String resolveObj(Object obj) {
        String value = "";
        if (obj != null) {
            if (obj instanceof String || obj instanceof Collection || obj instanceof Map) {
                value = obj.toString();
            } else {
                value = ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
            }
        }
        return value;
    }
    public static int getSize(){
    	return LOCAL_QUEUE.size();
    }
}
