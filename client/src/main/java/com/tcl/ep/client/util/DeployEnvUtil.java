package com.tcl.ep.client.util;

public class DeployEnvUtil {
	static final String ENV_KEY = "server.env";

	/**
	 * Get the deployed env
	 * 
	 * @author yliu
	 * @date Oct 23, 2014 5:16:50 PM
	 * @return
	 */
	public static String getDeployedEnv() {
		String devEnv = "DEV";
		String sysEnvVal = ConfigUtil.getServerConfig().getString(ENV_KEY);
		if (sysEnvVal == null) {
			return devEnv;
		}
		if (sysEnvVal.equalsIgnoreCase("UAT")) {
			return "UAT";
		}
		if (sysEnvVal.equalsIgnoreCase("PROD")) {
			return "PROD";
		}
		return devEnv;
	}

	/**
	 * 当前部署环境是否是正式产品线
	 * 
	 * @return
	 */
	public static boolean isProductEnv() {
		return DeployEnvUtil.getDeployedEnv().equals("PROD");
	}
}
