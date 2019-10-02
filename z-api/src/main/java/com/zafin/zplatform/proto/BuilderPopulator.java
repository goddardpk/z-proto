package com.zafin.zplatform.proto;
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
 * @param <T>
 * @param <B>
 */
public interface BuilderPopulator<T,B> {
    Builder<T,B> seedBuilder(PayLoad payload,Builder<T,B> builder) throws BuilderServiceException;
}
