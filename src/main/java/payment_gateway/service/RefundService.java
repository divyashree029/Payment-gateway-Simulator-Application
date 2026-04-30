package payment_gateway.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payment_gateway.dto.RefundRequest;
import payment_gateway.model.Payment;
import payment_gateway.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final PaymentRepository paymentRepository; //service talks to db through repo

    public Payment processRefund(RefundRequest request){

        // Find payment
        Payment payment = paymentRepository
                .findByTransactionId(request.getTransactionId()) // take transactionId from request and search in db
                .orElseThrow(() -> new RuntimeException("Payment not found")); //if not found->throw error asthe

        // Check if already refunded
        if (payment.isRefunded()) {
            throw new RuntimeException("Already refunded");// preventing double refund
        }

        // Update status
        payment.setStatus("REFUNDED");
        payment.setRefunded(true);

        // Save
        return paymentRepository.save(payment);  //entity->JPA->SQL update->db
    }



}
