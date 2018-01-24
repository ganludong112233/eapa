package com.tcl.ep.client.test;

import com.tcl.ep.client.EPClient;
import com.tcl.ep.client.model.SystemMonitorInfo;
import com.tcl.ep.client.util.SystemInfoUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		t1();
	}

	public static void t1() {
		t2();
	}

	public static void t2() {
		testSaveException();
	}

	public static void testSaveException() {
		EPClient.saveException(new RuntimeException(new NullPointerException("TEST")));
	}

	@org.junit.Test
	public void FunctionTest(){
		long time = System.currentTimeMillis();
		System.out.println(getDate(time));
		System.out.println(time);
	}

	public String getDate(long time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS" +
				"");
		String result = sdf.format(time);
		return result;
	}

	@org.junit.Test
	public void systemInfoTest(){

//		System.out.println("/:" + SystemInfoUtil.getPatitionUsage("/"));
//		System.out.println("CPU:" + SystemInfoUtil.cpuUsage());
//		System.out.println("Memory:" + SystemInfoUtil.memoryUsage());
//		SystemInfoUtil.basicInfo();

	}
}
