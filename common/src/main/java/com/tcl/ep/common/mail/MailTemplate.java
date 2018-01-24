package com.tcl.ep.common.mail;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MailTemplate {

	private static final Logger LOG = LoggerFactory.getLogger(MailTemplate.class);
	
	public static final String TARGET_NAME = "%TARGET%";
	public static final String PROJECT_ID = "%PROJECT_ID%";
	public static final String PROJECT_NAME = "%PROJECT_NAME%";
	public static final String MODULE = "%MODULE%";
	public static final String EXCEPTION_NAME = "%EXCEPTION_NAME%";
	public static final String REQUEST_URI = "%REQUEST_URI%";
	public static final String REQUEST_METHOD = "%REQUEST_METHOD%";
	public static final String HANDLER_CLASS = "%HANDLER_CLASS%";
	public static final String PARAMTER = "%PARAMTER%";
	public static final String HEADERS = "%HEADERS%";
	public static final String HANDLER_METHOD = "%HANDLER_METHOD%";
	public static final String ERROR_MESSAGE = "%ERROR_MESSAGE%";
	public static final String OCCUR_TIME = "%OCCUR_TIME%";
	public static final String IP = "%IP%";

	public static String getSendTemplate(String templateFileName) {
		InputStream instream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templateFileName);
		try {
			return IOUtils.toString(instream);
		} catch (Exception e) {
			LOG.error("获取邮件模板失败，templateFileName=" + templateFileName, e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(instream);
		}
		return null;
	}
}
