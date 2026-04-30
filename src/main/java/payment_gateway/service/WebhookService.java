package payment_gateway.service;

//payment logic!= notification logic
//when payment happens webhook is triggered automatically
import org.springframework.stereotype.Service;
import payment_gateway.model.Payment;

@Service
public class WebhookService {
    public void sendPaymentUpdate(Payment payment){
        // Simulate webhook
        System.out.println("🔔 Webhook Triggered!");
        System.out.println("Transaction ID: " + payment.getTransactionId());
        System.out.println("Status: " + payment.getStatus());
        System.out.println("Merchant ID: " + payment.getMerchantId());

    }

}
