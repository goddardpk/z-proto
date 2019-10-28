package com.zafin.zplatform.proto;


import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * There is a very simple API but there is very important separation of concern that
 * needs to be understood.
 * <BR>
 * The BuilderPopulator knows the values it 'can' populate while its internal factory
 * knows the values that 'must' be populated.
 * <BR>
 * A Builder Populator is initialized with the factory of mandatory values.
 * <BR>
 * It is helpful to mentally map that Payload data NEEDS builder populators. 
 * <BR>
 * Builders are NOT directly owned by the client system, but are a product of some internal integration solution.
 * An extraction system is configured with specific (versioned) builder populators.
 * To avoid binding the z-api too closely to a specfic protocol implementation, 
 * the API forces client to both express the target type (T) and the builder
 * type (B). This decision allows system to be a little more future proof in cases to support
 * other builders if internal framework builder is not satisfactory.
 * <BR>
 * From a client perspsective: 'how' a builder builds is not a concern, but what is a 
 * concern is knowing that a Payload can be safely marshalled
 * into a valid target object instance. Again the object created is not owned by the client system.
 * The client system merely asks a builder service to produce an object instance based on a given payload.
 * This design allows client system to know immediately if a given Payload cannot produce a valid target object for 
 * a given target (schema) version. In this regard the system should remain in a consistent state 
 * since there is no assumptions regarding object instance validity and any schema violations are discovered early.
 * <BR>
 * It should also be clear that the client system requesting an Object to be built from a given payload
 * does NOT have to be the source system or the target system. The client system initiating the
 * build process can be an extraction process which request a 'Payload' from the source system
 * and then delegates Payload to a client service responsible for building a given target object to be
 * consumed by a given target (schema) system.
 * 
 * @author Paul Goddard
 *
 * @param <T> Type to Build
 * @param <B> Current Builder to Use
 * @param <O> Old Builder used
 */
public interface BuilderPopulator<T,B,O> {
	/**
	 * 
	 * @param payload
	 * @return List of populated builders ordered oldest to newest
	 * @throws BuilderServiceException
	 */
    B seed(PayLoad payload) throws BuilderServiceException;
    void setPreviousPopulator(BuilderPopulator<?,?,?> previous) throws BuilderServiceException;
    BuilderPopulator<?,?,?> getPreviousPopulator();
    B getCurrentBuilder();
    void setCurrentBuilder(B builder);
    TransferState<O,B> getTransferState();
    void setTransferState(TransferState<O,B> transferState) throws BuilderServiceException;
    PayLoad loadTestPayLoad(PayLoad payload) throws BuilderServiceException;
    /**
     * Transfer state from oldest populator to next oldest populator
     * @return true if all states were moved to most current populator
     */
    boolean transferStateForward(PayLoad payload) throws BuilderServiceException;
	boolean canConvert(Object object, Class<?> toType);
	Object convert(Object object, Class<?> toType) throws BuilderServiceException;
	TypeConverter getTypeConverter();
	List<Class<?>> getSupportedTypes() throws BuilderServiceException;
    String getSupportedPackageName() throws BuilderServiceException;
    int getRevision() throws BuilderServiceException;
    B transferState(PayLoad payLoad) throws BuilderServiceException;
}
