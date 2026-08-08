package com.example.PaymentGatewaySimulator.mapper;

import com.example.PaymentGatewaySimulator.dto.RefundResponse;
import com.example.PaymentGatewaySimulator.entity.Refund;

public class RefundMapper {

    private RefundMapper() {
    }

    public static RefundResponse toResponse(Refund refund) {

        return RefundResponse.builder()
                .id(refund.getId())
                .refundId(refund.getRefundId())
                .paymentId(refund.getPayment().getId())
                .transactionId(
                        refund.getPayment().getTransactionId()
                )
                .amount(refund.getAmount())
                .refundStatus(refund.getRefundStatus())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}