package com.tcl.ep.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcl.ep.admin.model.AjaxModel;
import com.tcl.ep.admin.model.SearchProjecdInfo;
import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.common.exception.ParamException;
import com.tcl.ep.persistence.entity.Project;

@Controller
@RequestMapping("/project/")
public class ProjectController {

	private static Logger LOG = LoggerFactory.getLogger(ProjectController.class);

	@Resource
	private ProjectService projectService;

	@RequestMapping(value = "ajax/projcetAdd.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxModel<List<String>> addProject(@RequestBody Project project) {
		AjaxModel<List<String>> result = new AjaxModel<>();
		try {
			vailidateParams(project);
			projectService.add(project);
		} catch (ParamException e) {
			result.setMessage(e.getMessage());
			return result;
		}
		result.setStatus(AjaxModel.SUCCESS);
		result.setMessage("OK");
		return result;
	}

	private void vailidateParams(Project project) {
		if (StringUtils.isBlank(project.getProjectName()) || StringUtils.isBlank(project.getToEmails())) {
			throw new ParamException("Project name and email can't be null");
		}
		String[] mails = project.getToEmails().split(",");
		for (String mail : mails) {
			if (checkEmail(mail) == false) {
				throw new ParamException("Email format is not correct");
			}
		}

	}

	@RequestMapping("delete")
	public String delete(@RequestParam("projectId") Long projectId) {
		if (null == projectId || projectId == 0L) {
			return "redirect:/project/list";
		}
		projectService.projectOffLine(projectId);
		return "redirect:/project/list";
	}

	@RequestMapping(value = "ajax/modify.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxModel<List<String>> modifyProject(@RequestBody ProjectInfoVo project) {
		AjaxModel<List<String>> result = new AjaxModel<>();
		try {
			if (project.getProjectId() == null) {
				throw new ParamException("the parameter of projectId must be not null");
			}
			projectService.update(project);
			result.setStatus(AjaxModel.SUCCESS);
			result.setMessage("OK");
			return result;
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return result;
		}

	}

	/**
	 * 查询project列表
	 * 
	 * @return list of project files
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {

		String projectId = request.getParameter("projectId");
		String projectName = request.getParameter("projectName");
		String toEmails = request.getParameter("toEmails");

		model.addAttribute("projectId", projectId == null ? "" : projectId);
		model.addAttribute("projectName", projectName == null ? "" : projectName);
		model.addAttribute("toEmails", toEmails == null ? "" : toEmails);

		Project project = new Project();
		try {
			project.setProjectId(StringUtils.isBlank(projectId) ? 0L : Long.parseLong(projectId));
		} catch (NumberFormatException e) {
			LOG.error("projectId is NaN.");
			model.addAttribute("resultList", Collections.EMPTY_LIST);
			return "/admin/project/list";
		}
		project.setProjectName(projectName);
		project.setToEmails(toEmails);

		List<ProjectInfoVo> resultList = projectService.findProjects(project);
		if (CollectionUtils.isEmpty(resultList)) {
			resultList = Collections.EMPTY_LIST;
		}

		model.addAttribute("resultList", resultList);
		return "/admin/project/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ajax/getSuggestion.json", method = RequestMethod.POST)
	@ResponseBody
	public AjaxModel<List<String>> ajaxSearch(@RequestBody SearchProjecdInfo item) throws IOException {

		Project project = item.convertToProjcet();
		List<ProjectInfoVo> projectInfoList = projectService.findProjects(project);

		AjaxModel<List<String>> result = new AjaxModel<>();
		result.setResult(generateResultByType(projectInfoList, item.getType()));

		if (result.getResult().isEmpty()) {
			result.setResult(Collections.EMPTY_LIST);
			result.setMessage("no match result!");
		} else {
			result.setStatus(AjaxModel.SUCCESS);
		}
		return result;
	}

	/**
	 * 通过传入类型确定组装返回列
	 * 
	 * @param projectInfoList
	 * @param type
	 * @return
	 */
	private List<String> generateResultByType(List<ProjectInfoVo> projectInfoList, String type) {

		List<String> result = new ArrayList<>();
		if (type.equals("projectName")) {
			for (ProjectInfoVo projectInfoVo : projectInfoList) {
				result.add(projectInfoVo.getProjectName());
			}
		} else if (type.equals("toEmails")) {
			for (ProjectInfoVo projectInfoVo : projectInfoList) {
				result.add(projectInfoVo.getToEmails());
			}
		}
		return result;
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
