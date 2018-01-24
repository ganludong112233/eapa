package com.tcl.mie.test;

import com.ep.model.JVMMonitorInfo;
import com.ep.util.JVMUtil;

import java.util.Map;

/**
 * Created by panmin on 16-12-20.
 */
public class JVMUtilTest {

	public static void main(String[] args) {
		JVMMonitorInfo jvmInfo = new JVMMonitorInfo();
		Map<String, Double> memoryInfo = JVMUtil.getMemoryPoolInfo();
		jvmInfo.setSurvivorUsage(memoryInfo.get("survivorUsage"));
		jvmInfo.setEdenUsage(memoryInfo.get("edenUsage"));
		jvmInfo.setOldUsage(memoryInfo.get("oldUsage"));
		// jvmInfo.setPermUsage(memoryInfo.get("permUsage"));
		jvmInfo.setHeapUsage(JVMUtil.getHeapUsage());
		jvmInfo.setNonHeapUsage(JVMUtil.getNonHeapUsage());
		jvmInfo.setThread(JVMUtil.getThreadInfo());
		System.out.println(jvmInfo.toString());
	}
}
