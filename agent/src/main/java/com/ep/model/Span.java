package com.ep.model;

/**
 * Distribution trace span
 * 
 * @author yi_liu
 * 
 */
public class Span extends BaseInfo {
    private String id;
    private String parentId; // if parentId is null , this span is root span
    /**
     * 0: RPC, 1: LOCAL
     */
    private int type;
    private long startTime;
    private long endTime;
    private Object info;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }
}
