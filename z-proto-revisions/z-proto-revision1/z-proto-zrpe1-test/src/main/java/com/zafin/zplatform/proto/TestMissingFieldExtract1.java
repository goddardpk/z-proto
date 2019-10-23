package com.zafin.zplatform.proto;


import java.util.ArrayList;
import java.util.List;

import com.zafin.models.avro1.Alert;
import com.zafin.zplatform.proto.alert.AvroAlertPayLoadFactory1;
import com.zafin.zplatform.proto.alert.AlertTestPayLoad1;
import com.zafin.zplatform.proto.exception.BuilderServiceException;
import com.zafin.zplatform.proto.factory.PayLoadFactory;
import com.zafin.zplatform.proto.service.StartupArgs;

/**
 * Validate that PayLoadFactory is in-sync with underlying schema.
 * If a payload factory says a field is manadatory then the back-end schema process should fail when it 
 * tries to hydrate an object with 'missing' values.
 * 
 * @author Paul Goddard
 *
 */
public class TestMissingFieldExtract1<T,B> extends TestExtract1<T,B> {
   
   private final PayLoadFactory<T> payLoadFactory;
   private final int missingFieldIndexToRemove;
   private MockChangeSet1 mockChangeSet = null;
   
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
   
   public TestMissingFieldExtract1(PayLoadFactory<T> payLoadFactory, PayLoad testData, int missingFieldIndex) {
       this.payLoadFactory = payLoadFactory;
       this.missingFieldIndexToRemove = missingFieldIndex;
   }
   
   public MockChangeSet1 getMockChangeSet() {
       return mockChangeSet;
   }
   
   //Remove a mandatory field from testData
   @Override 
   public PayLoad decorate(PayLoad testData) {
       String key = payLoadFactory.getAllFields().get(missingFieldIndexToRemove);
       if (key == null) {
           System.out.println("Warning: No key value at index: " + missingFieldIndexToRemove);
       }
       mockChangeSet = new MockChangeSet1(testData);
       mockChangeSet.remove(key); //Decorate by taking away :)
       System.out.println("Removing mandatory key: [" +  key + "].");
       return mockChangeSet;
   }
   
   public static boolean test(PayLoadFactory<Alert> payLoadFactory, PayLoad testPayLoad) throws BuilderServiceException {
       TestMissingFieldExtract1<?,?> lastTest = null;
       int totalFieldsToTest = payLoadFactory.getAllFields().size();
       List<String> fieldPassed = new ArrayList<>();
       for (int fieldIndexToTest=0;fieldIndexToTest<totalFieldsToTest;fieldIndexToTest++) {
           boolean failed = false;
           System.out.println("Field test index: " + fieldIndexToTest + " of " + (totalFieldsToTest-1));
           try {
               //Instantiate a test where the mockchangeset (payload) is missing a manadatory field
               // as defined by payload factory. 
               lastTest = 
                       new TestMissingFieldExtract1<>(payLoadFactory,testPayLoad,fieldIndexToTest);
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
//       //AlertSpringConfig1.STARTUP_ARGS = startupArgs;
//       
//       //Test Missing Fields from revision 1 payload and factory
//       System.out.println("Using alert (rev 1) payload...");
//       AlertPayLoadFactory1<Alert> alertPayLoadFactory1 = new AlertPayLoadFactory1<>();
//       AlertTestPayLoad1<Alert> alertTestPayLoad1 = new AlertTestPayLoad1<>();
//       boolean fail = test(alertPayLoadFactory1, alertTestPayLoad1);
//       
//       if (!fail) {
//           System.out.println("Test Failed: Schema is out of sync with PayLoadFactory rev 1. Mandatory field defined in factory is not defined correctly in back-end schema definition.");
//           throw new IllegalStateException("Expected Builder Service Exception since mandatory field was missing.");
//       } else {
//           System.out.println("Test passed.");
//       }
//   }
}
