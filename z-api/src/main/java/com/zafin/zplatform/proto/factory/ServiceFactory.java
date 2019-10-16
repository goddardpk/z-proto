package com.zafin.zplatform.proto.factory;

import com.zafin.zplatform.proto.service.RemoteBuilderService;
/**
 * TODO: May be able to remove if we assume client framework can lookup or create service on its own
 * @author root
 *
 * @param <T>
 * @param <B>
 */
public interface ServiceFactory<T,B> {
	RemoteBuilderService<T,B> createRemoteBuilderService(Class<?> clazz);
}
