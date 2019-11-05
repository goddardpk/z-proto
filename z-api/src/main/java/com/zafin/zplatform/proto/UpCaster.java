package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface UpCaster<O,N> {
	/**
	 * Most old values should easily port to new types if backward compatibility is supported.
	 * If a schema type introduces a 'breaking' schema change, then this method may throw a BuilderServiceException,
	 * since it may not be able to resolve how to map old value to new type.
	 * 
	 * An example of a breaking schema change is a newer schema which introduces tighter constraints for a existing
	 * fields. If a legacy payload is consumed which has looser (legacy) constraints, then the newer schema 
	 * will not be able to consume this value and this upcaster should complain.
	 * 
	 * @param oldType
	 * @param newType
	 * @param oldValue
	 * @return the oldValue using new type
	 * @throws BuilderServiceException
	 */
	Object castOldValueToNewType(Class<O> oldType, Class<N> newType, Object oldValue) throws BuilderServiceException;
}
