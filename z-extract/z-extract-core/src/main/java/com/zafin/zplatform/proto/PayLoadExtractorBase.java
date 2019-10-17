package com.zafin.zplatform.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zafin.zplatform.proto.factory.PayLoadFactory;

public class PayLoadExtractorBase<T> implements PayLoadExtractor {

    private final PayLoadFactory<T> payLoadFactory;
    
    @SuppressWarnings("unchecked")
	public PayLoadExtractorBase(Schema schema) {
        this.payLoadFactory = ( PayLoadFactory<T>)create(schema);
    }
    
    private PayLoadFactory<?> create(Schema schema) {
        return new ExtractorPayLoadFactory<T>() {
            private PayLoadFactory<?> previousPayLoadFactory;
            
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
            public PayLoadFactory<?> getPreviousPayLoadFactory() {
                return previousPayLoadFactory;
            }

            @Override
            public void setPreviousPayLoadFactory(PayLoadFactory<?> previousPayLoadFactory) {
                this.previousPayLoadFactory = previousPayLoadFactory;
            }

            @Override
            public List<String> getAllFields() {
            	List<String> fields = new ArrayList<>();
            	if (previousPayLoadFactory != null) {
            		fields.addAll(previousPayLoadFactory.getAllFields());
            	} else {
            		fields.addAll(getJustMyFields());
            	}
                return fields;
            }

            @Override
            public List<String> getJustMyFields() {
                return schema.getFields().stream().map(x -> x.toString()).collect(Collectors.toList());
            }

			@Override
			public int getRevision() {
				return payLoadFactory.getRevision(); 
			}

			@Override
			public Class<T> getClazz() {
				return payLoadFactory.getClazz();
			}

        };
    }
    
    @Override
    public PayLoad getPayLoad(Object changeSet) throws BuilderServiceException {
        return payLoadFactory.create(changeSet);
    }

}
