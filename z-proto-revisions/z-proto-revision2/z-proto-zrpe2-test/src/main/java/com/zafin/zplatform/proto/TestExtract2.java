package com.zafin.zplatform.proto;

import java.util.ArrayList;
import java.util.List;

import com.zafin.zplatform.proto.alert.AlertTestPayLoad1;
import com.zafin.zplatform.proto.alert.AlertTestPayLoad2;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.service.StartupArgs;

public class TestExtract2<T,B> {
    static int numTargetSystemsPerChangeSet = 2;
    
    @SuppressWarnings("unchecked")
    private ServiceRegistry<T,B> createService(String configClass, String applicationClass, StartupArgs args) {
        return SpringServiceRegistryEntry.builder()
                .setSpringConfig(configClass)
                .setSpringApplicationClass(applicationClass)
                .setArgs(args)
                .build();
    }
    
    public List<TargetSystem> getMockTargetSystems(String targetName,Client<?,?> client) {
        List<TargetSystem> list = new ArrayList<>();
        TargetSystemChangeSetValidator mockValidator =  new TargetSystemChangeSetValidator(client);
        for (int i=0;i<numTargetSystemsPerChangeSet;i++) {
            list.add(new TargetSystem(targetName + "-"+i ,mockValidator));
        }
        return list;
    }
    
    public void assertPass(PayLoad stubData, Client<?,?> client) throws BuilderServiceException {
            List<TargetSystem>  mockTargets = getMockTargetSystems("MockTargetSystems",client);
            ChangeSet mockChangeSet = new MockChangeSet2(stubData);
            if (!canCreatePayLoad(client,mockTargets, mockChangeSet)) {
                throw new IllegalStateException("Unable to create payload for target systems [" + mockTargets + "].");
            }
    }
    
    public void assertFail(PayLoad stubData, Client<?,?> client) {
        try {
            assertPass(stubData,client);
        } catch (Exception e) {
            System.out.println("Failed as expected: " + e.getMessage());
        }
        throw new IllegalStateException("Expecting to fail.");
    }
    
    public boolean canCreatePayLoad(Client<?,?> client, List<TargetSystem> targetSystems, ChangeSet testChangeSet) throws BuilderServiceException {
        boolean passed = false;
        PayLoad payload = new ExtractProcessor().getPayLoad(testChangeSet, targetSystems);
        if (payload != null) {
            passed = true;
        }
        return passed;
    }

    protected PayLoad decorate(PayLoad payload) {
        return payload; //Does not decoration by default
    }
    
    public void regressionTest(PayLoad testPayload) throws BuilderServiceException {
        throw new BuilderServiceException("Fix me");
    }
}
