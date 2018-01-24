package com.tcl.ep.common.utils;

import java.util.List;

/**
 * 简单的分页类 Created by machangsheng on 15/10/8.
 */
public class Page<T> {
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private long totalRecord;
    /**
     * 当前页码,以1开始
     */
    private int currentPage;
    /**
     * 总页数(也可以认为是末页)
     */
    private int totalPage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 上一页
     */
    private int lastPage;
    /**
     * 要显示的数据
     */
    private List<T> recordList;

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取总记录数,这个在前台会用到
     * 
     * @return 总记录数
     */
    public long getTotalRecord() {
        return totalRecord;
    }

    /**
     * 获取当前页,前台也会用到
     * 
     * @return 当面页码
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    /**
     * 最完整的构造函数
     * 
     * @param totalRecord 总记录数
     * @param currentPage 当前页,以1开始
     * @param pageSize 每页记录数
     */
    public Page(long totalRecord, int currentPage, int pageSize) {
        this.totalRecord = totalRecord;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        init();
    }

    /**
     * 构造函数,必须指定总记录数,默认第一页,每页显示20条
     * 
     * @param totalRecord 总记录数
     */
    public Page(long totalRecord) {
        this(totalRecord, 1, 20);
    }

    /**
     * 构造函数,默认显示20条
     * 
     * @param totalRecord 总记录数
     * @param currentPage 当前页码
     */
    public Page(long totalRecord, int currentPage) {
        this(totalRecord, currentPage, 20);
    }

    /**
     * 计算总页数,有可能还计算上一页,下一页,第一页,最后一页
     */
    private void init() {
        // 只有在totalRecord > 0 的情况下计算才有意义
        if (totalRecord > 0 && pageSize > 0) {
            // 1. 计算总页数
            totalPage = (int) (totalRecord / pageSize);

            if ((totalRecord % pageSize != 0)) {
                totalPage = totalPage + 1;
            }

            // 2. 修正当前页码
            if (currentPage < 1) {
                currentPage = 1;
            } else if (currentPage > totalPage) {
                currentPage = totalPage;
            }

            // 3. 计算下一页
            nextPage = currentPage < totalPage ? currentPage + 1 : totalPage;
            // 4. 计算上一页
            lastPage = currentPage > 1 ? currentPage - 1 : 1;

        } else {
            totalPage = 0;
            pageSize = 20;
            totalPage = 1;
            currentPage = 1;
            nextPage = 1;
            lastPage = 1;
        }
    }

    /**
     * 下一页
     * 
     * @return 下一页
     */
    public int getNextPage() {
        return nextPage;
    }

    /**
     * 上一页
     * 
     * @return 上一页
     */
    public int getLastPage() {
        return lastPage;
    }

    /**
     * 获取记录开始的位置, 本方法只能在Page初始化之后调用
     *
     * @return 位置
     */
    public int getStartPos() {
        return (currentPage - 1) * pageSize;
    }


    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }
}
