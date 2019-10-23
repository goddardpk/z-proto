package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.factory.PayLoadFactory;

public interface PayLoadExtractor {
    PayLoad getPayLoad(Object object) throws BuilderServiceException;
    PayLoadFactory<?> create(Object object);
}
