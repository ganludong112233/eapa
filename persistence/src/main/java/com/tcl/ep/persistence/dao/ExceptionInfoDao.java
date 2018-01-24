package com.tcl.ep.persistence.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcl.ep.persistence.entity.ExceptionInfo;
import com.tcl.ep.persistence.vo.ExceptionInfoVo;
import com.tcl.ep.persistence.vo.ExceptionSearchReq;

public interface ExceptionInfoDao {

	int insert(ExceptionInfo exceptionInfo);

	ExceptionInfoVo findById(@Param("id")Long id);

	List<ExceptionInfo> findAll();

	List<ExceptionInfoVo> findList(ExceptionSearchReq params);

	Integer findTotal(ExceptionSearchReq params);

	List<String> findModules(@Param("projectId")Long projectId);
}
