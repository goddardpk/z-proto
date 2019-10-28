package com.zafin.zplatform.proto;


import java.util.ArrayList;
import java.util.List;

import com.zafin.models.avro2.Alert;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.factory.PayLoadFactory;

/**
 * Validate that PayLoadFactory is in-sync with underlying schema.
 * If a payload factory says a field is manadatory then the back-end schema process should fail when it 
 * tries to hydrate an object with 'missing' values.
 * 
 * @author root
 *
 */
public class TestMissingFieldExtract2<T,B,O> extends TestExtract2<T,B,O> {
   
   private final PayLoadFactory<T> payLoadFactory;
   private final int missingFieldIndexToRemove;
   private MockChangeSet2 mockChangeSet = null;
   
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append(getClass().getSimpleName());
       sb.append("\nPayLoadFactory: " + payLoadFactory);
       sb.append("\nMissingFieldIndexToRemove: " + missingFieldIndexToRemove);
       sb.append("\nMockChangeSet: " + mockChangeSet);
       String key = payLoadFactory.getAllFields().get(missingFieldIndexToRemove);
       sb.append("\nKey removed: " + key);
       return sb.toString();
   }
   
   public TestMissingFieldExtract2(PayLoadFactory<T> payLoadFactory, PayLoad testData, int missingFieldIndex) {
       this.payLoadFactory = payLoadFactory;
       this.missingFieldIndexToRemove = missingFieldIndex;
   }
   
   public MockChangeSet2 getMockChangeSet() {
       return mockChangeSet;
   }
   
   //Remove a mandatory field from testData
   @Override 
   public PayLoad decorate(PayLoad testData) throws BuilderServiceException {
       String key = payLoadFactory.getAllFields().get(missingFieldIndexToRemove);
       if (key == null) {
           System.out.println("Warning: No key value at index: " + missingFieldIndexToRemove);
       }
       mockChangeSet = new MockChangeSet2(testData);
       mockChangeSet.remove(key); //Decorate by taking away :)
       System.out.println("Removing mandatory key: [" +  key + "].");
       return mockChangeSet;
   }
   
   public static boolean test(PayLoadFactory<Alert> payLoadFactory, PayLoad testPayLoad) throws BuilderServiceException {
       TestMissingFieldExtract2<Alert,Alert.Builder,com.zafin.models.avro1.Alert.Builder> lastTest = null;
       int totalFieldsToTest = payLoadFactory.getAllFields().size();
       List<String> fieldPassed = new ArrayList<>();
       for (int fieldIndexToTest=0;fieldIndexToTest<totalFieldsToTest;fieldIndexToTest++) {
           boolean failed = false;
           System.out.println("Field test index: " + fieldIndexToTest + " of " + (totalFieldsToTest-1));
           try {
               //Instantiate a test where the mockchangeset (payload) is missing a manadatory field
               // as defined by payload factory. 
               lastTest = 
                       new TestMissingFieldExtract2<>(payLoadFactory,testPayLoad,fieldIndexToTest);
               lastTest.regressionTest(testPayLoad);
           } catch (BuilderServiceException e) {
               failed = true;
           } catch (Exception e2) {
               throw (e2);
           }
           if (!failed) {
               String key = payLoadFactory.getAllFields().get(fieldIndexToTest);
               int revision = RevisionUtil.getRevisionFromClassName(payLoadFactory.getClass().getSimpleName());
               
               if (testPayLoad.isMandatory(key, revision)) {
                   fieldPassed.add("Required Field index: [" + lastTest.missingFieldIndexToRemove + "] was missing and should have caused build failure.");
               } else {
                   System.out.println("Field Key [" + key + "] for revision: [" + revision + "] is not required.");
               }
           }
       }
       if (!fieldPassed.isEmpty()) {
           System.out.println("Issues: " + fieldPassed);
       }
       return fieldPassed.isEmpty();
   }
   
//   public static void main(String[] args) throws BuilderServiceException {
//       System.out.println("Test Back-end schema is in sync with PayLoad Factory...");
//       
//       //TODO There must be a better way to preserve startup state used across Spring configuration 
//       //This (saving startup state) is a hack until a better solution avails itself.
//       //This is bad because it ties tests to Spring configuration artifacts
//       StartupArgs startupArgs = new StartupArgs(args);
//       //AlertSpringConfig2.STARTUP_ARGS = startupArgs;
//       
//       //Test Missing Fields from revision 1 payload and factory
//       System.out.println("Using alert (rev 1) payload...");
//       //Test Missing Fields from revision 2 payload and factory
//       AlertPayLoadFactory2<com.zafin.models.avro2.Alert> alertPayLoadFactory2 = new AlertPayLoadFactory2<com.zafin.models.avro2.Alert>();
//       AlertTestPayLoad2 <com.zafin.models.avro2.Alert>alertTestPayLoad2 = new AlertTestPayLoad2<com.zafin.models.avro2.Alert>();
//       boolean fail = test(alertPayLoadFactory2, alertTestPayLoad2);
//
//       if (!fail) {
//           System.out.println("Test failed: Schema is out of sync with PayLoadFactory rev 2. Mandatory field defined in factory is not defined correctly in back-end schema definition.");
//           throw new IllegalStateException("Expected Builder Service Exception since mandatory field was missing.");
//       } else {
//           System.out.println("Test passed.");
//       }
//       
//   }
}
