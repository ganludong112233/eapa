package com.tcl.ep.biz.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tcl.ep.biz.service.PerformanceStatisticsService;
import com.tcl.ep.biz.vo.ApiStatistic;
import com.tcl.ep.common.exception.ServiceException;
import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.dao.PerfStatsDao;
import com.tcl.ep.persistence.entity.PerfStats;
import com.tcl.ep.persistence.vo.CallCountStatictic;
import com.tcl.ep.persistence.vo.PerfStatsDto;
import com.tcl.ep.persistence.vo.SearchPerfStatReq;

/**
 * Created by panmin on 16-11-29.
 */
@Service
public class PerformanceStatisticsServiceImpl implements PerformanceStatisticsService {

    private static Logger LOG = LoggerFactory.getLogger(PerformanceStatisticsServiceImpl.class);

    @Resource
    private PerfStatsDao performanceStatisticsDao;

    @Override
    public Page<PerfStatsDto> findPerfStatsList(SearchPerfStatReq params, long startDate,
            long endDate) {
        LOG.debug("Search parameters are:{}", params);
        Integer totalCount = performanceStatisticsDao.findTotal(params, startDate, endDate);
        List<PerfStatsDto> items = new ArrayList<PerfStatsDto>();
        if (totalCount != null && totalCount > 0) {
            items = performanceStatisticsDao.findList(params, startDate, endDate);
        } else {
            totalCount = new Integer(0);
        }
        Page<PerfStatsDto> record =
                new Page<>(totalCount, params.getPageNo(), params.getPageSize());
        record.setRecordList(items);
        return record;
    }

    /* *
     * 查询访问量最多的topNumber个接口的信息
     */
    @Override
    public List<ApiStatistic> findTopApiStatsList(long projectId, int topNumber, long startDate,
            long endDate) {
        List<ApiStatistic> resultList = new ArrayList<ApiStatistic>();
        // find requested top 10 api info
        List<PerfStats> apiInfos =
                performanceStatisticsDao.findTopApiStatsList(projectId, topNumber, startDate,
                        endDate);
        for (PerfStats performanceStatistics : apiInfos) {
            ApiStatistic apiStatistic = new ApiStatistic();
            apiStatistic.setApi(performanceStatistics.getApi());
            apiStatistic.setAvgCostTime(performanceStatistics.getAvgCostTime());
            apiStatistic.setCallCount(performanceStatistics.getCallCount());
            // 查询每个api在该时间段中,响应时间正常,缓慢,异常的次数
            ArrayList<Map<String, Object>> reponseSpeedsList =
                    performanceStatisticsDao.findApiRespSpeedsList(projectId,
                            performanceStatistics.getApi(), startDate, endDate);
            Map<String, Object> reponseSpeedsMap = new HashMap<String, Object>();
            double total = 0;
            for (Map<String, Object> map : reponseSpeedsList) {
                reponseSpeedsMap.put(map.get("timeKey").toString(), map.get("value"));
                Object object = map.get("value");
                total += Double.parseDouble(object.toString());
            }
            reponseSpeedsMap.put("average", total / reponseSpeedsMap.size());
            apiStatistic.setReponseSpeedsMap(reponseSpeedsMap);
            resultList.add(apiStatistic);
        }
        return resultList;
    }

    @Override
    public int addPerfStatsList(List<PerfStats> perfStatsList) {
        LOG.debug("save: perfStatsList={}", perfStatsList.toString());
        List<PerfStats> itemList = new ArrayList<>();
        for (PerfStats perfStats : perfStatsList) {
            PerfStats item = new PerfStats();
            BeanUtils.copyProperties(perfStats, item);
            itemList.add(item);
        }
        return performanceStatisticsDao.insertBatch(itemList);

    }

    /*
     * 查询时间段内每天的总访问量
     */
    @Override
    public List<CallCountStatictic> findPerfStatsTrend(long projectId, int day) {

        // Map<String, List<CallCountStatictic>> resultMap=new HashMap<>();
        if (day == 0) {
            day = 5;
        }
        // 默认查询最近5每天的访问量,tpm
        List<CallCountStatictic> callCountList =
                performanceStatisticsDao.findCallCountList(projectId, day, "%Y-%m-%d");
        // resultMap.put("week", callCountList);
        // 每天每小时访问量,峰值时间段

        for (CallCountStatictic item : callCountList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            try {
                date = simpleDateFormat.parse(item.getTime());
            } catch (ParseException e) {
                LOG.error("Date thranslate failed.");
                throw new ServiceException(500, "Date thranslate failed.");
            }
            long startTime = date.getTime();
            long endTime = startTime + 24 * 60 * 60 * 1000;
            Map<String, String> peakTime =
                    performanceStatisticsDao.findPeakTime(projectId, startTime, endTime);
            String timeString = peakTime.get("startTime") + "-" + peakTime.get("endTime");

            item.setPeakTime(timeString);
            /*
             * List<CallCountStatictic>
             * dayDataList=performanceStatisticsDao.findCallCountList(projectId, startTime,
             * endTime,"%H"); resultMap.put(dateFormat.format( startTime ), dayDataList);
             */

        }
        return callCountList;
    }
}
