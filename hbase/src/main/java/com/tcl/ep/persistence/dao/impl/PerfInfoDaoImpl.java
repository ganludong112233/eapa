package com.tcl.ep.persistence.dao.impl;

import java.util.List;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Repository;

import com.tcl.ep.persistence.dao.PerfInfoDao;
import com.tcl.ep.persistence.entity.TracedPerfInfo;

@Repository
public class PerfInfoDaoImpl extends HbaseDaoImpl implements PerfInfoDao {

    private String tableName = "eapa:traced_perf_info";
    private String cfInfo = "i";

    @Override
    public void save(TracedPerfInfo perfInfo) {
        if (perfInfo.getApi() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "api", perfInfo.getApi()
                    .getBytes());
        }
        if (perfInfo.getClassName() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "cn", perfInfo.getClassName()
                    .getBytes());
        }
        if (perfInfo.getMethodName() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "mn", perfInfo.getMethodName()
                    .getBytes());
        }
        if (perfInfo.getSignature() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "s", perfInfo.getSignature()
                    .getBytes());
        }
        if (perfInfo.getIp() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "ip", perfInfo.getIp().getBytes());
        }
        if (perfInfo.getEnv() != null) {
            template.put(tableName, perfInfo.getRowKey(), cfInfo, "env", perfInfo.getEnv()
                    .getBytes());
        }
        template.put(tableName, perfInfo.getRowKey(), cfInfo, "pi",
                Long.toString(perfInfo.getProjectId()).getBytes());
        template.put(tableName, perfInfo.getRowKey(), cfInfo, "ot",
                Long.toString(perfInfo.getOccurTime()).getBytes());
        template.put(tableName, perfInfo.getRowKey(), cfInfo, "ct",
                Long.toString(perfInfo.getCostTime()).getBytes());
    }

    @Override
    public List<TracedPerfInfo> findByTraceId(String traceId) {
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(traceId.getBytes()));

        return template.find(tableName, scan, new RowMapper<TracedPerfInfo>() {

            @Override
            public TracedPerfInfo mapRow(Result result, int rowNum) throws Exception {
                TracedPerfInfo perfIno = new TracedPerfInfo();
                while (result.advance()) {
                    Cell cell = result.current();
                    perfIno.setTraceId(new String(result.getRow()));
                    String columnName = new String(CellUtil.cloneQualifier(cell));
                    if (columnName.equals("api")) {
                        perfIno.setApi(new String(CellUtil.cloneValue(cell)));
                    }
                    if (columnName.equals("cn")) {
                        perfIno.setClassName(new String(CellUtil.cloneValue(cell)));
                    }
                    if (columnName.equals("mn")) {
                        perfIno.setMethodName(new String(CellUtil.cloneValue(cell)));
                    }
                    if (columnName.equals("s")) {
                        perfIno.setSignature(new String(CellUtil.cloneValue(cell)));
                    }
                    if (columnName.equals("env")) {
                        perfIno.setEnv(new String(CellUtil.cloneValue(cell)));
                    }
                    if (columnName.equals("ot")) {
                        perfIno.setOccurTime(Long.valueOf(new String(CellUtil.cloneValue(cell))));
                    }
                    if (columnName.equals("ct")) {
                        perfIno.setCostTime(Long.valueOf(new String(CellUtil.cloneValue(cell))));
                    }
                }
                return perfIno;
            }
        });
    }

}
