package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface PayLoadExtractor {
    PayLoad getPayLoad(Object changeSet) throws BuilderServiceException;
}
