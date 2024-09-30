package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.fin.CreateUserFinApiResponseDto;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import com.ssamba.petsi.user_service.global.util.HttpEntityUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FinApiService {

    @Value("${fin.api-url}")
    private final String FIN_API_URL;
    @Value("${fin.api-key}")
    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public String addMember(String email) {
      HttpEntity<Map<String, Object>> request = HttpEntityUtil.createHttpEntity(MediaType.APPLICATION_JSON, createFinApiDto(email));
        ParameterizedTypeReference<CreateUserFinApiResponseDto> responseType =
                new ParameterizedTypeReference<>() {
                };

        try {
            ResponseEntity<CreateUserFinApiResponseDto> response
                    = restTemplate.exchange(FIN_API_URL, HttpMethod.POST, request, responseType);
            return response.getBody().getUserKey();
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.FINAPI_REGISTER_ERROR);
        }
    }

    private Map<String, Object> createFinApiDto(String email) {
        Map<String, Object> finApiDto = new HashMap<>();
        finApiDto.put("apiKey", apiKey);
        finApiDto.put("userId", email);
        return finApiDto;
    }
}
