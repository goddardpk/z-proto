package com.zafin.zplatform.proto;

public class MockChangeSet2 extends ChangeSet {
    
    public MockChangeSet2(PayLoad stubData) {
        super(stubData);
    }
    
    @Override
    public void load() {
        // TODO Auto-generated method stub
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

}