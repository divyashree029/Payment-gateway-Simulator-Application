package payment_gateway.service;
// this takes payment req and converts it to payment & simulates bank response, saves to db

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payment_gateway.dto.PaymentRequest;
import payment_gateway.model.Payment;
import payment_gateway.repository.PaymentRepository;

import java.util.Random;
import java.util.UUID;

@Service //makes this a business logic layer
@RequiredArgsConstructor //auto generates constructor for final fields
public class PaymentService {
    private final PaymentRepository paymentRepository; //spring injects the repository automatically
    private final WebhookService webhookService;//So PaymentService can call WebhookService

    public Payment processPayment(PaymentRequest request){

        //creating payment entity (DTO->Entity)
        Payment payment = new Payment();
        payment.setAmount(request.getAmount()); //here we are converting dto->entity
        payment.setPaymentMethod(request.getMethod());
        payment.setMerchantId(request.getMerchantId());

        //Generate Transaction ID, uuid=universal unique identifier
        payment.setTransactionId(UUID.randomUUID().toString());  //every payment with unique transaction id

        //simulate bank response
        int random = new Random().nextInt(3); //success/failed/pending

        if (random == 0) {
            payment.setStatus("SUCCESS");
        } else if (random == 1) {
            payment.setStatus("FAILED");
        } else {
            payment.setStatus("PENDING");
        }

        //save this to db
        //return paymentRepository.save(payment);

        Payment savedPayment = paymentRepository.save(payment);//stores in db
        webhookService.sendPaymentUpdate(savedPayment);// Trigger webhook-here it calls another service
        return savedPayment;//sends result to client
    }

}
