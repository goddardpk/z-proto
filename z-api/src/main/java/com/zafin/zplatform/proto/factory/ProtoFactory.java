package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.Schema;
import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface ProtoFactory<T,B> extends TransferStateFactory {
	enum IDL  {AVRO,THRIFT, OTHER, UNKNOWN};	
    int getRevision() throws BuilderServiceException;
    Schema createSchema(T object, int revision);
}