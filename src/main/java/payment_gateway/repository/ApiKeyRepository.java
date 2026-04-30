package payment_gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payment_gateway.model.ApiKey;

import java.util.Optional;

public interface ApiKeyRepository  extends JpaRepository<ApiKey, Long> {

    Optional<ApiKey> findByKey(String key); //auto SQL generated


}
