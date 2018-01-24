package com.tcl.ep.admin.model;

/**
 * Ajax Request
 */
public class AjaxModel<T> {

    /**
     * 状态-0, 正常,其它-异常
     */
    private short status = FAILED;

    /**
     * 返回的提示信息,通常这个是错误信息
     */
    private String message = "";

    /**
     * 结果数据
     */
    private T result;

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 成功
     */
    public static final short SUCCESS = 0;
    /**
     * 失败
     */
    public static final short FAILED = 1;

    @Override
    public String toString() {
        return "AjaxModel{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
