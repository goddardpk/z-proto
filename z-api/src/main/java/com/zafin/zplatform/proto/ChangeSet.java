package com.zafin.zplatform.proto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public abstract class ChangeSet implements PayLoad {
    private PayLoad previousChangeSet;
    protected final Map<String, Object> changes = new HashMap<>();
    private final int revision;
    
    public ChangeSet(PayLoad payLoad) throws BuilderServiceException {
        if (payLoad == null) {
            throw new IllegalArgumentException("Null payload ");
        }
        revision = payLoad.getRevision();
        for (Map.Entry<String, Object> entry: payLoad.getAllProperties().entrySet()) {
            put(entry.getKey(),entry.getValue());
        }
    }

    @Override
    public PayLoad put(String key, Object value) {
        changes.put(key, value);
        return this;
    }

    @Override
    public Object get(String key) {
        return changes.get(key);
    }

    @Override
    public Map<String, Object> getAllProperties() {
        return Collections.unmodifiableMap(changes);
    }

    @Override
    public boolean isMandatory(String key, int revision) {
        return false;
    }

    @Override
    public void setPreviousPayLoad(PayLoad previousPayLoad) {
        this.previousChangeSet = previousPayLoad;
    }

    @Override
    public PayLoad getPreviousPayLoad() {
        return previousChangeSet;
    }
    
    @Override
    public String toString() {
        return  "ChangeSet: [" + getAllProperties().toString() + "]";
    }
    
    @Override
    public int getRevision() {
        return revision;
    }

}
