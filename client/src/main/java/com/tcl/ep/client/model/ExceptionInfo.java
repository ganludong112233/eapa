package com.tcl.ep.client.model;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

public class ExceptionInfo {

    private String exceptionName;
    private Long occurTime;
    private String parameter;
    private String module;
    private String errorMsg;
    private String uri;
    private String headers;
    private String requestMethod;
    private Long projectId;
    private String handlerMethod;
    private String handlerClass;
    private Integer handlerLine;
    private String environment;
    private String extraInformation;

    public ExceptionInfo() {

    }

    public ExceptionInfo(HttpServletRequest request, Object handler, String errorInfo) {
        setInfoByRequest(request);
        setInfoByHandler(handler);

        this.errorMsg = errorInfo;
    }

    public String getErrorMessage() {
        return errorMsg;
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
        return "ExceptionInfo{" + "projectId='" + projectId + '\'' +",errorMessage='" + errorMsg + '\'' + ", uri='" + uri + '\''
                + ", params='" + parameter + '\'' + ", headers='" + headers + '\''
                + ", requestMethod='" + requestMethod + '\'' + ", moduleName='" + module + '\''
                + ", handlerClass='" + handlerClass + '\'' + ", handlerMethod='" + handlerMethod
                + '\'' + ", handlerLine='" + handlerLine
                + '\'' + '}';
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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getExtraInformation() {
		return extraInformation;
	}

	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}
}
