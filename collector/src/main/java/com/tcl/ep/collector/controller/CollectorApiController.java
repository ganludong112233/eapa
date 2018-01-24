package com.tcl.ep.collector.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.ep.biz.service.ExceptionInService;
import com.tcl.ep.biz.service.PerformanceStatisticsService;
import com.tcl.ep.common.exception.ParamException;
import com.tcl.ep.persistence.entity.ExceptionInfo;
import com.tcl.ep.persistence.entity.PerfStats;
import com.tcl.mie.util.Assert;
import com.tcl.mie.vo.response.ResponseBase;
import com.tcl.mie.vo.response.ResponseBuilder;

@Controller
public class CollectorApiController {
    @Resource
    private PerformanceStatisticsService performanceStatusService;
    @Resource
    private ExceptionInService exceptionInfoService;

    @RequestMapping(value = "/perf/stats", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBase addPerfStats(@RequestBody List<PerfStats> perfStatsList,
            HttpServletRequest request) {
        try {
            performanceStatusService.addPerfStatsList(perfStatsList);
            return ResponseBuilder.success();
        } catch (Exception e) {
            return ResponseBuilder.invalidArgs(e.getMessage());
        }
    }

    @RequestMapping(value = "/exception/batchAdd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBase addException(@RequestBody List<ExceptionInfo> exceptionInfos,
            HttpServletRequest request) {
        try {
            String ip = request.getRemoteHost();
            validateParams(exceptionInfos, ip);

            exceptionInfoService.addExceptionInfo(exceptionInfos);
            return ResponseBuilder.success();
        } catch (Exception e) {
            return ResponseBuilder.invalidArgs(e.getMessage());
        }
    }

    @RequestMapping(value = "/exception/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBase addException(@RequestBody ExceptionInfo exceptionInfo,
            HttpServletRequest request) {
        List<ExceptionInfo> exceptionInfos = new ArrayList<ExceptionInfo>();
        exceptionInfos.add(exceptionInfo);
        try {
            String ip = request.getRemoteHost();
            validateParams(exceptionInfos, ip);

            exceptionInfoService.addExceptionInfo(exceptionInfos);
            return ResponseBuilder.success();
        } catch (Exception e) {
            return ResponseBuilder.genericResponse(500, e.getMessage(), null);
        }
    }

    private void validateParams(List<ExceptionInfo> exceptionInfos, String ip) {

        Assert.notNull(exceptionInfos, "Excption info can't be null");
        for (ExceptionInfo exceptionInfo : exceptionInfos) {
            if (exceptionInfo.getProjectId() == null) {
                throw new ParamException("ProjectId can't be null");
            }
            if (StringUtils.isEmpty(exceptionInfo.getExceptionName())) {
                throw new ParamException("ExceptionName can't be null");
            }
            if (StringUtils.isEmpty(exceptionInfo.getErrorMsg())) {
                throw new ParamException("ErrorMsg can't be null");
            }
            if (exceptionInfo.getOccurTime() == null) {
                throw new ParamException("OccurTime can't be null");
            }
            // if (StringUtils.isEmpty(exceptionInfo.getModule())) {
            // throw new ParamException("Module can't be null");
            // }
            if (StringUtils.isBlank(exceptionInfo.getIp())) {
                exceptionInfo.setIp(ip);
            }
        }

    }
}
