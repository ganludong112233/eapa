package com.tcl.ep.biz.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tcl.ep.biz.service.ExceptionInService;
import com.tcl.ep.biz.service.MailSendService;
import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.common.exception.ServiceException;
import com.tcl.ep.common.mail.MailTemplate;
import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.dao.ExceptionInfoDao;
import com.tcl.ep.persistence.entity.ExceptionInfo;
import com.tcl.ep.persistence.entity.Project;
import com.tcl.ep.persistence.vo.ExceptionInfoVo;
import com.tcl.ep.persistence.vo.ExceptionSearchReq;

@Service
public class ExceptionInServiceImpl implements ExceptionInService {
    private static Logger LOG = LoggerFactory.getLogger(ExceptionInServiceImpl.class);

    @Resource
    private ExceptionInfoDao exceptionInfoDao;
    @Resource
    private ProjectService projectService;
    @Resource
    private MailSendService mailSendService;
    @Value("${send.mail.template}")
    private String MAIL_TEMPLATE;

    @Override
    public int addExceptionInfo(List<ExceptionInfo> exceptionInfos) {

        LOG.debug("save: exceptionInfo={}", exceptionInfos.toString());
        int count = 0;
        Project project = projectService.findByProjectId(exceptionInfos.get(0).getProjectId());
        if (project == null) {
            LOG.error("projectId={}info is not exsist", exceptionInfos.get(0).getProjectId());
            throw new ServiceException(400, "projectId=" + exceptionInfos.get(0).getProjectId()
                    + " info is not exsist");
        }
        for (ExceptionInfo exceptionInfo : exceptionInfos) {
            exceptionInfoDao.insert(exceptionInfo);
            count++;
        }
        for (ExceptionInfo exceptionInfo : exceptionInfos) {
            sendEmails(project, exceptionInfo);
        }
        return count;
    }

    @SuppressWarnings("unused")
    private void sendMessages(Project project, ExceptionInfo exceptionInfo) {

    }

    private void sendEmails(Project project, ExceptionInfo exceptionInfo) {

        try {
            Long projectId = project.getProjectId();
            String projectName = project.getProjectName();
            String module = exceptionInfo.getModule()==null?"":exceptionInfo.getModule();
            String exceptionName = exceptionInfo.getExceptionName();
            String errorMsg = exceptionInfo.getErrorMsg().replace("\\n", "<br>");
            String uri = exceptionInfo.getUri() == null ? "" : exceptionInfo.getUri();
            String headers = exceptionInfo.getHeaders() == null ? "" : exceptionInfo.getHeaders();
            String parameter =
                    exceptionInfo.getParameter() == null ? "" : exceptionInfo.getParameter();
            String requestMethod =
                    exceptionInfo.getRequestMethod() == null ? "" : exceptionInfo
                            .getRequestMethod();
            String handlerMethod =
                    exceptionInfo.getHandlerMethod() == null ? "" : exceptionInfo
                            .getHandlerMethod();
            String handlerClass =
                    exceptionInfo.getHandlerClass() == null ? "" : exceptionInfo.getHandlerClass();
            String ip = exceptionInfo.getIp() == null ? "" : exceptionInfo.getIp();
            // Long occurTime=exceptionInfo.getOccurTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String occurTime = sdf.format(new Date(Long.valueOf(exceptionInfo.getOccurTime())));

            List<String> sendList = new ArrayList<String>();
            if (StringUtils.isNotBlank(project.getToEmails())) {
                String mails[] = StringUtils.split(project.getToEmails(), ",");
                for (String mail : mails) {
                    sendList.add(mail);
                }
            }
            // 多对象发送时，统一称呼，一个邮箱时准确称呼
            String name = "Owner(s)";
            if (CollectionUtils.isNotEmpty(sendList) && sendList.size() == 1) {
                String mail = sendList.get(0);
                if (StringUtils.isNotBlank(mail)) {
                    name = mail.substring(0, mail.indexOf("@"));
                }
            }

            String content = MailTemplate.getSendTemplate(MAIL_TEMPLATE);
            String subject = "【Exception Warn】-"+projectName+"("+exceptionInfo.getEnv()+")";
            if (StringUtils.isNotBlank(content)) {
                content =
                        content.replace(MailTemplate.TARGET_NAME, name)
                                .replace(MailTemplate.PROJECT_ID, String.valueOf(projectId))
                                .replace(MailTemplate.PROJECT_NAME, projectName)
                                .replace(MailTemplate.MODULE, module)
                                .replace(MailTemplate.EXCEPTION_NAME, exceptionName)
                                .replace(MailTemplate.ERROR_MESSAGE, errorMsg)
                                .replace(MailTemplate.REQUEST_URI, uri)
                                .replace(MailTemplate.HEADERS, headers)
                                .replace(MailTemplate.PARAMTER, parameter)
                                .replace(MailTemplate.REQUEST_METHOD, requestMethod)
                                .replace(MailTemplate.HANDLER_CLASS, handlerClass)
                                .replace(MailTemplate.HANDLER_METHOD, handlerMethod)
                                .replace(MailTemplate.IP, ip)
                                .replace(MailTemplate.OCCUR_TIME, occurTime);
            } else {
                LOG.info("邮件模板未正常获取，templateName=" + MAIL_TEMPLATE);
                content = "异常项目id：" + projectId;
            }
            mailSendService.sendHtmlMsg(subject, content, sendList, null);
        } catch (Exception e) {
            LOG.error("异常报警邮件发送失败， message:{}", exceptionInfo.toString(), e);
        }
    }

    @Override
    public Page<ExceptionInfoVo> findList(ExceptionSearchReq params) {
        LOG.debug("Search parameters are:{}", params);
        Integer totalCount = exceptionInfoDao.findTotal(params);
        List<ExceptionInfoVo> items = new ArrayList<ExceptionInfoVo>();
        if (totalCount != null && totalCount > 0) {
            items = exceptionInfoDao.findList(params);
            for (ExceptionInfoVo exceptionInfoVo : items) {
                String handlerClass = exceptionInfoVo.getHandlerClass();
                int start = handlerClass.lastIndexOf(".");
                handlerClass = handlerClass.substring(start + 1, handlerClass.length());
                exceptionInfoVo.setHandlerClass(handlerClass);
                String errorMsg = exceptionInfoVo.getErrorMsg();
                if (StringUtils.isNotEmpty(params.getErrorMsg())
                        && StringUtils.isNotEmpty(errorMsg)) {
                    int errorPoint = errorMsg.indexOf(params.getErrorMsg());
                    int erroStart = errorPoint - 20 > 0 ? errorPoint - 20 : 0;
                    int erroEnd =
                            errorMsg.length() - errorPoint >= 20 ? errorPoint + 20 : errorMsg
                                    .length();
                    errorMsg = "..." + errorMsg.substring(erroStart, erroEnd) + "...";
                    exceptionInfoVo.setErrorMsg(errorMsg);
                } else {
                    exceptionInfoVo.setErrorMsg(exceptionInfoVo.getErrorMsg().substring(0, 20)
                            + "...");
                }
            }
        } else {
            totalCount = new Integer(0);
        }
        Page<ExceptionInfoVo> expos =
                new Page<>(totalCount, params.getPageNo(), params.getPageSize());
        expos.setRecordList(items);
        return expos;
    }

    @Override
    public ExceptionInfoVo findDetail(Long excepionInfoId) {

        return exceptionInfoDao.findById(excepionInfoId);
    }

    @Override
    public List<String> findModules(Long projectId) {

        return exceptionInfoDao.findModules(projectId);
    }

}
