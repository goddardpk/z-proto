package com.zafin.zplatform.proto.factory;

import java.util.List;

import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface PayLoadFactory<T> {
    
    PayLoad create(Object source) throws BuilderServiceException;
    boolean isSupported(Object source);
    PayLoadFactory<?> getPreviousPayLoadFactory();
    void setPreviousPayLoadFactory(PayLoadFactory<?> previousPayLoadFactory);
    List<String> getAllFields(); //TODO: Delegate to Schema to get this info
    List<String> getJustMyFields(); //TODO: Delegate to Schema to get this info
    int getRevision();
    Class<T> getClazz();
}
