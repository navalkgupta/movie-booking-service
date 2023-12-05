package com.xyz.mbs.service.impl;

import com.xyz.mbs.enums.PaymentMode;
import com.xyz.mbs.enums.PaymentStatus;
import com.xyz.mbs.enums.PaymentType;
import com.xyz.mbs.model.Payment;
import com.xyz.mbs.model.Refund;
import com.xyz.mbs.repository.PaymentRepository;
import com.xyz.mbs.service.PaymentGatewayInterface;
import com.xyz.mbs.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentGatewayInterface paymentGatewayInterface;

    public Payment makePayment(Payment payment) {
        Map<String, Object> pgResponse = callToPG(createPgRequest(payment.getBooking().getId(), payment.getAmount(),
                payment.getPaymentMode(), PaymentType.PAYMENT));
        payment.setPaymentRefId((String)pgResponse.get("refId"));
        payment.setPaymentStatus((PaymentStatus)pgResponse.get("status"));
        return paymentRepository.save(payment);
    }

    public Refund initiateRefund(Payment payment, double amount){
        Refund refund = new Refund();
        refund.setBooking(payment.getBooking());
        refund.setAmount(amount);
        refund.setPaymentMode(payment.getPaymentMode());
        Map<String, Object> pgRequest = createPgRequest(refund.getBooking().getId(), refund.getAmount(),
                refund.getPaymentMode(), PaymentType.REFUND);
        pgRequest.put("paymentRefId", payment.getPaymentRefId());

        Map<String, Object> pgResponse = callToPG(pgRequest);
        refund.setPaymentRefId((String)pgResponse.get("refId"));
        refund.setPaymentStatus((PaymentStatus)pgResponse.get("status"));
        return paymentRepository.save(refund);
    }

    private Map<String, Object> createPgRequest(long bookingId, double amount,
                                                PaymentMode paymentMode, PaymentType paymentType){
        Map<String, Object> pgRequest = new HashMap<>();
        pgRequest.put("bookingId", bookingId);
        pgRequest.put("amount", amount);
        pgRequest.put("type", paymentType.name());
        pgRequest.put("pg", paymentMode);
        return pgRequest;
    }

    private Map<String, Object> callToPG(Map<String, Object> pgRequest){
        Map<String, Object> pgResponse = new HashMap<>();
        switch ((PaymentMode)pgRequest.get("pg")){
            case CREDIT_CARD -> {
                System.out.println("Payment through credit card..");
                pgResponse = paymentGatewayInterface.pay(pgRequest, PaymentStatus.SUCCESS);
            }
            case NET_BANKING -> {
                System.out.println("Payment through net banking..");
                pgResponse = paymentGatewayInterface.pay(pgRequest, PaymentStatus.FAILURE);
            }
            case UPI -> {
                System.out.println("Payment through UPI..");
                pgResponse = paymentGatewayInterface.pay(pgRequest, PaymentStatus.SUCCESS);
            }
        }
        return pgResponse;
    }
}
