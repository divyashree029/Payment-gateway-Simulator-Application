package com.example.PaymentGatewaySimulator.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RefundIdGenerator {

    public String generateRefundId() {

        String random = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        return "REF-" + random;
    }
}