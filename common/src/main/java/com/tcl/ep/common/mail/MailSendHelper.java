package com.tcl.ep.common.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSendHelper {
    private static final Logger LOG = LoggerFactory.getLogger(MailSendHelper.class);

    /**
     * sendFrom,sendName,mailHost,sendList,subject,content不可为空
     * <p>
     * 
     * @param mailModel
     * @return true发送成功
     * @throws Exception
     */
    public static boolean sendHtmlMsg(MailParamsModel mailModel) throws Exception {

        // 必要的参数校验
        validator(mailModel);

        LOG.info("开始发送邮件，邮件发送列表，sendList:{}", mailModel.getSendList());
        HtmlEmail email = new HtmlEmail();
        try {
            email.setFrom(mailModel.getSendFrom());
        } catch (EmailException e1) {
            LOG.info("发送方账号异常，mail:{}，e:{}", mailModel.getSendFrom(), e1.getMessage());
            throw new RuntimeException("发送方账号异常，sendFrom=" + mailModel.getSendFrom());
        }
        email.setAuthentication(mailModel.getSendName(), mailModel.getMailPassword());
        email.setHostName(mailModel.getMailHost());
        email.setSmtpPort(mailModel.getMailPort());

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");

        email.setHeaders(headers);

        List<String> sendList = mailModel.getSendList();
        for (String toEmail : sendList) {
            try {
                // 单个接收方邮箱账号异常，继续
                email.addTo(toEmail);
            } catch (EmailException e) {
                LOG.info("接收方账号异常，mail:{}，e:{}", toEmail, e.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(mailModel.getCcList())) {
            List<String> ccList = mailModel.getCcList();
            for (String ccEmail : ccList) {
                try {
                    // 单个接收方(CC)邮箱账号异常，继续
                    email.addCc(ccEmail);
                } catch (EmailException e) {
                    LOG.info("接收方(CC)账号异常，mail:{}，e:{}", ccEmail, e.getMessage());
                }
            }
        }

        email.setCharset("UTF-8");
        email.setSubject(mailModel.getSubject());
        try {
            email.setHtmlMsg(mailModel.getContent());
            email.send();
        } catch (EmailException e) {
            LOG.info("邮件发送失败，mail:{}，e:{}", email, e.getMessage(), e);
            throw new RuntimeException("邮件发送失败，ErrorMsg=" + e.getMessage());
        }
        LOG.info("邮件发送成功");
        return true;
    }

    private static void validator(MailParamsModel mailModel) {
        if (mailModel == null) {
            throw new RuntimeException("无效的邮件发送对象, mailModel=" + mailModel);
        }
        if (StringUtils.isBlank(mailModel.getSendName())) {
            throw new RuntimeException("无效的邮件发送名称，sendName=" + mailModel.getSendName());
        }
        if (StringUtils.isBlank(mailModel.getSendFrom())) {
            throw new RuntimeException("无效的邮件发送方，sendFrom=" + mailModel.getSendFrom());
        }
        if (StringUtils.isBlank(mailModel.getMailHost())) {
            throw new RuntimeException("无效的邮件协议主机，mailHost=" + mailModel.getMailHost());
        }
        if (CollectionUtils.isEmpty(mailModel.getSendList())) {
            throw new RuntimeException("无效的邮件接收方，sendList=" + mailModel.getSendList());
        }
        if (StringUtils.isBlank(mailModel.getSubject())) {
            throw new RuntimeException("无效的邮件标题，subject=" + mailModel.getSubject());
        }
        if (StringUtils.isBlank(mailModel.getContent())) {
            throw new RuntimeException("无效的邮件内容，content=" + mailModel.getContent());
        }
    }

}
