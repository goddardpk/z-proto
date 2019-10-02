package com.zafin.zplatform.proto;

import java.util.Map;

public interface PayLoad {

    PayLoad put(String key, Object value);
    Object get(String key);
    Map<String, Object> getAllProperties();
    boolean isMandatory(String key,int revision);
    void load();
    void setPreviousPayLoad(PayLoad previousPayLoad);
    PayLoad getPreviousPayLoad();
    int getRevision();
}