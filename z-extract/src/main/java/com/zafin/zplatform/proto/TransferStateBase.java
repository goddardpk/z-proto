package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.TransferState;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
/**
 * Common secret sauce for moving state from old type to new type.
 * 
 * @author Paul Goddard
 *
 * @param <O> old type
 * @param <N> new type
 */
public abstract class TransferStateBase<O,N> implements TransferState<O,N>{
	
	protected final Class<O> SOURCE;
	protected final Class<N> TARGET;
	
	@SuppressWarnings("unchecked")
	public TransferStateBase(O oldState, N newState) {
		TARGET = (Class<N>) newState.getClass();
		SOURCE = (Class<O>) oldState.getClass();
	}
	
	@Override
	public N transferState(Object oldState) throws BuilderServiceException {
		@SuppressWarnings("unchecked")
		O stateInOldObject = (O) oldState;
		N sameStateInNewObject = (N) convert(stateInOldObject);
		return sameStateInNewObject;
	}

	@Override
	public Class<?> getNewType() {
		return TARGET;
	}

	@Override
	public Class<?> getOldType() {
		return SOURCE;
	}
	
	public abstract N convert(O oldState);

}
