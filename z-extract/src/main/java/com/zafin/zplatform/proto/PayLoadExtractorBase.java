package com.zafin.zplatform.proto;

import java.util.Collections;
import java.util.List;

public class PayLoadExtractorBase implements PayLoadExtractor {

    private final PayLoadFactory payLoadFactory;
    
    public PayLoadExtractorBase() {
        this.payLoadFactory = create();
    }
    
    private PayLoadFactory create() {
        return new ExtractorPayLoadFactory() {
            private PayLoadFactory previousPayLoadFactory;
            
            @Override
            public PayLoad create(Object source) {
                return null;
            }

            @Override
            public boolean isSupported(Object source) {
                boolean supported = false;
                if (source instanceof ChangeSet) {
                    supported = true;
                }
                return supported;
            }

            @Override
            public PayLoadFactory getPreviousPayLoadFactory() {
                return previousPayLoadFactory;
            }

            @Override
            public void setPreviousPayLoadFactory(PayLoadFactory previousPayLoadFactory) {
                this.previousPayLoadFactory = previousPayLoadFactory;
            }

            @Override
            public List<String> getAllMandatoryFields() {
                return Collections.emptyList();
            }

            @Override
            public List<String> getJustMyMandatoryFields() {
                return Collections.emptyList();
            }
        };
    }
    
    @Override
    public PayLoad getPayLoad(Object changeSet) {
        return payLoadFactory.create(changeSet);
    }

}
