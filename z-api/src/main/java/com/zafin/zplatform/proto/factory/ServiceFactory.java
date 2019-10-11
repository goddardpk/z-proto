package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.service.RemoteBuilderService;

public interface ServiceFactory<T,B> {
	RemoteBuilderService<T,B> createRemoteBuilderService(Class<?> clazz);
}
