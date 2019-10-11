package com.zafin.zplatform.proto;

import java.util.Collection;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface SchemaRegistry {
	boolean isSchemaRegistered(Schema schema);
	Schema getSchema(Object object, int revision);
	boolean addSchema(Schema schema) throws BuilderServiceException;
	Collection<Schema> getSchemas();
}
