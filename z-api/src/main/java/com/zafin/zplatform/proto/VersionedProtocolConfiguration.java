package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface VersionedProtocolConfiguration<T,B,O> {
	 TransferState<?,?,?> getTransferState()throws BuilderServiceException;
	 BuilderPopulator<T,B,O> getAlertBuilderPopulator() throws BuilderServiceException;
	 Builder<T,B,O> getBuilder() throws BuilderServiceException;
	 void testConfiguration() throws BuilderServiceException;
	 Client<T,B,O> getClient() throws BuilderServiceException;
	 VersionedProtocolConfiguration<?,O,?> getPreviousConfiguration();
	 void setPreviousConfiguration(VersionedProtocolConfiguration<?,O,?> versionedProtocolConfiguration) throws BuilderServiceException;
	 boolean validated();
	 int getRevision();
}
