package com.tcl.ep.biz.service.impl;

import com.tcl.ep.biz.service.ProjectService;
import com.tcl.ep.biz.vo.ProjectInfoVo;
import com.tcl.ep.common.exception.ParamException;
import com.tcl.ep.common.exception.ServiceException;
import com.tcl.ep.persistence.dao.ProjectDao;
import com.tcl.ep.persistence.entity.Project;
import com.tcl.mie.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private static Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Resource
    private ProjectDao projectDao;

    @Override
    public void add(Project project) {
        ValidateParams(project);
        LOG.info("project info is " + project);
        if (projectDao.findByName(project.getProjectName()) != null) {
            throw new ServiceException(400, project.getProjectName() + "is exsist");
        }
         Long maxProjectId=projectDao.findMaxProjectId();
        if (maxProjectId==null) {
        	maxProjectId=1000L;
		}
        long projectId = maxProjectId + 1;
        project.setProjectId(projectId);
        projectDao.insert(project);
    }

    @Override
    public Project findByProjectId(Long projectId) {

        return projectDao.findByProjectId(projectId);
    }

    private void ValidateParams(Project project) {

        Assert.notNull(project, "project info can't be null");
        if (StringUtils.isEmpty(project.getProjectName())) {
            throw new ParamException("project name can't be null");
        }
    }

    @Override
    public Project findByProjectName(String projectName) {
        return projectDao.findByName(projectName);
    }

    @Override
    public List<ProjectInfoVo> findProjects(Project projectReq) {
        List<ProjectInfoVo> resultList = new ArrayList<ProjectInfoVo>();
        List<Project> projectList = projectDao.findProjects(projectReq);
        ProjectInfoVo infoVo;
        for (Project project : projectList) {
            infoVo = new ProjectInfoVo();
            resultList.add(infoVo.ModelConvertToVo(project));
        }
        return resultList;
    }

    @Override
    public int update(ProjectInfoVo projectInfo) {
        LOG.debug("project information:{}", projectInfo.toString());
       
        Project project = projectInfo.voConvertToModel();
        int cnt = projectDao.update(project);
        return cnt;
    }

    @Override
    public void projectOffLine(Long projectId) {
        //项目下线
        int status = 1;
        projectDao.updateStatus(projectId, status);
    }
}
