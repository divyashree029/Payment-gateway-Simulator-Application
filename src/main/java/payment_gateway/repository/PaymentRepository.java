package payment_gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;  //ready made db oerations
import payment_gateway.model.Payment; //works on payment entity

import java.util.Optional;

//advanced level: use of query method derivation e.g; List<Payment> findByStatus(String status);

public interface PaymentRepository extends JpaRepository<Payment, Long>  //says that it will perform db operation on payment table and also primary key in payment was long for id
{
    Optional<Payment> findByTransactionId(String transactionId);
    //use of optional yake andre payment may exit and may not exist too
}
