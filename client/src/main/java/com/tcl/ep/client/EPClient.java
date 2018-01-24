package com.tcl.ep.client;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tcl.ep.client.localcache.ExceptionInfoLocalRepository;
import com.tcl.ep.client.model.ExceptionInfo;
import com.tcl.ep.client.util.ClientConfiguration;
import com.tcl.ep.client.util.ExceptionUtil;
import com.tcl.ep.client.util.SendExceptionUtil;


public class EPClient {
	private final static Logger LOG = LoggerFactory.getLogger(EPClient.class);
	
	/**
	 * There are three types serverEnv that defined as following.
	 * <p>
	 * <li>DEV -- Development</li>
	 * <li>UAT --- test</li>
	 * <li>PROD --- online</li> <br>
	 * 
	 * @param serverEnv
	 */
	public static void setDeplyEnv(String serverEnv) {
		ClientConfiguration.setServerEnv(serverEnv);
	}

	public static void saveException(Exception e) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		SaveExceptionLocal(exceptionInfo, e);
		sendException();
	}
	public static void saveException(Exception e,String extraInfomation) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		exceptionInfo.setExtraInformation(extraInfomation);
		SaveExceptionLocal(exceptionInfo, e);
		sendException();
	}
	public static void saveException(String errorInfo) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		Exception e = new Exception(errorInfo);
		SaveExceptionLocal(exceptionInfo, e);
		sendException();
	}

	public static void saveException(HttpServletRequest request, Exception e) {
		ExceptionInfo exceptionInfo = new ExceptionInfo();
		// get the request info
		exceptionInfo.setRequestMethod(request.getMethod());
		exceptionInfo.setParameter(JSON.toJSONString(request.getParameterMap()));
		exceptionInfo.setUri(request.getRequestURI());
		exceptionInfo.setHeaders(JSON.toJSONString(getHeadersInfo(request)));
		// get the exception info
		SaveExceptionLocal(exceptionInfo, e);
		sendException();
	}

	// create the exception info and the basic info
	private static void SaveExceptionLocal(ExceptionInfo exceptionInfo, Exception e) {
		if (exceptionInfo == null) {
			exceptionInfo = new ExceptionInfo();
		}
		exceptionInfo.setProjectId(ClientConfiguration.getProjectId());
		exceptionInfo.setModule(ClientConfiguration.getModule());
		exceptionInfo.setErrorMsg(ExceptionUtil.getFullExceptionTraceMsg(e));
		Throwable rootCause = ExceptionUtil.getRootCause(e);
		String exceptionName=rootCause.getClass().getCanonicalName().trim();
		int start=exceptionName.lastIndexOf(".");
		exceptionName=exceptionName.substring(start+1, exceptionName.length());
		exceptionInfo.setExceptionName(exceptionName);
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		exceptionInfo.setHandlerClass(stackTraceElements[0].getClassName().trim());
		exceptionInfo.setHandlerMethod(stackTraceElements[0].getMethodName().trim());
		exceptionInfo.setOccurTime(new Date().getTime());
		exceptionInfo.setHandlerLine(stackTraceElements[0].getLineNumber());
		exceptionInfo.setEnvironment(ClientConfiguration.getServerEnv());
		ExceptionInfoLocalRepository.offer(exceptionInfo);
	}

    // get request headers
    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
	// send exception info to the eapa server
	private static void sendException() {
		try {
			SendExceptionUtil.send();
		} catch (Exception e) {
			LOG.info("save exception failure");
		}
	}

}
