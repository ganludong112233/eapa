package com.tcl.ep.persistence.vo;

import java.util.List;
import java.util.Map;

public class PerfstatTrendDto {
	private Long projectId;
	private String api;
	private List<Map<String,Long>> avgCostTimeMap;
	public String getApi() {
		return api;
	}
	public List<Map<String, Long>> getAvgCostTimeMap() {
		return avgCostTimeMap;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public void setAvgCostTimeMap(List<Map<String, Long>> avgCostTimeMap) {
		this.avgCostTimeMap = avgCostTimeMap;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
