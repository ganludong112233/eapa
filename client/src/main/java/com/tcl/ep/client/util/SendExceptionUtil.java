package com.tcl.ep.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tcl.ep.client.localcache.ExceptionInfoLocalRepository;
import com.tcl.ep.client.model.ExceptionInfo;

/**
 * <p>
 * 该类主要用于异步发送客户端收集的系统信息
 * </p>
 * 
 * @author huan.yang
 * 
 */
public class SendExceptionUtil {

	private static final Logger LOG = LoggerFactory.getLogger(SendExceptionUtil.class);

	private static Executor executor = Executors.newFixedThreadPool(2);

	private static int size = 5;

	private static long periodTime = 3000;

	private static long lastExecuteTime;

	/**
	 * 
	 * 
	 * @return
	 */
	private static List<ExceptionInfo> restriveData() {

		List<ExceptionInfo> params = new ArrayList<ExceptionInfo>();
		try {
			ExceptionInfo obj = (ExceptionInfo) ExceptionInfoLocalRepository.poll();
			while (null != obj) {
				params.add(obj);

				if (params.size() >= size || checkTime()) {
					lastExecuteTime = System.currentTimeMillis();
					break;
				}

				obj = (ExceptionInfo) ExceptionInfoLocalRepository.poll();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return params;
	}

	private static boolean checkTime() {
		return System.currentTimeMillis() > (lastExecuteTime + periodTime);
	}

	/**
	 * 异步发送消息
	 * 
	 * @param models
	 */
	private static void resolveData(List<ExceptionInfo> models) {
		LOG.debug("Exception Message is :{}" + models.toString());
		try {
			HttpClientUtil.postJson(ClientConfiguration.getServiceUrl(), JSON.toJSONString(models));
		} catch (Exception e1) {
			LOG.error(e1.getMessage(), e1);
		}

	}

	public static void send() {
        int crentsize=ExceptionInfoLocalRepository.getSize();
		if (crentsize>= size) {

			executor.execute(new Runnable() {

				@Override
				public void run() {

					while (true) {
						try {
							List<ExceptionInfo> models = restriveData();
							if (CollectionUtils.isEmpty(models)) {
								continue;
							}
							LOG.info("异常信息异步发送消息，消费条数:{}", models.size());
							resolveData(models);
						} catch (Exception e) {
							LOG.error(e.getMessage(), e);
						}
					}
				}

			});
		}
	}

}
