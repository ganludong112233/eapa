package com.tcl.ep.persistence.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CallCountStatictic {

	private String time;
	private int callCount;
	private float tpm;
	private String peakTime;
	public int getCallCount() {
		return callCount;
	}
	public String getTime() {
		return time;
	}
	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public float getTpm() {
		return tpm;
	}
	public void setTpm(float tpm) {
		this.tpm = tpm;
	}
	public String getPeakTime() {
		return peakTime;
	}
	public void setPeakTime(String peakTime) {
		this.peakTime = peakTime;
	}
	
	
}
