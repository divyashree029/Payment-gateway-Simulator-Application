package com.example.PaymentGatewaySimulator.controller;

import com.example.PaymentGatewaySimulator.dto.RefundRequest;
import com.example.PaymentGatewaySimulator.dto.RefundResponse;
import com.example.PaymentGatewaySimulator.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping("/{paymentId}/refunds")
    public ResponseEntity<RefundResponse> createRefund(
            @PathVariable Long paymentId,
            @Valid @RequestBody RefundRequest request) {

        return ResponseEntity.ok(
                refundService.createRefund(
                        paymentId,
                        request
                )
        );
    }
}
