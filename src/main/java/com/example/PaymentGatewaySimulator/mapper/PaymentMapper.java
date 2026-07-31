package com.example.PaymentGatewaySimulator.mapper;

import com.example.PaymentGatewaySimulator.dto.PaymentRequest;
import com.example.PaymentGatewaySimulator.dto.PaymentResponse;
import com.example.PaymentGatewaySimulator.entity.Payment;

  
public class PaymentMapper {

    public static Payment toEntity(PaymentRequest request){
        return Payment.builder()
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .build();
    }

    public static PaymentResponse toResponse(Payment payment){
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}
