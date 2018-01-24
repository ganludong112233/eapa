package com.tcl.ep.admin.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tcl.ep.admin.model.AjaxModel;
import com.tcl.ep.biz.service.ExceptionInService;
import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.common.utils.DateTimeUtil;
import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.vo.ExceptionInfoVo;
import com.tcl.ep.persistence.vo.ExceptionSearchReq;
import com.tcl.mie.util.Assert;

@Controller
@RequestMapping("/exception/")
public class ExceptionController {
	@Resource
	private ExceptionInService exceptionInfoService;
	@Resource
	private ProjectService projectService;

	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		ExceptionSearchReq params = buildSearchReq(request);
		Page<ExceptionInfoVo> data = exceptionInfoService.findList(params);
		List<ProjectInfoVo> projects= projectService.findProjects(null);
		List<String> modules=exceptionInfoService.findModules(params.getProjectId());
		String startDate=DateTimeUtil.formatDate(new Date(params.getStartDate()));
		String endDate=DateTimeUtil.formatDate(new Date(params.getEndDate()));
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("page", data);
		model.addAttribute("projects", projects);
		model.addAttribute("modules", modules);
		model.addAttribute("params", params);
		return "/admin/exception/list";

	}
	@ResponseBody
	@RequestMapping(value = "ajax/detail.json")
	public AjaxModel<String> detail(Long excepionInfoId) {
		AjaxModel<String> result = new AjaxModel<>();
		Assert.notNull(excepionInfoId, "Id can't be null");
		ExceptionInfoVo data = exceptionInfoService.findDetail(excepionInfoId);
		if (data != null) {
			result.setStatus(AjaxModel.SUCCESS);
			result.setResult(JSON.toJSONString(data));
		} else {
			result.setMessage("查询失败");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "ajax/moduleSearch.json", method = RequestMethod.GET)
    @ResponseBody
    public AjaxModel<List<String>> moduleSearch(String  projectId) throws IOException {
    	Assert.notNull(projectId, "Project id can be null"); 
    	
        List<String > modules = exceptionInfoService.findModules(Long.parseLong(projectId));

        AjaxModel<List<String>> result = new AjaxModel<>();

        if (modules.isEmpty()) {
            result.setResult(Collections.EMPTY_LIST);
            result.setMessage("no match result!");
        } else {
        	result.setResult(modules);
            result.setStatus(AjaxModel.SUCCESS);
        }
        return result;
    }

	private ExceptionSearchReq buildSearchReq(HttpServletRequest request) {
		ExceptionSearchReq exceptionSearchReq = new ExceptionSearchReq();
		if (StringUtils.isNotBlank(request.getParameter("projectId"))) {
			exceptionSearchReq.setProjectId(Long.valueOf(request.getParameter("projectId")));
		}
		if (StringUtils.isNotBlank(request.getParameter("exceptionName"))) {
			exceptionSearchReq.setExceptionName(request.getParameter("exceptionName"));
		}

		if (StringUtils.isNotBlank(request.getParameter("modules"))) {
			exceptionSearchReq.setModule(request.getParameter("modules"));
		}
		if (StringUtils.isNotBlank(request.getParameter("env"))) {
			exceptionSearchReq.setEnv(request.getParameter("env"));
		}
		if (StringUtils.isNotBlank(request.getParameter("errorMsg"))) {
			exceptionSearchReq.setErrorMsg(request.getParameter("errorMsg"));
		}
		if (StringUtils.isNotBlank(request.getParameter("ip"))) {
			exceptionSearchReq.setIp(request.getParameter("ip"));
		}
		Integer pageNo = 1;
		if (StringUtils.isNotBlank(request.getParameter("currPage"))) {
			pageNo = Integer.valueOf(request.getParameter("currPage"));
		}
		exceptionSearchReq.setPageNo(pageNo);

		Integer pageSize = 50;
		if (StringUtils.isNotBlank(request.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		exceptionSearchReq.setPageSize(pageSize);
		if (StringUtils.isNotBlank(request.getParameter("startDate"))) {
			exceptionSearchReq.setStartDate(DateTimeUtil.checkDate(request.getParameter("startDate")));
		}else {
			exceptionSearchReq.setStartDate(DateTimeUtil.getCurrentTimeOfDay().getTimeInMillis());
		}
		Long endDate;
		if (StringUtils.isNotBlank(request.getParameter("endDate"))) {
			 endDate=DateTimeUtil.checkDate(request.getParameter("endDate"))+ (24 * 60 * 60 - 1) * 1000;
		}else {
			 endDate=DateTimeUtil.getCurrentTimeOfDay().getTimeInMillis() + (24 * 60 * 60 - 1) * 1000;
		}
		exceptionSearchReq.setEndDate(endDate);
		return exceptionSearchReq;

	}

}
