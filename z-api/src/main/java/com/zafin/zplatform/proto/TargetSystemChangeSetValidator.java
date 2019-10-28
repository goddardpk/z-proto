package com.zafin.zplatform.proto;

import java.util.ArrayList;
import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public class TargetSystemChangeSetValidator {
    private final Client<?,?,?> client;
    public TargetSystemChangeSetValidator(Client<?,?,?> client) {
        if (client == null) {
            throw new IllegalArgumentException("Null client");
        }
        this.client = client;
    }
    
    public List<String> reasonForNotAccepting(TargetSystem targetSystem,ChangeSet changeSet) throws BuilderServiceException {
        PayLoad payload = changeSet;
        List<String> issues = new ArrayList<>();
        try {
            Object test = client.create(payload);
            if (test == null) {
                issues.add("Client unable to create object");
            }
            issues.addAll(targetSystem.acceptChange(changeSet.getPreviousPayLoad(), changeSet));
        } catch (BuilderServiceException e) {
            issues.add("Build Exception: " + e.getMessage());
        }
        if (!issues.isEmpty()) {
            throw new BuilderServiceException("Reason for not accepting changeset: " + changeSet + " are due to the following issues: " + issues);
        }
        return issues;
    }
    
    public List<String> acceptChange(PayLoad oldPayLoad, PayLoad newPayLoad) {
        //TODO does nothing real. Only needs a new payload
        //A rigorous validation process would see if old payload (last payload) was received
        List<String> issues = new ArrayList<>();
        if (newPayLoad == null) {
            issues.add("New PayLoad is null");
        }
        return issues;
    }
  
}
