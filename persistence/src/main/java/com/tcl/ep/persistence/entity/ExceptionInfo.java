package com.tcl.ep.persistence.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ExceptionInfo {
	
	private String ip;
	private Long id;
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
	private String env;
	private String extraInformation;

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

	public Long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getModule() {
		return module;
	}

	public Long getOccurTime() {
		return occurTime;
	}

	public String getParameter() {
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getExtraInformation() {
		return extraInformation;
	}

	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	
}