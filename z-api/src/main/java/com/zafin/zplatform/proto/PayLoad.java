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
    /**
     * Current thoughts are that Payloads do not need to know their target schemas and subsequently do not know if a field is required. 
     * However, for automated testing scenarios (where target schemas are known) it may possible to have this 'a priori knowledge'. Since changesets extend payload, a test changeset 
     * could be auto-generated to verify if a given target systems is not catching missing field data.
     * 
     * @param fieldName
     * @return True if it is known if field can be null and return null if it is not known and return False if it is known that null is not supported.
     * @throws BuilderServiceException
     */
    Boolean isNullSupported(String fieldName) throws BuilderServiceException;
    boolean loadTestData() throws BuilderServiceException;
    void setPreviousPayLoad(PayLoad previousPayLoad);
    PayLoad getPreviousPayLoad();
    int getRevision() throws BuilderServiceException;
    boolean supportNullValues();
}