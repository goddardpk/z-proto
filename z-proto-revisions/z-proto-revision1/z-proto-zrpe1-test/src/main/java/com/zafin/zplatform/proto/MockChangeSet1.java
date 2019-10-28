package com.zafin.zplatform.proto;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public class MockChangeSet1 extends ChangeSet {
    
    public MockChangeSet1(PayLoad stubData) throws BuilderServiceException {
        super(stubData);
    }
    
    @Override
    public boolean loadTestData() {
        return false;
    }
    
    @Override
    public String toString() {
        return "Mock: [" + super.toString() + "]";
    }
    
    public void remove(String key) {
        Object deletedObject = changes.remove(key);
        if (deletedObject == null) {
            throw new IllegalArgumentException("Key [" + key + "] is not in mock changeset.");
        }
    }

	@Override
	public boolean supportNullValues() {
		return false;
	}

}