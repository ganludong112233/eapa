package com.tcl.ep.persistence.dao;

import com.tcl.ep.persistence.entity.SystemMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panmin on 16-11-29.
 */
public interface SystemMonitorDao {
    /**
     * Get system monitor information by project name and date
     *
     * @param projectName
     * @param startDate
     * @param endDate
     * @return
     */
    List<SystemMonitor> findSystemMonitorList(@Param("projectName") String projectName,
                                              @Param("startDate") long startDate, @Param("endDate") long endDate);

}
