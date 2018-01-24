package com.tcl.ep.admin.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.ep.admin.model.AjaxModel;
import com.tcl.ep.admin.utils.QueryUtils;
import com.tcl.ep.biz.service.ExceptionStatisticService;
import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.common.exception.ParamException;
import com.tcl.ep.common.utils.DateTimeUtil;
import com.tcl.ep.persistence.vo.ExceptionModuleStatisticDto;
import com.tcl.ep.persistence.vo.ExceptionTrendDto;
import com.tcl.ep.persistence.vo.SearchTrendReq;

/**
 * Created by panmin on 16-11-24.
 */
@Controller
@RequestMapping("/statistic")
public class ExceptionStatisticController {

    @Resource
    private ExceptionStatisticService exceptionStatisticService;
    @Resource
    private ProjectService projectService;

    @RequestMapping("list")
    public String list(HttpServletRequest request, Model model) {
        //取当前时间，但是时，分，秒为０
        Calendar calendar = DateTimeUtil.getCurrentTimeOfDay();

        long currDate = calendar.getTimeInMillis();
        long startDate = QueryUtils.checkDate(request.getParameter("startDate"));
        long endDate = QueryUtils.checkDate(request.getParameter("endDate"));
        String projectName = request.getParameter("projectName");

        if (startDate == QueryUtils.INVALID_DATE) {
            // 对当天零点
            startDate = currDate;
        }
        if (endDate == QueryUtils.INVALID_DATE) {
            endDate = new Date().getTime();// 当前系统时间
        } else {
            endDate = endDate + (24 * 60 * 60 - 1) * 1000; // 结束日期的23:59:59
        }

        List<ProjectInfoVo> projectList = projectService.findProjects(null);

        List<ExceptionModuleStatisticDto> resultList;

        if (null == projectName || projectName.equals("-1")) {
            resultList = exceptionStatisticService.findStatisticException(null, startDate, endDate);
        } else {
            resultList = exceptionStatisticService.findStatisticException(projectName, startDate, endDate);
        }

        model.addAttribute("projectName", projectName == null ? "-1" : projectName);
        model.addAttribute("projectList", projectList);
        model.addAttribute("resultList", resultList);
        model.addAttribute("startDate", QueryUtils.formatDate(new Date(startDate)));
        model.addAttribute("endDate", QueryUtils.formatDate(new Date(endDate)));
        return "/admin/statistic/list";
    }

    @RequestMapping(value = "ajax/exceptionTrend.json", method = RequestMethod.POST)
    @ResponseBody
    public AjaxModel<List<ExceptionTrendDto>> addProject(@RequestBody SearchTrendReq searchTrendReq) {
        AjaxModel<List<ExceptionTrendDto>> result = new AjaxModel<>();

        //取当前时间，但是时，分，秒为０
        Calendar calendar = DateTimeUtil.getCurrentTimeOfDay();
        List<ExceptionTrendDto> exceptionTrendList;
        long currDate = calendar.getTimeInMillis();
        long startDate = QueryUtils.checkDate(searchTrendReq.getStartDate());
        long endDate = QueryUtils.checkDate(searchTrendReq.getEndDate());

        if (startDate == QueryUtils.INVALID_DATE) {
            // 对当天零点
            startDate = currDate;
        }
        if (endDate == QueryUtils.INVALID_DATE) {
            endDate = new Date().getTime();// 当前系统时间
        } else {
            endDate = endDate + (24 * 60 * 60 - 1) * 1000; // 结束日期的23:59:59
        }

        try {
            exceptionTrendList = exceptionStatisticService.findExceptionTrend(searchTrendReq.getProjectName(),
                    searchTrendReq.getModule(), startDate, endDate);
        } catch (ParamException e) {
            result.setMessage(e.getMessage());
            return result;
        }
        result.setStatus(AjaxModel.SUCCESS);
        result.setMessage("OK");
        result.setResult(exceptionTrendList);
        return result;
    }
}
