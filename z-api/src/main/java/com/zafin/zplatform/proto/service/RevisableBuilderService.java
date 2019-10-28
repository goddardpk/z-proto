package com.zafin.zplatform.proto.service;

import java.util.Map;

import com.zafin.zplatform.proto.BuilderPopulator;
import com.zafin.zplatform.proto.exception.BuilderServiceException;

/**
 * This Builder does not care about how a builder service creates a record.
 * 
 * This service is aware that build processes change with time.
 * Some build processes are backwards compatible and some are not.
 * A newer build process can re-use a previous build process (if it is backwards compatible).
 * 
 * If a previous build process cannot be used (breaking build), 
 * then the 'starting' revision number and the 'ending' revision number will be the same.
 * 
 * @author Paul Goddard
 *
 * @param <T>
 * @param <B>
 */
public interface RevisableBuilderService<T,B,O> extends RemoteBuilderService<T,B,O> {

    boolean isSubRevision(int revision) throws BuilderServiceException;
    
    RevisableBuilderService<T,B,O> getPreviousCompatibleService();
    
    void setPreviousCompatibleService(RevisableBuilderService<T,B,O> previousCompatibleService);

    int getStartingCompatibleRevision() throws BuilderServiceException;

    int getEndingCompatibleRevision() throws BuilderServiceException;
    
    RevisableBuilderService<T,B,O> routeTo(Map<String, Object> props);
    
    BuilderPopulator<T,B,O> getBuilderPopulator();

}
