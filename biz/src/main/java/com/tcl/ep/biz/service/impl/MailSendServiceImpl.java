package com.tcl.ep.biz.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tcl.ep.biz.service.MailSendService;
import com.tcl.ep.common.mail.MailParamsModel;
import com.tcl.ep.common.mail.MailSendHelper;

@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService {

	private static final Logger LOG = LoggerFactory
			.getLogger(MailSendServiceImpl.class);
	@Value("${send.mail.from}")
	private String SEND_FROM;
	@Value("${send.mail.name}")
	private String SEND_NAME;
	@Value("${send.mail.password}")
	private String MAIL_PASSWORD;
	@Value("${send.mail.host}")
	private String MAIL_HOST;
	@Value("${send.mail.port}")
	private int MAIL_PORT;

	@Override
	public boolean sendHtmlMsg(String subject, String content,
			List<String> sendList, List<String> ccList) {

		MailParamsModel model = new MailParamsModel();
		model.setSendFrom(SEND_FROM).setSendName(SEND_NAME)
				.setMailPassword(MAIL_PASSWORD).setMailHost(MAIL_HOST)
				.setMailPort(MAIL_PORT).setSendList(sendList).setCcList(ccList)
				.setSubject(subject).setContent(content);

		boolean result;
		try {
			result = MailSendHelper.sendHtmlMsg(model);
			return result;
		} catch (Exception e) {
			LOG.error("邮件发送失败，msg:{}", e.getMessage());
			return false;
		}
	}

}
