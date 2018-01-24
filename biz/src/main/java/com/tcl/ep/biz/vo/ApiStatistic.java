package com.tcl.ep.biz.vo;

import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ApiStatistic {

	private String api;
	private Integer callCount;
	private Integer avgCostTime;
	private Map<String, Object> reponseSpeedsMap ;//响应缓慢,正常,异常的次数
	public String getApi() {
		return api;
	}
	
	public Integer getCallCount() {
		return callCount;
	}
	
	public void setApi(String api) {
		this.api = api;
	}

	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}
	

	public Integer getAvgCostTime() {
		return avgCostTime;
	}

	public void setAvgCostTime(Integer avgCostTime) {
		this.avgCostTime = avgCostTime;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Map<String, Object> getReponseSpeedsMap() {
		return reponseSpeedsMap;
	}

	public void setReponseSpeedsMap(Map<String, Object> reponseSpeedsMap) {
		this.reponseSpeedsMap = reponseSpeedsMap;
	}
}
