package com.example.PaymentGatewaySimulator.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class TransactionIdGenerator{
    public String generateTransactionId() {

        String date = LocalDate.now()
                .toString()
                .replace("-", "");

        String random =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        return "PAY-" + date + "-" + random;
    }


}
