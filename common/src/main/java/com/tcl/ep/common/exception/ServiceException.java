package com.tcl.ep.common.exception;

public class ServiceException extends RuntimeException {
	
    private static final long serialVersionUID = 1397410891768947160L;
    
    private int code;
    
    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
    
    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
}
