package com.zafin.zplatform.proto;

import java.util.Map;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface PayLoad {
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws BuilderServiceException can be thrown if PayLoad configured not to support null values
	 */
    PayLoad put(String key, Object value) throws BuilderServiceException;
    Object get(String key) throws BuilderServiceException;
    Map<String, Object> getAllProperties();
    boolean isMandatory(String key,int revision) throws BuilderServiceException;
    boolean loadTestData() throws BuilderServiceException;
    void setPreviousPayLoad(PayLoad previousPayLoad);
    PayLoad getPreviousPayLoad();
    int getRevision() throws BuilderServiceException;
    boolean supportNullValues();
}