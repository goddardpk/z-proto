package com.zafin.zplatform.proto;


/**
 * Every system record will have an API for its build process.
 * If a system has a Foo record then there will be:
 * 1. A FooService
 * 2. A FooException
 * 
 * @param <T> type of record to be created
 * @param <B> type of builder to be used to create record
 * 
 * @author Paul Goddard
 *
 */
public interface RemoteBuilderService<T,B> {
    //Client<T,B> getClient();
    //Class<?> getServiceInterface();
    Builder<T,B> seedBuilder(PayLoad payload, Builder<T,B> builder) throws BuilderServiceException;
    T build(PayLoad payLoad, Builder<T,B> builder) throws BuilderServiceException;
    BuilderPopulator<T,B> getBuilderPopulator();
}
