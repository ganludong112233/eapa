package org.com.tcl.eapa.biz;

import javax.annotation.Resource;

import org.junit.Test;

import com.tcl.ep.biz.service.PerformanceStatisticsService;
import com.tcl.ep.biz.service.UserService;

public class PerfStatsTest extends AbstractBaseServiceTest {

    @Resource
    PerformanceStatisticsService service;
    @Resource
    UserService userService;

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            // service.findPerfStatsTrend(10001, 5);
            userService.selectByUserName("");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

    }
}
