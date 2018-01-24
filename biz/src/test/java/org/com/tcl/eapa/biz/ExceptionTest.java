package org.com.tcl.eapa.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.tcl.ep.biz.service.ExceptionInService;
import com.tcl.ep.persistence.entity.ExceptionInfo;

public class ExceptionTest extends AbstractBaseServiceTest{

	@Resource
	private ExceptionInService service;
	/*@Test
	 public void testsave() {
		ExceptionInfo project=service.getDetail(1L);
		System.out.println(JSON.toJSONString(project));
    }*/
	@Test
	public void insert(){
		ExceptionInfo info=new ExceptionInfo();
		info.setProjectId(10001L);
		info.setExceptionName("error");
		info.setModule("admin");
		info.setErrorMsg("dshkasdhvadsbvjks");
		info.setOccurTime(new Date().getTime());
		info.setHandlerClass("contentService");
		info.setRequestMethod("search");
		System.out.println("---------"+JSONObject.toJSONString(info));
		List<ExceptionInfo> list=new ArrayList<>();
		list.add(info);
		service.addExceptionInfo(list);
	}
}
