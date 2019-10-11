package com.zafin.zplatform.proto.service;

import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * Every aggregate root object will have a common API for its build process.
 *
 * @param <T> type of record to be created
 * @param <B> type of builder to be used to create record
 * 
 * @author Paul Goddard
 *
 */
public interface RemoteBuilderService<T,B> {
	/**
	 * Honest process to insure backwards compatibility:
	 * If Builders contain all the required instantiation parameters,
	 * then is holds that the process of 'seeding state' can always be delegated to
	 * previous builders before attempting to populate the current builder.
	 *
	 * @param payload
	 * @return
	 * @throws BuilderServiceException  If a previous builder is unable to support the given payload a BuilderServiceException will be thrown.
	 */
    B seedOldBuilderFirst(PayLoad payload) throws BuilderServiceException;
    
    T build(B builder) throws BuilderServiceException;
    //B getBuilder() throws BuilderServiceException;
    
    //TransferState<?,?> getTransferState() throws BuilderServiceException;
    int getRevision();
}
