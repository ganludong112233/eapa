package com.tcl.ep.biz.service;

import java.util.List;

import com.tcl.ep.biz.vo.ApiStatistic;
import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.entity.PerfStats;
import com.tcl.ep.persistence.vo.CallCountStatictic;
import com.tcl.ep.persistence.vo.PerfStatsDto;
import com.tcl.ep.persistence.vo.SearchPerfStatReq;

/**
 * Created by panmin on 16-11-29.
 */
public interface PerformanceStatisticsService {

    Page<PerfStatsDto> findPerfStatsList(SearchPerfStatReq request, long startDate, long endDate);

    List<ApiStatistic> findTopApiStatsList(long projectId, int topNumber, long startDate,
            long endDate);

    int addPerfStatsList(List<PerfStats> perfStatsList);

    List<CallCountStatictic> findPerfStatsTrend(long projectId, int day);
}
