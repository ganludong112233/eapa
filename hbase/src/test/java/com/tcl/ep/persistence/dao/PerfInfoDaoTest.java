package com.tcl.ep.persistence.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.tcl.ep.persistence.entity.TracedPerfInfo;
import com.tcl.mie.unit.SpringDAOTest;

public class PerfInfoDaoTest extends SpringDAOTest {
    @Resource
    PerfInfoDao dao;

    @Test
    public void testSave() {
        TracedPerfInfo pi = new TracedPerfInfo();
        pi.setEnv("dev");
        pi.setApi("/api/test");
        pi.setClassName(null);
        pi.setMethodName(null);
        pi.setSignature(null);
        pi.setOccurTime(new Date().getTime());
        pi.setCostTime(222);
        pi.setTraceId("332323232");
        dao.save(pi);
    }

    @Test
    public void testFindByTraceId() {
        List<TracedPerfInfo> pfList = dao.findByTraceId("332323232");
        for (TracedPerfInfo perfInfo : pfList) {
            System.out.println(perfInfo.getApi() + "-" + perfInfo.getTraceId() + "-"
                    + perfInfo.getCostTime());
        }
    }
}
