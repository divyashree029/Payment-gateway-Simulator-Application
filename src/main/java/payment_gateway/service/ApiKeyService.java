package payment_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payment_gateway.repository.ApiKeyRepository;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;

    public boolean isValidKey(String key) {
        return apiKeyRepository.findByKey(key).isPresent();
    }
}