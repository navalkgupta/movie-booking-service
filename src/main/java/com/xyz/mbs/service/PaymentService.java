package com.xyz.mbs.service;

import com.xyz.mbs.enums.PaymentMode;
import com.xyz.mbs.model.Payment;
import com.xyz.mbs.model.Refund;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public interface PaymentService {
    default List<PaymentMode> getPaymentOptions(){
        return Arrays.stream(PaymentMode.values()).toList();
    };

    Payment makePayment(Payment payment);

    Refund initiateRefund(Payment payment, double amount);
}
