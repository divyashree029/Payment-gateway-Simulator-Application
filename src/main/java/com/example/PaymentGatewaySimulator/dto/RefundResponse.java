package com.example.PaymentGatewaySimulator.dto;

import com.example.PaymentGatewaySimulator.enums.RefundStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundResponse {

    private Long id;
    private String refundId;
    private Long paymentId;
    private String transactionId;
    private BigDecimal amount;
    private RefundStatus refundStatus;
    private LocalDateTime createdAt;
}