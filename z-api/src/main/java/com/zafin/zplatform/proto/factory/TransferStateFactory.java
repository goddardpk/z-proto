package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.TransferState;
import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface TransferStateFactory {
	TransferState<?,?> createTransferState(Object oldObject, Object newObject) throws BuilderServiceException;
}
