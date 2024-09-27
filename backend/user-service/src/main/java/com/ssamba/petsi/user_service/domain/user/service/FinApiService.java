package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.fin.CreateUserFinApiResponseDto;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import com.ssamba.petsi.user_service.global.util.HttpEntityUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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

    private final String FIN_API_URL = "https://finopenapi.ssafy.io/ssafy/api/v1/member/";
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
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Object> createFinApiDto(String email) {
        Map<String, Object> finApiDto = new HashMap<>();
        finApiDto.put("apiKey", "2443ab151bab467083ba59ec8b9f6ef5");
        finApiDto.put("userId", email);
        return finApiDto;
    }
}
