package com.tcl.ep.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientConfiguration {
	private static Logger logger = LoggerFactory.getLogger(ClientConfiguration.class);
	private static boolean switchStatus = true;
	private static boolean timerStatus = true;
	private static long projectId;
	private static String module;
	private static String serviceUrl;
	private static String sysReportUrl;
	private static String epClientUrlPrefix = "/ep/";
	private static String eapaMquRL;
	private static String eapaMqUserName;
	private static String eapaMqPass;
	private static String eapaMqqueue;
	private final static int socketTimeout = 3000;
	private final static int connectTimeout = 3000;
	private static InputStream inputStream = null;
	private static Properties prop;
	private static String serverEnv = "PROD";

	static {
		inputStream = ClientConfiguration.class.getResourceAsStream("/eapa.properties");
		prop = new Properties();
		try {
			prop.load(inputStream);
			serviceUrl= (String) prop.get("serviceUrl");
			sysReportUrl = (String) prop.get("sysReportUrl");
			eapaMquRL=(String) prop.get("eapaMquRL");
			eapaMqUserName=(String) prop.get("eapaMqUserName");
			eapaMqPass=(String) prop.get("eapaMqPass");
			eapaMqqueue=(String) prop.get("eapaMqqueue");
					
		} catch (IOException e1) {
			logger.error("io exception occur while reading properties.");
			try {
				inputStream.close();
			} catch (IOException e11) {
			}
		}
		try {
			serverEnv = DeployEnvUtil.getDeployedEnv();
		} catch (Exception e) {
			logger.info("EAPA read serverEnv faliure");
		}
		logger.info("EAPA detected the serverEnv is {}.", serverEnv);
	}

	public static boolean isSwitchStatus() {
		return switchStatus;
	}

	public static void setSwitchStatus(boolean switchStatus) {
		ClientConfiguration.switchStatus = switchStatus;
	}

	public static boolean isTimerStatus() {
		return timerStatus;
	}

	public static void setTimerStatus(boolean timerStatus) {
		ClientConfiguration.timerStatus = timerStatus;
	}

	public static long getProjectId() {
		return projectId;
	}

	public static void setProjectId(long projectId) {
		ClientConfiguration.projectId = projectId;
	}

	public static String getModule() {
		return module;
	}

	public static void setModule(String module) {
		ClientConfiguration.module = module;
	}

	public static String getServiceUrl() {
		return serviceUrl;
	}

	public static void setServiceUrl(String serviceUrl) {
		ClientConfiguration.serviceUrl = serviceUrl;
	}

	public static String getSysReportUrl() {
		return sysReportUrl;
	}

	public static void setSysReportUrl(String sysReportUrl) {
		ClientConfiguration.sysReportUrl = sysReportUrl;
	}

	public static String getEpClientUrlPrefix() {
		return epClientUrlPrefix;
	}

	public static void setEpClientUrlPrefix(String epClientUrlPrefix) {
		ClientConfiguration.epClientUrlPrefix = epClientUrlPrefix;
	}

	public static int getSockettimeout() {
		return socketTimeout;
	}

	public static int getConnecttimeout() {
		return connectTimeout;
	}

	public static String getServerEnv() {
		return serverEnv;
	}

	public static void setServerEnv(String serverEnv) {
		ClientConfiguration.serverEnv = serverEnv;
	}

	public static String getEapaMquRL() {
		return eapaMquRL;
	}

	public static String getEapaMqUserName() {
		return eapaMqUserName;
	}

	public static String getEapaMqPass() {
		return eapaMqPass;
	}

	public static String getEapaMqqueue() {
		return eapaMqqueue;
	}

	

}
