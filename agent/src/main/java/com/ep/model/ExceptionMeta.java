package com.ep.model;

public class ExceptionMeta {
    private String errorInfo;
    private Long collectedTime;

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Long getCollectedTime() {
        return this.collectedTime;
    }

    public void setCollectedTime(Long collectedTime) {
        this.collectedTime = collectedTime;
    }

}
