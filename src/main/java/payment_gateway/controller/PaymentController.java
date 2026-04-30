package payment_gateway.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment_gateway.dto.PaymentRequest;
import payment_gateway.model.Payment;
import payment_gateway.service.PaymentService;

@RestController   //combines controller and response body-returns JSON response directly
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Payment> makePayment(
            @Valid @RequestBody PaymentRequest request) {
        // request body convert JSON to java obj
        //valid triggers validation to DTO
        Payment response = paymentService.processPayment(request);
        return ResponseEntity.ok(response);
    }
}
