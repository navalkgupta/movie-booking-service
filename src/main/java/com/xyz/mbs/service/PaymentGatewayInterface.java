package com.xyz.mbs.service;

import com.xyz.mbs.enums.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class PaymentGatewayInterface {
    public Map<String, Object> pay(Map<String, Object> pgRequest, PaymentStatus paymentStatus){
        Map<String, Object> pgResponse = new HashMap<>();
        pgResponse.put("status", paymentStatus);
        pgResponse.put("refId", pgRequest.hashCode());
        return pgResponse;
    }
}
