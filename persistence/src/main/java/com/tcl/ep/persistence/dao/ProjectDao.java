package com.tcl.ep.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcl.ep.persistence.entity.Project;

public interface ProjectDao {

	int insert(Project project);

	Project findByProjectId(@Param("projectId") Long projectId);

	Project findByName(@Param("projectName") String projectName);

	List<Project> findProjects(Project project);

	Long findMaxProjectId();

	int update(Project project);

    void updateStatus(@Param("projectId") Long projectId, @Param("status")Integer status);
}