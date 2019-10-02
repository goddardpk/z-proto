package com.zafin.zplatform.proto;

import java.util.Map;

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
public interface RevisableBuilderService<T,B> extends RemoteBuilderService<T,B> {

    boolean isSubRevision(int revision);
    
    RevisableBuilderService<T,B> getPreviousCompatibleService();
    
    void setPreviousCompatibleService(RevisableBuilderService<T,B> previousCompatibleService);

    int getStartingCompatibleRevision();

    int getEndingCompatibleRevision();
    
    RevisableBuilderService<T,B> routeTo(Map<String, Object> props);

}
