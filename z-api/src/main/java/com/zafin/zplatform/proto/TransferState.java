package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * Transfer state from old component to another (newer) component of similar type.
 * @author Paul Goddard
 * 
 * @param <O> Old Component
 * @param <N> New Component
 */
public interface TransferState<O,N> {
	/**
	 * Transfer old state over to new state.
	 * This process is agnostic to any data profile constraints. This process will reflectively 
	 * inspect attribute names and transfer this attribute state from oldState component over to new state component.
	 * If the target component is missing an attribute, this transfer process will not abort.
	 * @param oldState
	 * @return
	 * @throws BuilderServiceException if anything goes wrong with state transfer
	 */
    N transferState(Object oldState, PayLoad payLoad) throws BuilderServiceException;
    Class<?> getNewType();
    Class<?> getOldType();
}
