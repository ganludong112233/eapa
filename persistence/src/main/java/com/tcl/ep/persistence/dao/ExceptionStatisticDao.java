package com.tcl.ep.persistence.dao;

import com.tcl.ep.persistence.vo.ExceptionModuleStatisticDto;
import com.tcl.ep.persistence.vo.ExceptionTrendDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by panmin on 16-11-24.
 */
public interface ExceptionStatisticDao {
    /**
     * 查询统计异常列表
     *
     * @param projectName
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExceptionModuleStatisticDto> findStatisticException(@Param("projectName") String projectName,
                                                             @Param("startDate") long startDate,
                                                             @Param("endDate") long endDate);

    /**
     * 统计时间段内异常趋势
     *
     * @param projectName
     * @param module
     * @param startDate
     * @param endDate
     * @return
     */
    List<ExceptionTrendDto> findExceptionTrend(@Param("projectName") String projectName,
                                               @Param("module") String module,
                                               @Param("startDate") Long startDate,
                                               @Param("endDate") Long endDate);
}
