package com.zafin.zplatform.proto;

import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
* Insulate system services from handling Avro generated builders directly.
* <BR><BR>
* The point of 'T' is the 'type' of record this builder process will produce.
* <BR>
* The point of 'B' is the 'Builder' to be used. 
* <BR>
* <BR>
* The build (micro-service) contract can be identified with this diminutive API. 
* 
**/
public interface Builder<T,B> extends Cloneable {
    List<?> seedOldBuilderFirst(PayLoad payload) throws BuilderServiceException;
    T build();
    B getNativeBuilder();
    BuilderPopulator<T,B> getBuilderPopulator();
    Builder<?,?> getPreviousBuilder();
    void setPreviousBuilder(Builder<?,?> previous);
    T build(Object builder);
}
