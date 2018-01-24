package com.tcl.ep.admin.controller;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tcl.ep.admin.model.AjaxModel;
import com.tcl.ep.admin.model.ApiStatisticList;
import com.tcl.ep.admin.utils.QueryUtils;
import com.tcl.ep.biz.service.PerformanceStatisticsService;
import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.common.exception.ParamException;
import com.tcl.ep.common.utils.DateTimeUtil;
import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.vo.CallCountStatictic;
import com.tcl.ep.persistence.vo.PerfStatsDto;
import com.tcl.ep.persistence.vo.SearchPerfStatReq;

/**
 * Created by panmin on 16-11-29.
 */
@Controller
@RequestMapping("/performance/")
public class PerfStatController {
	@Resource
	private PerformanceStatisticsService performanceStatusService;
	@Resource
	private ProjectService projectService;

	@RequestMapping("list")
	public String list(SearchPerfStatReq request, Model model) {
		long startDate = QueryUtils.checkDate(request.getStartDate());
		long endDate = QueryUtils.checkDate(request.getEndDate());
		
		List<ProjectInfoVo> projectList = projectService.findProjects(null);
		Page<PerfStatsDto> resultList = performanceStatusService.findPerfStatsList(request, startDate, endDate);
        if (startDate==endDate&&startDate==0) {
        	endDate=startDate= DateTimeUtil.getCurrentMillsOfUTC();
		}
		model.addAttribute("params", request);
		model.addAttribute("projectList", projectList);
		model.addAttribute("page", resultList);
		model.addAttribute("startDate", QueryUtils.formatDate(new Date(startDate)));
		model.addAttribute("endDate", QueryUtils.formatDate(new Date(endDate)));
		return "/admin/performance/list";
	}

	@RequestMapping("chart")
	public String chart(HttpServletRequest request, Model model) {
		String reqProjectId = request.getParameter("projectId");
		String reqTopNumber = request.getParameter("topNumber");
		long startDate = QueryUtils.checkDate(request.getParameter("startDate"));
		long endDate = QueryUtils.checkDate(request.getParameter("endDate"));
		
		ApiStatisticList resultList = new ApiStatisticList();
		List<ProjectInfoVo> projectList = projectService.findProjects(null);
		long projectId = Long.parseLong(projectList.get(0).getProjectId());
		int topNumber = 10;
		if (reqProjectId!=null && reqTopNumber!=null) {

			try {
				projectId = Long.parseLong(reqProjectId);
				topNumber = Integer.parseInt(reqTopNumber);
			} catch (NumberFormatException e) {
				throw new ParamException("参数不正确");
			}
		}
		if (0 != projectId) {
			resultList.setApiStatisticList(performanceStatusService.findTopApiStatsList(projectId, topNumber,startDate, endDate));
			resultList.setPerfstatTrendList(performanceStatusService.findPerfStatsTrend(projectId,5));
		}
		
		model.addAttribute("projectId", reqProjectId);
		model.addAttribute("projectList", projectList);
		model.addAttribute("resultList", resultList);
		model.addAttribute("apiStatisticList", JSON.toJSONString(resultList.getApiStatisticList()));
		model.addAttribute("perfstatTrendList", JSON.toJSONString(resultList.getPerfstatTrendList()));
		model.addAttribute("topNumber", reqTopNumber);

		return "/admin/performance/chart";
	}
	 @RequestMapping(value = "ajax/perfstatTrend.json", method = RequestMethod.POST)
	    @ResponseBody
	    public AjaxModel<List<CallCountStatictic>> queryPerfstat(Long projectId,Integer day) {
	        AjaxModel<List<CallCountStatictic>> result = new AjaxModel<>();
	        
	        List<CallCountStatictic> perfstatTrendMap;
			try {
				perfstatTrendMap = performanceStatusService.findPerfStatsTrend(projectId,day);
	        } catch (ParamException e) {
	            result.setMessage(e.getMessage());
	            return result;
	        }
	        result.setStatus(AjaxModel.SUCCESS);
	        result.setMessage("OK");
	        result.setResult(perfstatTrendMap);
	        return result;
	    }
	 
}
