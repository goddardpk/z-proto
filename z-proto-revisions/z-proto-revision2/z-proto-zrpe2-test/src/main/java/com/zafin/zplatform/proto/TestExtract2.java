package com.zafin.zplatform.proto;

import java.util.ArrayList;
import java.util.List;

import com.zafin.zplatform.proto.alert.AlertTestPayLoad1;
import com.zafin.zplatform.proto.alert.AlertTestPayLoad2;
import com.zafin.zplatform.proto.service.StartupArgs;
import com.zafin.zplatform.proto.alert.AlertSpringClient2;
import com.zafin.zplatform.proto.alert.AlertSpringConfig2;

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
    
    public Client<?,?> revision2SpringClient() {
        //Revision could be derived from internal to createService(...) but this will insure config files comply...
        return createService(AlertSpringConfig2.class.getCanonicalName(), 
                AlertSpringClient2.class.getCanonicalName(), 
                AlertSpringConfig2.STARTUP_ARGS)
                .getBean("alertClient");
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
    
    protected void testPayLoadWithClient2(PayLoad testPayload) throws BuilderServiceException {
        System.out.println("Test with client revision 2...");
        assertPass(decorate(testPayload), revision2SpringClient());
    }
    public void regressionTest(PayLoad testPayload) throws BuilderServiceException {
        testPayLoadWithClient2(testPayload);
    }
    public void testExtractWith2ClientRevisions(StartupArgs args) throws BuilderServiceException {
        //TODO There must be a better way to preserve startup state used across configuration 
        //This (saving startup state) is a hack until a better solution avails itself.
        //This is bad because it ties tests to Spring configuration artifacts
        
        AlertSpringConfig2.STARTUP_ARGS = args;
        
        System.out.println("Test Client (rev 2) with Alert Payload (rev 2)...");
        testPayLoadWithClient2(new AlertTestPayLoad2());
        System.out.println("Test Client (rev 2) with Alert Payload (rev 2) passed");
        
        System.out.println("Test Client (rev 2) with Alert Payload (rev 1)...");
        testPayLoadWithClient2(new AlertTestPayLoad1());
        System.out.println("Test Client (rev 2) with Alert Payload (rev 1) passed.");
        
    }
}
