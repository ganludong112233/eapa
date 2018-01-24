package com.tcl.ep.biz.service;

import java.util.List;

import com.tcl.ep.common.utils.Page;
import com.tcl.ep.persistence.entity.ExceptionInfo;
import com.tcl.ep.persistence.vo.ExceptionInfoVo;
import com.tcl.ep.persistence.vo.ExceptionSearchReq;

public interface ExceptionInService {

	int addExceptionInfo(List<ExceptionInfo> exceptionInfo);

	Page<ExceptionInfoVo> findList(ExceptionSearchReq params);

	ExceptionInfoVo findDetail(Long excepionInfoId);

	List<String> findModules(Long projectId);

}
