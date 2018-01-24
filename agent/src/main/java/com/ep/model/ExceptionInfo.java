package com.ep.model;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

import com.alibaba.fastjson.JSON;

public class ExceptionInfo {
    private String ip;
    private Long projectId; // 项目Id
    private String env; // 环境
    private String threadId;
    private String exceptionName; // 异常名字
    private Long occurTime; // 发生时间
    private String errorMsg; // 错误信息
    private String handlerMethod; // 抛出异常的方法
    private String handlerClass; // 抛出异常的类

    private String parameter;
    private String module;
    private String uri;
    private String headers;
    private String requestMethod;
    private Integer handlerLine;

    public ExceptionInfo() {

    }

    public ExceptionInfo(HttpServletRequest request, Object handler, String errorInfo) {
        setInfoByRequest(request);
        setInfoByHandler(handler);

        this.errorMsg = errorInfo;
    }

    public String getThreadId() {
        return this.threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public String getHandlerClass() {
        return handlerClass;
    }

    public String getHandlerMethod() {
        return handlerMethod;
    }

    public String getHeaders() {
        return headers;
    }

    public String getModule() {
        return module;
    }

    public String getModuleName() {
        return module;
    }

    public Long getOccurTime() {
        return occurTime;
    }

    public String getParameter() {
        return parameter;
    }

    public String getParams() {
        return parameter;
    }

    public Long getProjectId() {
        return projectId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public void setHandlerClass(String handlerClass) {
        this.handlerClass = handlerClass;
    }

    public void setHandlerMethod(String handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    private String getParamsInfo(HttpServletRequest request) {
        StringBuilder paramInfo = new StringBuilder();

        @SuppressWarnings("unchecked")
        Map<String, String[]> paramMap = request.getParameterMap();

        if (paramMap != null && !paramMap.isEmpty()) {
            paramInfo.append("{");
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                paramInfo.append(entry.getKey()).append("=")
                        .append(Arrays.toString(entry.getValue())).append(", ");
            }
            paramInfo.append("}");
        }

        return paramInfo.toString();
    }

    private void setInfoByHandler(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            handlerClass = hm.getBeanType().getCanonicalName();
            handlerMethod = hm.getMethod().getName();
        } else {
            handlerClass = handler.getClass().getCanonicalName();
            handlerMethod = "default Method";
        }
    }

    private String getHeadersInfo(HttpServletRequest request) {
        StringBuilder paramInfo = new StringBuilder();

        @SuppressWarnings("unchecked")
        Enumeration<String> headNames = request.getHeaderNames();
        if (headNames != null) {
            paramInfo.append("{");
            while (headNames.hasMoreElements()) {
                String key = headNames.nextElement();

                paramInfo.append(key).append("=[").append(request.getHeader(key)).append("], ");
            }
            paramInfo.append("}");
        }

        return paramInfo.toString();
    }

    private void setInfoByRequest(HttpServletRequest request) {

        uri = request.getRequestURI();
        requestMethod = request.getMethod();
        parameter = getParamsInfo(request);
        headers = getHeadersInfo(request);
        module = request.getContextPath();

    }

    @Override
    public String toString() {
        return "ExceptionInfo{" + "projectId='" + projectId + '\'' + ",errorMessage='" + errorMsg
                + '\'' + ", uri='" + uri + '\'' + ", params='" + parameter + '\'' + ", headers='"
                + headers + '\'' + ", requestMethod='" + requestMethod + '\'' + ", moduleName='"
                + module + '\'' + ", handlerClass='" + handlerClass + '\'' + ", handlerMethod='"
                + handlerMethod + '\'' + ", handlerLine='" + handlerLine + '\'' + '}';
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setOccurTime(Long occurTime) {
        this.occurTime = occurTime;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getHandlerLine() {
        return handlerLine;
    }

    public void setHandlerLine(Integer handlerLine) {
        this.handlerLine = handlerLine;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String toJson() {
        // StringBuffer strBuffer = new StringBuffer();

        // strBuffer.append("{");
        // strBuffer.append("\"projectId\":\"" + this.projectId);
        // strBuffer.append(",\"env\":\"" + this.env + "\"");
        // strBuffer.append(",\"threadId\":\"" + this.threadId + "\"");
        // strBuffer.append(",\"exceptionName\":\"" + this.exceptionName + "\"");
        // strBuffer.append(",\"occurTime\":\"" + DateUtil.format2Str(this.occurTime) + "\"");
        // strBuffer.append(",\"errorMsg\":\"" + this.errorMsg + "\"");
        // strBuffer.append(",\"handlerMethod\":\"" + this.handlerMethod + "\"");
        // strBuffer.append(",\"handlerClass\":\"" + this.handlerClass + "\"");
        // strBuffer.append("}");

        // return strBuffer.toString();
        return JSON.toJSONString(this);
    }

}
