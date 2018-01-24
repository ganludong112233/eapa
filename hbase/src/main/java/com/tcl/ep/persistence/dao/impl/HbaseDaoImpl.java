package com.tcl.ep.persistence.dao.impl;

import javax.annotation.Resource;

import org.springframework.data.hadoop.hbase.HbaseTemplate;

public class HbaseDaoImpl {
    @Resource
    HbaseTemplate template;

    public HbaseTemplate getTemplate() {
        return template;
    }

    public void setTemplate(HbaseTemplate template) {
        this.template = template;
    }
}
