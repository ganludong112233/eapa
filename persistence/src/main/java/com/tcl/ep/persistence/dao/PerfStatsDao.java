package com.tcl.ep.persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tcl.ep.persistence.entity.PerfStats;
import com.tcl.ep.persistence.vo.CallCountStatictic;
import com.tcl.ep.persistence.vo.PerfStatsDto;
import com.tcl.ep.persistence.vo.SearchPerfStatReq;

public interface PerfStatsDao {

    int insert(PerfStats record);

    PerfStats selectByPrimaryKey(Long id);

    List<PerfStats> findTopApiStatsList(@Param("projectId") long projectId,
            @Param("topNumber") int topNumber, @Param("startDate") long startDate,
            @Param("endDate") long endDate);

    ArrayList<Map<String, Object>> findApiRespSpeedsList(@Param("projectId") long projectId,
            @Param("api") String api, @Param("startDate") long startDate,
            @Param("endDate") long endDate);

    int insertBatch(@Param("list") List<PerfStats> perStaList);

    List<PerfStatsDto> findList(@Param("params") SearchPerfStatReq params,
            @Param("startDate") long startDate, @Param("endDate") long endDate);

    int findTotal(@Param("params") SearchPerfStatReq params, @Param("startDate") long startDate,
            @Param("endDate") long endDate);

    List<Map<String, Long>> findApiReqCostTimeList(@Param("projectId") long projectId,
            @Param("startDate") long startDate, @Param("endDate") long endDate);

    List<CallCountStatictic> findCallCountList(@Param("projectId") long projectId,
            @Param("day") int day, @Param("timeFormat") String timeFormat);

    Map<String, String> findPeakTime(@Param("projectId") long projectId,
            @Param("startDate") long startDate, @Param("endDate") long endDate);
}
