package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.Schema;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.service.RemoteBuilderService;

public interface ProtoFactory<T,B> extends TransferStateFactory {
	enum IDL  {AVRO,THRIFT, OTHER, UNKNOWN};	
	RemoteBuilderService<T, B> createService(Class<?> serviceClass) throws BuilderServiceException;
    int getRevision();
    Schema createSchema(T object, int revision);
}