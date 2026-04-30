package payment_gateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import payment_gateway.dto.RefundRequest;
import payment_gateway.model.Payment;
import payment_gateway.service.RefundService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/refund")
    public Payment refund(@RequestBody RefundRequest request) {
        return refundService.processRefund(request);
    }
}