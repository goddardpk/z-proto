package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.Schema;

public interface SchemaFactory<T> {
	Schema createSchema(T object, int revision);
}
