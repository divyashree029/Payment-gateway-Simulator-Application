package payment_gateway.model;

// spring automatically creates table in mySQL
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity  //creates table
@Data
public class Payment {

    @Id   //primary key
    @GeneratedValue   //autoincrement id
    private long id;
    private double amount; //payment amount
    private String status;  //success or fail
    private String paymentMethod; //upi or card
    private String merchantId;  //who requested payment
    private String transactionId;  //unique id
    private LocalDateTime createdAt= LocalDateTime.now(); //timestamp

    private boolean refunded; // to avoid same payment twice: success=false, refunded=true

}
