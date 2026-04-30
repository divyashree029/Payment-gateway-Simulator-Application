package payment_gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import payment_gateway.repository.ApiKeyRepository;
import payment_gateway.model.ApiKey;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final ApiKeyRepository apiKeyRepository;


    public boolean isValidKey(String Key){
        return apiKeyRepository.findByKey(Key).isPresent();  //api exist in db? it will check if exist or not
    }


}
