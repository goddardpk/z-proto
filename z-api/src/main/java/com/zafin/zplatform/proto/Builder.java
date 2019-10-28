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
public interface Builder<T,B,O> extends Cloneable {
    List<?> seedOldBuilderFirst(PayLoad payload) throws BuilderServiceException;
    /**
     * Stateless build call.
     * Use pre-identified test payload to insure build() works.
     * @return a test object
     * @throws BuilderServiceException 
     */
    T build() throws BuilderServiceException;
    /**
     * Return a test instance of the supported builder.
     * @return
     */
    B getNativeBuilder();
    /**
     * Every builder has a corresponding builder populator which the builder delegates to.
     * The populator exclusively handles the population requirements of the builder.
     * @return
     */
    BuilderPopulator<T,B,O> getBuilderPopulator();
    /**
     * This protocol design leverages a kind of 'backwards chain of responsiblity' design pattern.
     * Builders, State transfer, and populators all delegate to their previous versions. Generally
     * chain of responsibility insure that if a current instance cannot handle call it can recurse backwards until
     * if finds a recipient who can handle request.
     * This backwards chain of responsiblity bakes backwards compatibility into the design by recursively
     * dispatching to earliest supported version and then works forward by accumulating backward states 
     * into the next version. This strategy provides a more honest process of both validating the old builders can still 
     * operate and also that newer builders can capture old builder state and move it forward. 
     * 
     * @return
     */
    Builder<?,?,?> getPreviousBuilder();
    void setPreviousBuilder(Builder<?,?,?> previous);
    /**
     * Stateful build call
     * @param builder to use
     * @return
     */
    T build(Object builder);
    /**
     * If you throw a service (into the wild...a mesh of services),
     * it should be able to test the environment in which it is to operate.
     * 
     * @throws BuilderServiceException
     */
    void test() throws BuilderServiceException;
    
    List<PayLoad> getTestPayLoads() throws BuilderServiceException;
    void testPayLoads() throws BuilderServiceException;
	boolean addTestPayLoad(PayLoad payload) throws BuilderServiceException;
	Class<T> getClassToBuild();
	Class<B> getCurrentBuilderClass();
	Class<O> getPreviousBuilderClass();

}
