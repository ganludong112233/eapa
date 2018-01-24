package com.tcl.ep.biz.service;

import com.tcl.ep.persistence.vo.ExceptionModuleStatisticDto;
import com.tcl.ep.persistence.vo.ExceptionTrendDto;

import java.util.List;

/**
 * Created by panmin on 16-11-24.
 */
public interface ExceptionStatisticService {

    /**
     * 查询统计异常列表
     *
     * @param projectName
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExceptionModuleStatisticDto> findStatisticException(String projectName, long startDate, long endDate);


    /**
     * 统计时间段内异常趋势
     *
     * @param projectName
     * @param module
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExceptionTrendDto> findExceptionTrend(String projectName, String module, Long startDate, Long endDate);
}
