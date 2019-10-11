package com.zafin.zplatform.proto.service;


import com.zafin.zplatform.proto.PayLoad;
import com.zafin.zplatform.proto.exception.PayLoadException;

public interface PayLoadService {
    PayLoad createPayLoad(Object source)
            throws PayLoadException;
}
