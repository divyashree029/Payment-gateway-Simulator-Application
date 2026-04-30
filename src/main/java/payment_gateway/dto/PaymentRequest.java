package payment_gateway.dto;
//data transfer object: this receives data frm client and sends back safely
//use DTO- input
//use entity for db , bcz entity as input will cause security issues

import jakarta.validation.constraints.NotBlank; // ensures not null, not empty, no just spaces
import jakarta.validation.constraints.NotNull; //ensures not null
import lombok.Data;

@Data
public class PaymentRequest {

    @NotNull(message="Amount cannot be NULL")
    private Double amount;

    @NotBlank(message= "Payment method is required")
    private String method;

    @NotBlank(message="Merchant ID is required")
    private String merchantId;
}
