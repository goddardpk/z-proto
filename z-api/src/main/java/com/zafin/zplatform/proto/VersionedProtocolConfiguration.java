package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface VersionedProtocolConfiguration {
	 TransferState<?,?> getTransferState()throws BuilderServiceException;
	 BuilderPopulator<?,?> getAlertBuilderPopulator() throws BuilderServiceException;
	 Builder<?,?> getBuilder() throws BuilderServiceException;
	 void testConfiguration() throws BuilderServiceException;
	 Client<?,?> getClient() throws BuilderServiceException;
	 VersionedProtocolConfiguration getPreviousConfiguration();
	 void setPreviousConfiguration(VersionedProtocolConfiguration versionedProtocolConfiguration) throws BuilderServiceException;
	 boolean validated();
}
