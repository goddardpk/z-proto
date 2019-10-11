package com.zafin.zplatform.proto.service;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public class AlertException extends BuilderServiceException {
    private static final long serialVersionUID = 1L;

    public AlertException(String msg) {
        super(msg);
    }
    
    public AlertException(String msg, Throwable e) {
        super(msg,e);
    }
}
