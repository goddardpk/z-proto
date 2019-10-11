package com.zafin.zplatform.proto;

import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public class TargetSystem {
    private final String name;
    private final TargetSystemChangeSetValidator validator;
    
    public TargetSystem(String name, TargetSystemChangeSetValidator validator) {
        this.name = name;
        this.validator = validator;
    }
    
    public String getName() {
        return name;
    }
    public List<String> reasonForNotAccepting(ChangeSet changeSet) throws BuilderServiceException {
        return validator.reasonForNotAccepting(this,changeSet);
    }
    
    @Override
    public String toString() {
        return "[TargetSystem: " + name + "]";
    }
    
    public List<String> acceptChange(PayLoad oldChange, PayLoad newChange) {
        return validator.acceptChange(oldChange, newChange);
    }
}
