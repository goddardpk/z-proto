package com.zafin.zplatform.proto.service;

import java.util.List;

import com.zafin.zplatform.proto.BuilderPopulator;
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
public interface RemoteBuilderService<T,B,O> extends  BuilderPopulator<T,B,O> {
	RemoteBuilderService<T, B,O> setup(RemoteBuilderService<?,?,?> remoteBuilderService);
	/**
	 * Honest process to insure backwards compatibility:
	 * If Builders contain all the required instantiation parameters,
	 * then is holds that the process of 'seeding state' can always be delegated to
	 * previous builders before attempting to populate the current builder.
	 *
	 * @param payload
	 * @return A List of all 'seeded' builders starting with oldest first
	 * @throws BuilderServiceException  If a previous builder is unable to support the given payload a BuilderServiceException will be thrown.
	 */
    List<?> seedOldBuilderFirst(PayLoad payload) throws BuilderServiceException;
    
    T build(Object builder) throws BuilderServiceException;
    //B getBuilder() throws BuilderServiceException;
    
    //TransferState<?,?> getTransferState() throws BuilderServiceException;
    int getRevision() throws BuilderServiceException;
}
