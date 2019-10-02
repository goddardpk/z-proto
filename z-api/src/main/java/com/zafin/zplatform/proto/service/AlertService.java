package com.zafin.zplatform.proto.service;


import com.zafin.zplatform.proto.Builder;
import com.zafin.zplatform.proto.BuilderServiceException;
import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.RemoteBuilderService;

public interface AlertService<T,B> extends RemoteBuilderService<T,B> {
    T build(PayLoad payLoad, Builder<T,B> builder)
            throws BuilderServiceException;
}
