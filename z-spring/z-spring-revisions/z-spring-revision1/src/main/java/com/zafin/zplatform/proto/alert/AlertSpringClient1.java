package com.zafin.zplatform.proto.alert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.zafin.zplatform.proto.Builder;
import com.zafin.zplatform.proto.BuilderServiceException;
import com.zafin.zplatform.proto.Client;
import com.zafin.zplatform.proto.ClientBase;
import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.SpringServiceRegistryEntry;
import com.zafin.zplatform.proto.service.AlertService;
import com.zafin.zplatform.proto.service.StartupArgs;

/**
 * This is a Spring implementation of calling a 'build' service.
 * 
 * @author Paul Goddard
 *
 * @param <T>
 * @param <B>
 */
public class AlertSpringClient1<T,B> extends ClientBase<T,B> {
    
    @Qualifier("testPayLoad")
    @Autowired
    private PayLoad testPayLoad;

    @Autowired
    private AlertService<T,B> alertService;
    
    @Autowired
    private Builder<T,B> builder;
    
    public static void main(String[] args) throws BuilderServiceException, ClassNotFoundException {
        Client<?,?> client =  null;
        SpringServiceRegistryEntry<?,?> registry = SpringServiceRegistryEntry.builder()
                .setSpringConfig(AlertSpringConfig1.class.getCanonicalName())
                .setSpringApplicationClass(AlertSpringClient1.class.getCanonicalName())
                .setArgs(new StartupArgs(args))
                .build();
        registry.run();
        client = registry.getBean("alertClient");
        client.test();
    }

    public T create(PayLoad payload) throws BuilderServiceException {
        return alertService.build(payload, builder);
    }
    

    @Override
    public void test() {
        try {
            T alert = create(testPayLoad);
            System.out.println("Alert created: " + alert);
        } catch (BuilderServiceException e) {
            throw new IllegalStateException("Unable to create Alert using test payload: [" + testPayLoad + "].",e);
        }
    }

    @Override
    public Class<?> getSupportedClient() {
        return AlertSpringClient1.class;
    }

}
