package com.ep.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class SystemInfoUtil {
	private static Logger logger = LoggerFactory.getLogger(SystemInfoUtil.class);
	/**
	 * 功能：内存使用率
	 * */
	public static String memoryUsage() {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null)
					break;
				int beginIndex = 0;
				int endIndex = line.indexOf(":");
				if (endIndex != -1) {
					String key = line.substring(beginIndex, endIndex);
					beginIndex = endIndex + 1;
					endIndex = line.length();
					String memory = line.substring(beginIndex, endIndex);
					String value = memory.replace("kB", "").trim();
					map.put(key, value);
				}
			}

			long memTotal = Long.parseLong(map.get("MemTotal").toString());
			long memFree = Long.parseLong(map.get("MemFree").toString());
			long memused = memTotal - memFree;
			long buffers = Long.parseLong(map.get("Buffers").toString());
			long cached = Long.parseLong(map.get("Cached").toString());
			float usage = (float) (memused - buffers - cached) / memTotal;
			DecimalFormat df = new DecimalFormat(".0");
			String value = df.format(usage * 100);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return "0";
	}
	
	/**
	 * 获取分区的使用占用率
	 * 
	 * @param path
	 * @return
	 */
	public static String getPartitionUsage(String path) {
		File f = new File(path);
		long total = f.getTotalSpace();
		long free = f.getFreeSpace();
		long used = total - free;
		float usage = (float) used / total;
		DecimalFormat df = new DecimalFormat(".0");
		String value = df.format(usage * 100);
		return value;
	}
	
	public static void basicInfo() {
		int kb = 1024;
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		// 操作系统
		String osName = System.getProperty("os.name");
		// 总的物理内存
		long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / kb;
		// 剩余的物理内存
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / kb;
		// 已使用的物理内存
		long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb
				.getFreePhysicalMemorySize()) / kb;
		System.out.println("操作系统:" + osName + "总的物理内存:" + totalMemorySize
				+ "剩余物理内存:" + freePhysicalMemorySize + "已使用的物理内存" + usedMemory);
	}
	
	/**
	 * 功能：获取Linux系统cpu使用率
	 * */
	public static String cpuUsage() {
		try {
			Map<?, ?> map1 = cpuinfo();
			Thread.sleep(1 * 1000);
			Map<?, ?> map2 = cpuinfo();

			long user1 = Long.parseLong(map1.get("user").toString());
			long nice1 = Long.parseLong(map1.get("nice").toString());
			long system1 = Long.parseLong(map1.get("system").toString());
			long idle1 = Long.parseLong(map1.get("idle").toString());

			long user2 = Long.parseLong(map2.get("user").toString());
			long nice2 = Long.parseLong(map2.get("nice").toString());
			long system2 = Long.parseLong(map2.get("system").toString());
			long idle2 = Long.parseLong(map2.get("idle").toString());

			long total1 = user1 + system1 + nice1;
			long total2 = user2 + system2 + nice2;
			float total = total2 - total1;

			long totalIdle1 = user1 + nice1 + system1 + idle1;
			long totalIdle2 = user2 + nice2 + system2 + idle2;
			float totalidle = totalIdle2 - totalIdle1;

			DecimalFormat df = new DecimalFormat(".0");
			float cpusage = (float) (total / totalidle) * 100;
			String value = df.format(cpusage);
			return value;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "0";
	}
	/**
	 * 功能：CPU使用信息
	 * */
	public static Map<?, ?> cpuinfo() {
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null) {
					break;
				}
				if (line.startsWith("cpu")) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					List<String> temp = new ArrayList<String>();
					while (tokenizer.hasMoreElements()) {
						String value = tokenizer.nextToken();
						temp.add(value);
					}
					map.put("user", temp.get(1));
					map.put("nice", temp.get(2));
					map.put("system", temp.get(3));
					map.put("idle", temp.get(4));
					map.put("iowait", temp.get(5));
					map.put("irq", temp.get(6));
					map.put("softirq", temp.get(7));
					map.put("stealstolen", temp.get(8));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return map;
	}
@SuppressWarnings("unused")
private static Map<String, String> getTomcatInfo(int port){
	Map<String, String> resultMap=new HashMap<String, String>();
	Runtime r = Runtime.getRuntime();
	Process pro1 ,pro2,pro3;
	try {
		String portCommand="netstat -anp|grep"+port;
		pro1 = r.exec(portCommand);
		BufferedReader in1 = new BufferedReader(new InputStreamReader(
				pro1.getInputStream()));
		String line = null;
		String pid;
		while ((line = in1.readLine()) != null) {
			line = line.trim();
			String[] temp = line.split("\\s+");
			String pidinfo=temp[temp.length-1];
			String regEx="[^0-9]";   
			Pattern p = Pattern.compile(regEx);   
			Matcher m = p.matcher(pidinfo);   
			logger.debug("pid is :"+m.replaceAll("").trim());
			pid=m.replaceAll("").trim();
			resultMap.put("pid", pid);
			String threadCommand="cat /proc/"+pid+"/status";
			pro2=r.exec(threadCommand);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(
					pro2.getInputStream()));
			String line2=null;
			while ((line2 = in2.readLine()) != null) {
				if(line2.startsWith("Threads:")){
					String[] temp2=line2.split("\\s+");
					resultMap.put("threads", temp2[2]);
				}		
			}	
		}
		//查看连接数
		pro3=r.exec("netstat -na | grep ESTAB | grep "+port+" | wc -l");
		BufferedReader in3 = new BufferedReader(new InputStreamReader(
				pro3.getInputStream()));
		resultMap.put("connectionNum", in3.readLine());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return resultMap;
}
	/**
	 * 获取带宽使使用率
	 * @return
	 */
	public static String getNetworkSpeed() {
		float TotalBandwidth = 1000;
		System.out.println("开始收集网络带宽使用率");
		float netUsage = 0.0f;
		Process pro1,pro2;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "cat /proc/net/dev";
			//第一次采集流量数据
			long startTime = System.currentTimeMillis();
			pro1 = r.exec(command);
			BufferedReader in1 = new BufferedReader(new InputStreamReader(pro1.getInputStream()));
			String line = null;
			long inSize1 = 0, outSize1 = 0;
			while((line=in1.readLine()) != null){
				line = line.trim();
				if(line.startsWith("eth0")){
					System.out.println(line);
					String[] temp = line.split("\\s+");
					inSize1 = Long.parseLong(temp[1].substring(5)); //Receive bytes,单位为Byte
					outSize1 = Long.parseLong(temp[9]);             //Transmit bytes,单位为Byte
					break;
				}
			}
			in1.close();
			pro1.destroy();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				System.out.println("NetUsage休眠时发生InterruptedException. " + e.getMessage());
				System.out.println(sw.toString());
			}
			//第二次采集流量数据
			long endTime = System.currentTimeMillis();
			pro2 = r.exec(command);
			BufferedReader in2 = new BufferedReader(new InputStreamReader(pro2.getInputStream()));
			long inSize2 = 0 ,outSize2 = 0;
			while((line=in2.readLine()) != null){
				line = line.trim();
				if(line.startsWith("eth0")){
					System.out.println(line);
					String[] temp = line.split("\\s+");
					inSize2 = Long.parseLong(temp[1].substring(5));
					outSize2 = Long.parseLong(temp[9]);
					break;
				}
			}
			if(inSize1 != 0 && outSize1 !=0 && inSize2 != 0 && outSize2 !=0){
				float interval = (float)(endTime - startTime)/1000;
				//网口传输速度,单位为bps
				float curRate = (float)(inSize2 - inSize1 + outSize2 - outSize1)*8/(1000000*interval);
				netUsage = curRate/TotalBandwidth;
				System.out.println("本节点网口速度为: " + curRate + "Mbps");
				System.out.println("本节点网络带宽使用率为: " + netUsage);
			}
			in2.close();
			pro2.destroy();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			System.out.println("NetUsage发生InstantiationException. " + e.getMessage());
			System.out.println(sw.toString());
		}
		DecimalFormat df = new DecimalFormat(".0");
		String value = df.format(netUsage * 100);
		return value;
	}

	/**
	 * 获取磁盘IO使用率
	 * @return
	 */
	public static String getHardDiskIOPercent() {
		System.out.println("开始收集磁盘IO使用率");
		float ioUsage = 0.0f;
		Process pro = null;
		Runtime r = Runtime.getRuntime();
		try {
			String command = "iostat -d -x";
			pro = r.exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			String line = null;
			int count =  0;
			while((line=in.readLine()) != null){
				if(++count >= 4){
					String[] temp = line.split("\\s+");
					if(temp.length > 1){
						float util =  Float.parseFloat(temp[temp.length-1]);
						ioUsage = (ioUsage>util)?ioUsage:util;
					}
				}
			}
			if(ioUsage > 0){
				System.out.println("本节点磁盘IO使用率为: " + ioUsage);
				ioUsage /= 100;
			}
			in.close();
			pro.destroy();
		} catch (IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			System.out.println("IoUsage发生InstantiationException. " + e.getMessage());
			System.out.println(sw.toString());
		}
		DecimalFormat df = new DecimalFormat(".0");
		String value = df.format(ioUsage * 100);
		return value;
	}
}
