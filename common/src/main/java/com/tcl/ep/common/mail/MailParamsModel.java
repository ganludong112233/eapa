package com.tcl.ep.common.mail;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MailParamsModel {
	/**
	 * 发送方(xxxxx@xx.com)
	 */
	private String sendFrom;
	/**
	 * 发送方(xxxxx@xx.com)
	 */
	private String sendName;
	/**
	 * 邮箱密码(xxxxxx)
	 */
	private String mailPassword;
	/**
	 * smtp主机地址(smtp.126.com)
	 */
	private String mailHost;
	/**
	 * 端口号
	 */
	private int mailPort;
	/**
	 * 邮件标题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;

	/**
	 * 发送邮件列表
	 */
	private List<String> sendList;
	/**
	 * 抄送邮件列表
	 */
	private List<String> ccList;
	public List<String> getCcList() {
		return ccList;
	}
	public String getContent() {
		return content;
	}

	public String getMailHost() {
		return mailHost;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	
	public int getMailPort() {
		return mailPort;
	}

	public String getSendFrom() {
		return sendFrom;
	}

	public List<String> getSendList() {
		return sendList;
	}

	public String getSendName() {
		return sendName;
	}

	public String getSubject() {
		return subject;
	}

	public MailParamsModel setCcList(List<String> ccList) {
		this.ccList = ccList;
		return this;
	}

	public MailParamsModel setContent(String content) {
		this.content = content;
		return this;
	}

	public MailParamsModel setMailHost(String mailHost) {
		this.mailHost = mailHost;
		return this;
	}

	public MailParamsModel setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
		return this;
	}

	public MailParamsModel setMailPort(int mailPort) {
		this.mailPort = mailPort;
		return this;
	}

	public MailParamsModel setSendFrom(String sendFrom) {
		this.sendFrom = sendFrom;
		return this;
	}

	public MailParamsModel setSendList(List<String> sendList) {
		this.sendList = sendList;
		return this;
	}

	public MailParamsModel setSendName(String sendName) {
		this.sendName = sendName;
		return this;
	}

	public MailParamsModel setSubject(String subject) {
		this.subject = subject;
		return this;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
