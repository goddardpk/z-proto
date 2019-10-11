package com.zafin.zplatform.proto;

import java.util.List;

public interface Schema {
	List<Field> getFields();
	int getRevision();
	boolean supports(String fieldName);
	Class<?> getRegisteredClass();
}
