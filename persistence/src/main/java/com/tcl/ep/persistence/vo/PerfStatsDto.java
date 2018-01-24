package com.tcl.ep.persistence.vo;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.tcl.ep.persistence.entity.PerfStats;

/**
 * if the value of api is not empty, this class represent API performance statistics else it refers
 * method execution statistics
 * 
 * @author yi_liu
 * 
 */
public class PerfStatsDto extends PerfStats{
  
    private String projectName;
    

    public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
