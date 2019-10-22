package com.zafin.zplatform.proto;

import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public interface ExtractProcessor {

	PayLoad getPayLoad(ChangeSet changeSet, List<TargetSystem> targetSystems) throws BuilderServiceException;

}