package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.Schema;

public interface ProtoFactory<T,B> extends TransferStateFactory {
	enum IDL  {AVRO,THRIFT, OTHER, UNKNOWN};	
    int getRevision();
    Schema createSchema(T object, int revision);
}