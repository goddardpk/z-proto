package com.zafin.zplatform.proto;

import java.util.ArrayList;
import java.util.List;

import com.zafin.zplatform.proto.exception.BuilderServiceException;

public class TestExtract2<T,B,O> {
    public ExtractProcessor getExtractProcessor() {
		return extractProcessor;
	}

	public void setExtractProcessor(ExtractProcessor extractProcessor) {
		this.extractProcessor = extractProcessor;
	}

	static int numTargetSystemsPerChangeSet = 2;
    
    private ExtractProcessor extractProcessor;
    
    public List<TargetSystem> getMockTargetSystems(String targetName,Client<?,?,?> client) {
        List<TargetSystem> list = new ArrayList<>();
        TargetSystemChangeSetValidator mockValidator =  new TargetSystemChangeSetValidator(client);
        for (int i=0;i<numTargetSystemsPerChangeSet;i++) {
            list.add(new TargetSystem(targetName + "-"+i ,mockValidator));
        }
        return list;
    }
    
    public void assertPass(PayLoad stubData, Client<T,B,O> client) throws BuilderServiceException {
            List<TargetSystem>  mockTargets = getMockTargetSystems("MockTargetSystems",client);
            ChangeSet mockChangeSet = new MockChangeSet2(stubData);
            if (!canCreatePayLoad(client,mockTargets, mockChangeSet)) {
                throw new IllegalStateException("Unable to create payload for target systems [" + mockTargets + "].");
            }
    }
    
    public void assertFail(PayLoad stubData, Client<T,B,O> client) {
        try {
            assertPass(stubData,client);
        } catch (Exception e) {
            System.out.println("Failed as expected: " + e.getMessage());
        }
        throw new IllegalStateException("Expecting to fail.");
    }
    
    public boolean canCreatePayLoad(Client<T,B,O> client, List<TargetSystem> targetSystems, ChangeSet testChangeSet) throws BuilderServiceException {
        boolean passed = false;
        PayLoad payload = extractProcessor.getPayLoad(testChangeSet, targetSystems);
        if (payload != null) {
            passed = true;
        }
        return passed;
    }

    protected PayLoad decorate(PayLoad payload) throws BuilderServiceException {
        return payload; //Does not decoration by default
    }
    
    public void regressionTest(PayLoad testPayload) throws BuilderServiceException {
        throw new BuilderServiceException("Fix me");
    }
}
