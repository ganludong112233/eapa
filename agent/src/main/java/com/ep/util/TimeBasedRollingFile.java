package com.ep.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.ep.config.AbstractConfig;

public class TimeBasedRollingFile {

	private String outputFile = null;
	private BufferedWriter bw = null;
	// private OutputStream os = null;
	private long nextRollingTime;
	private int retainDays = 0;
	// Underlying StreamEncoder can't change the buffersize(8192) need invoke
	// flush() method through out caller
	private int bufferSize = 0;
	private int actualSize = 0;

	public TimeBasedRollingFile(String outputFile, int retainDays) {
		this.retainDays = retainDays;
		nextRollingTime = DateUtil.getNextDayStartTime();
		this.outputFile = outputFile;
		bufferSize = AbstractConfig.getFileOutBufferSize();
		openStream(outputFile);
		checkRolling();
	}

	private void openStream(String outputFile) {
		try {
			// os = new FileOutputStream(outputFile, true);
			bw = new BufferedWriter(new FileWriter(new File(outputFile), true));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void checkRolling() {
		File file = new File(outputFile);
		if (!file.exists()) {
			return;
		}

		if (file.lastModified() < DateUtil.getTodayStartTime()) {
			doRolling(file.lastModified());
		}
	}

	public void append(String content, long time) {
		triggerRolling(time);
		try {
			actualSize += content.length();
			// os.write(content.getBytes());
			// os.flush();
			bw.write(content);

			// don't need to take multiple thread into consideration.
			if (actualSize >= bufferSize) {
				bw.flush();
				actualSize = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void triggerRolling(long time) {
		if (time < this.nextRollingTime) {
			// should not to rolling
			return;
		}

		doRolling(DateUtil.getLastDayTime(time));

	}

	/**
	 * roll the file to the date of giving time
	 * 
	 * @param time
	 */
	public void doRolling(long time) {
		try {
			// close the old stream
			// os.close();
			bw.close();
			// move the file to new filename
			FileUtil.renameFile(outputFile, getArchiveFileName(time));
		} catch (IOException e) {
			e.printStackTrace();
		}
		openStream(outputFile);
		nextRollingTime = DateUtil.getNextDayStartTime();
		trigger2DeleteObosoletedLog();
	}

	/**
	 * the result will look alike "/date/log/tomcat_2015-22-44.log"
	 * 
	 * @param date
	 * @return the last day file name
	 */
	public String getArchiveFileName(long time) {
		String fileName = FileUtil.getFilePathExtractedSuffix(outputFile);
		// calculate the archive date
		String suffix = FileUtil.getFilePathSuffix(outputFile);
		String suffixName = "";
		if (!suffix.equals("")) {
			suffixName = "." + suffix;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);

		String dateStr = DateUtil.simpleFormat2Str(calendar.getTimeInMillis());
		return fileName + "_" + dateStr + suffixName;
	}

	private void trigger2DeleteObosoletedLog() {
		String fileNamePrefix = FileUtil.getFileNamePrefix(outputFile);
		String filePath = FileUtil.getDirectoryPath(outputFile);

		Map<String, Object> validFilePrefixes = new HashMap<String, Object>();
		validFilePrefixes.put(fileNamePrefix, null);

		for (int i = 1; i <= retainDays; i++) {
			validFilePrefixes.put(
					fileNamePrefix
							+ "_"
							+ DateUtil.simpleFormat2Str(DateUtil
									.getBeforeDayTime(i)), null);
		}

		File logFiles = new File(filePath);
		for (File log : logFiles.listFiles()) {
			if (!log.getName().startsWith(fileNamePrefix)) {
				continue;
			}

			String filePrefix = FileUtil.getFileNamePrefix(log.getName());
			if (!validFilePrefixes.containsKey(filePrefix)) {
				log.delete();
			}
		}
	}
}
