package com.zafin.zplatform.proto;


import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * This Client does not care about transport technologies. (HTTP,Reactive,Rest,Soap,Blah)
 * 
 * Builder Clients eventually have a responsibility of creating a record type (T) instance from a payload.
 * 
 * Clients can test their environment.
 * 
 * Client 'Testing' may check if a payload can produce a record type (T)
 * 
 * Client 'Testing' may check if a target system is available to process payload
 * 
 * Client 'Testing' may check if a target can process a record using a specific payload.
 * 
 * @author Paul Goddard
 *
 * @param <T> type of record
 * @param <B> builder of record
 * @param <O> old builder of record
 */
public interface Client<T, B, O> extends Builder<T,B,O> {
    
    Builder<?,?,?> getPreviousBuilder();
    void setPreviousBuilder(Builder<?,?,?> client);
    
    Class<?> getSupportedClient();
    
    Class<?> getServiceInterface();
    void setServiceInterface(Class<?> serviceInterface);

    void startService(Class<?> clazz);
    
    /**
     * The build process is decomposed into 2 phases: 
     * 1. Build a schema compliant record. 
     * 2. Build a business compliant record.
     * 
     * @param payload
     * @return
     * @throws BuilderServiceException
     */
    T create(PayLoad payload) throws BuilderServiceException;
    
    T build(Object builder);
    
    T create(B builder);
    
    VersionedProtocolConfiguration getVersionedProtocolConfiguration() throws BuilderServiceException;
    
    /**
     * A test payload acts as a sort of ping request to verify that a client can reach its corresponding server
     * All client implementations should support a mechanism to load a test payload so that it can verify its environment.
     * @return
     * @throws BuilderServiceException 
     */
    boolean loadTestPayLoad() throws BuilderServiceException;
    
}