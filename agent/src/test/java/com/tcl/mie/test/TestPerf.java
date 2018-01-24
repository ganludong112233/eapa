package com.tcl.mie.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

import com.ep.jdbc.Target;

// @Controller
// @Service
public class TestPerf {
	public static void main(String[] args) {
		System.out.println("fsajldfjasldfjlasfj ");
		// testWriteFile1();
		List<Thread> threadList = new ArrayList<Thread>();
		int threadCount = 1;
		for (int i = 1; i <= threadCount; i++) {
			Thread testThread = new Thread() {
				public void run() {
					while (true) {
						// System.out.println(Thread.currentThread()
						// .isInterrupted());
						// LockSupport.parkNanos(1000000L);

						if (Thread.currentThread().isInterrupted()) {
							break;
						}
						try {
							// LockSupport.parkNanos(100000000L);
							// System.out.println(getTarget("fasdfasdfasd")
							// .getContent());
							getTarget("fasdfasdfasd").getContent();
							testException();
						} catch (Exception e) {
						}
					}
				}

				public void testException() {
					throw new RuntimeException(String.valueOf(System
							.currentTimeMillis()));
				}
			};
			// -javaagent:/data/develop/workspace/java/eapa/agent/target/agent-jar-with-dependencies.jar
			testThread.start();
			threadList.add(testThread);
		}
		// LockSupport.parkNanos(10000000000L);
		// System.out.println("abbbbbbbbbbbbbbbbbbbbbbbbb#############aaa`");
		//
		// for (Thread testThread : threadList) {
		// testThread.interrupt();
		// }
		LockSupport.park();
	}

	public static Target getTarget(String xx) {
		return new Target();
	}

	// public static void testWriteFile1() {
	//
	// for (int i = 1; i <= 1; i++) {
	// FileUtil.writeToFileByLock("/data/a.log",
	// "xxxxxxxxxxxxxxxxxxxxxxxxx");
	// }
	// throw new RuntimeException("xxxxx");
	// }
	//
	// public static void testWriteFile2() {
	// FileOutputStream fos = null;
	// try {
	// fos = new FileOutputStream("/data/a.log");
	// for (int i = 1; i <= 1000000; i++) {
	// fos.write("xxxxxxxxxxxxxxxxxxxxxxxxx".getBytes());
	// }
	// } catch (IOException e) {
	// } finally {
	// try {
	// if (fos != null)
	// fos.close();
	// } catch (IOException e) {
	// }
	// }
	// }
}
