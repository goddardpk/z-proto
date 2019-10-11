package com.zafin.zplatform.proto.exception;
/**
 * Every system record will have an API for its build process.
 * If a system has a Foo record then there will be:
 * 1. A FooService
 * 2. A FooException
 * 
 * @author Paul Goddard
 *
 */
public class BuilderServiceException  extends Exception {
    private static final long serialVersionUID = 1L;

    public BuilderServiceException(String msg) {
        super(msg);
    }
    public BuilderServiceException(String msg, Throwable t) {
        super(msg,t);
    }
}
