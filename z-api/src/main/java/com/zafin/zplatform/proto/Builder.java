package com.zafin.zplatform.proto;
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
    T buildFrom(PayLoad payload)  throws BuilderServiceException;
    B createNewNativeBuilder();
    BuilderPopulator<T,B> getBuilderPopulator();
    Builder<T,B> getPreviousBuilder();
    void setPreviousBuilder(Builder<T,B> previous);
}
