package com.tcl.ep.biz.service;

import java.util.List;

import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.persistence.entity.Project;

public interface ProjectService {

	void add(Project project);

	Project findByProjectId(Long projectId);

	Project findByProjectName(String projectName);

	List<ProjectInfoVo> findProjects(Project project);

	int update(ProjectInfoVo project);

	void projectOffLine(Long projcetId);
}
