package com.example.PaymentGatewaySimulator.dto;

import com.example.PaymentGatewaySimulator.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentStatusRequest {

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;
}