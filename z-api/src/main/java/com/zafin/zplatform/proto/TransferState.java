package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * Transfer state from old component to another (newer) component of similar type.
 * @author Paul Goddard
 * 
 * @param <O> Old Component
 * @param <N> New Component
 */
public interface TransferState<T,B,O> {
	Class<T> getTargetClassToBuild();
    Class<B> getCurrentBuilderClass();
    Class<O> getPreviousBuilderClass();
    /**
     * Returns True when this transfer state component thinks it is the last transfer node.
     */
    boolean isTerminal();
    TransferState<?,?,?> getPreviousTransferState();
    void setPreviousTransferState(TransferState<?,?,?> previousTransferState) throws BuilderServiceException;
    int getRevision();
    
    /**
     * A part of the state transfer process, the specific revisioned builder state can be extracted out to a 'generic' Avro type.
     * This 'generic' type can then be transported to a newer builder revision.
     *  
     * @param builderObject
     * @return a generic record according as defined by 3rd party protocol.
     * @throws BuilderServiceException
     */
    Object getGenericRecord(Object builderObject) throws BuilderServiceException;
    /**
     * Merge generic records.
     *  
     * @param oldGenericRecord
     * @param newGenericRecord
     * @return a Generic Record that is contains the merged valued from both the oldGeneric record and the newGeneric record
     * @throws BuilderServiceException
     */
    Object mergeGenericRecords(Object oldGenericRecord, Object newGenericRecord) throws BuilderServiceException;

    /**
     * @return newer builder revision populated with state from oldBuilder
     * @throws BuilderServiceException 
     */
    B transfer(O oldBuilder, B newEmptyBuilder,BuilderPopulator<T,B,O> builderPopulator) throws BuilderServiceException;
    Object getValue(Object objectKey, Object map) throws BuilderServiceException;
}
