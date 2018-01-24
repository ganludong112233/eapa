package com.tcl.ep.persistence.dao;

import java.util.List;

import com.tcl.ep.persistence.entity.TracedPerfInfo;

public interface PerfInfoDao {
    /**
     * @param perfInfo
     */
    public void save(TracedPerfInfo perfInfo);

    /**
     * 
     * @param traceId
     * @return
     */
    public List<TracedPerfInfo> findByTraceId(String traceId);
}
