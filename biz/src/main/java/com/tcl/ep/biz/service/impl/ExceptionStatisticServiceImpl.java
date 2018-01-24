package com.tcl.ep.biz.service.impl;

import com.tcl.ep.biz.service.ExceptionStatisticService;
import com.tcl.ep.persistence.dao.ExceptionStatisticDao;
import com.tcl.ep.persistence.vo.ExceptionModuleStatisticDto;
import com.tcl.ep.persistence.vo.ExceptionTrendDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by panmin on 16-11-24.
 */
@Service
public class ExceptionStatisticServiceImpl implements ExceptionStatisticService {

    @Resource
    private ExceptionStatisticDao exceptionStatisticDao;

    @Override
    public List<ExceptionModuleStatisticDto> findStatisticException(String projectName, long startDate, long endDate) {
        return exceptionStatisticDao.findStatisticException(projectName, startDate, endDate);
    }

    @Override
    public List<ExceptionTrendDto> findExceptionTrend(String projectName, String module, Long startDate, Long endDate) {
        return exceptionStatisticDao.findExceptionTrend(projectName, module, startDate, endDate);
    }
}
