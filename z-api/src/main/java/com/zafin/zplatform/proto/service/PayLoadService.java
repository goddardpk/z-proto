package com.zafin.zplatform.proto.service;


import com.zafin.zplatform.proto.PayLoad;

public interface PayLoadService {
    PayLoad createPayLoad(Object source)
            throws PayLoadException;
}
