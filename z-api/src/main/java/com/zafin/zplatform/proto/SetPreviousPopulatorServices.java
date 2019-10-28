package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.service.RemoteBuilderService;

public interface SetPreviousPopulatorServices<T,B,O> {
	RemoteBuilderService<?,?,?> setup(RemoteBuilderService<?,?,?> remoteBuilderService);
	/**
	 * @param revision
	 * @return true if revision is supported and (revision-1) is not supported
	 */
	boolean isLastSupportedRevision(int revision);
}
