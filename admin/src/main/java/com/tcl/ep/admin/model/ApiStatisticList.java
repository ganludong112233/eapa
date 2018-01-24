package com.tcl.ep.admin.model;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.tcl.ep.biz.vo.ApiStatistic;
import com.tcl.ep.persistence.vo.CallCountStatictic;

public class ApiStatisticList {
	
	private List<ApiStatistic> apiStatisticList;//访问量最高的数据
	 List<CallCountStatictic> perfstatTrendList;//最近几天访问量数据

	public List<CallCountStatictic> getPerfstatTrendList() {
		return perfstatTrendList;
	}

	public void setPerfstatTrendList(List<CallCountStatictic> perfstatTrendList) {
		this.perfstatTrendList = perfstatTrendList;
	}

	public List<ApiStatistic> getApiStatisticList() {
		return apiStatisticList;
	}

	public void setApiStatisticList(List<ApiStatistic> apiStatisticList) {
		this.apiStatisticList = apiStatisticList;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	
}
