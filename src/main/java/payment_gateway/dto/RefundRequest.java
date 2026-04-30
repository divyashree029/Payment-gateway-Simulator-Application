package payment_gateway.dto;


import lombok.Data;

@Data
public class RefundRequest {

    private String transactionId; //unique id of payment
    private Double amount;//amount to refund

}
