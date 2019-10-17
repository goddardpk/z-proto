package com.zafin.zplatform.proto;

import java.util.Collection;

public interface TypeConverter {
	 boolean canConvert(Object object, Class<?> toType);
	 Object convert(Object object, Class<?> toType);
	 Collection<?> getSupportedTypes();
}
