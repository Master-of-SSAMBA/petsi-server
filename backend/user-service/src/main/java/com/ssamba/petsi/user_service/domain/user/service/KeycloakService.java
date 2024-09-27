package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.request.CreateKeycloakUserRequestDto;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import com.ssamba.petsi.user_service.global.util.HttpEntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakService {
//    @Value("${keycloak.auth-server-url}")
//    private String keycloakServerUrl;
//    @Value("${keycloak.realm}")
//    private String realm;
//    @Value("${keycloak.resource}")
//    private String clientId;
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String createUserInKeycloak(CreateKeycloakUserRequestDto createKeycloakUserRequestDto) {
        Map<String, Object> keycloakUser = CreateKeycloakUserRequestDto.createKeycloakUser(createKeycloakUserRequestDto);
        HttpEntity<Map<String, Object>> request =
                HttpEntityUtil.createHttpEntity(MediaType.APPLICATION_JSON, keycloakUser, getKeycloakAdminAccessToken());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://j11a403.p.ssafy.io:8082/auth/admin/realms/petsi/users", request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(extractUserIdFromLocationHeader(response));
                return extractUserIdFromLocationHeader(response);
            } else {
                throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractUserIdFromLocationHeader(ResponseEntity<String> response) {
        String locationHeader = response.getHeaders().getLocation().toString();
        String[] segments = locationHeader.split("/");
        return segments[segments.length - 1];
    }

    private String getKeycloakAdminAccessToken() {
        HttpEntity<MultiValueMap<String, String>> request =
                HttpEntityUtil.createHttpEntity(MediaType.APPLICATION_FORM_URLENCODED, createKeycloakRequestBody());
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://j11a403.p.ssafy.io:8082/auth/realms/petsi/protocol/openid-connect/token",
                request,
                Map.class
        );

        return response.getBody().get("access_token").toString();
    }

    public MultiValueMap<String, String> createKeycloakRequestBody() {
        MultiValueMap<String, String> keycloakRequestBody = new LinkedMultiValueMap<>();
        keycloakRequestBody.add("client_id", "admin-cli");
        keycloakRequestBody.add("grant_type", "password");
        keycloakRequestBody.add("username", "petsi");
        keycloakRequestBody.add("password", "petsi0909!");
        return keycloakRequestBody;
    }
}
