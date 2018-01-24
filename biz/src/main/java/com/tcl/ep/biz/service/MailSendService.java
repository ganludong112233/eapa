package com.tcl.ep.biz.service;

import java.util.List;

public interface MailSendService {
	/**
	 * @param subject 不可空
	 * @param content 不可空
	 * @param sendList 不可空
	 * @param ccList 可空
	 * @return true|false
	 */
	boolean sendHtmlMsg(String subject, String content, List<String> sendList, List<String> ccList);
}
