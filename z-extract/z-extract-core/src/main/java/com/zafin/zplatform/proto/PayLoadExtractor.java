package com.zafin.zplatform.proto;

public interface PayLoadExtractor {
    PayLoad getPayLoad(Object changeSet) throws BuilderServiceException;
}
