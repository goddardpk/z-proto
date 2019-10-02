package com.zafin.zplatform.proto.service;

import com.zafin.zplatform.proto.BuilderServiceException;

public class PayLoadException extends BuilderServiceException {
    private static final long serialVersionUID = 1L;

    public PayLoadException(String msg) {
        super(msg);
    }
    
    public PayLoadException(String msg, Throwable e) {
        super(msg,e);
    }
}
