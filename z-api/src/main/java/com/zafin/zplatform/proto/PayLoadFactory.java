package com.zafin.zplatform.proto;

import java.util.List;

public interface PayLoadFactory {
    
    PayLoad create(Object source);
    boolean isSupported(Object source);
    PayLoadFactory getPreviousPayLoadFactory();
    void setPreviousPayLoadFactory(PayLoadFactory previousPayLoadFactory);
    List<String> getAllMandatoryFields();
    List<String> getJustMyMandatoryFields();
}
