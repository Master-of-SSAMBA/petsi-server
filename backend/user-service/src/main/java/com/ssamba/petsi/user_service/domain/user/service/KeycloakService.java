package com.ssamba.petsi.user_service.domain.user.service;

import com.ssamba.petsi.user_service.domain.user.dto.request.RegisterKeycloakUserRequestDto;
import com.ssamba.petsi.user_service.domain.user.repository.UserRepository;
import com.ssamba.petsi.user_service.global.exception.BusinessLogicException;
import com.ssamba.petsi.user_service.global.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm;
    private final UserRepository userRepository;

    public KeycloakService(Keycloak keycloak, @Value("${keycloak.realm}") String realm, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.userRepository = userRepository;
    }

    public void registerUserInKeycloak(RegisterKeycloakUserRequestDto request) {
        UserRepresentation userRepresentation = createUserRepresentation(request);

        Response response = keycloak.realm(realm).users().create(userRepresentation);
        if (response.getStatus() != 201) {
            throw new BusinessLogicException(ExceptionCode.KEYCLOAK_REGISTER_ERROR);
        }
    }

    private UserRepresentation createUserRepresentation(RegisterKeycloakUserRequestDto request) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(request.getEmail());
        userRepresentation.setEmail(request.getEmail());
        userRepresentation.setEnabled(true);
        userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(request.getPassword())));
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("userId", Collections.singletonList(String.valueOf(request.getUserId())));
        attributes.put("userKey", Collections.singletonList(request.getUserKey()));
        userRepresentation.setAttributes(attributes);
        return userRepresentation;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false); // 임시 비밀번호 아님
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void deleteUnverifiedUsers() {
        for (UserRepresentation user : getUnverifiedUsers()) {
            if (isUnverifiedForLongTime(user)) {
                keycloak.realm(realm).users().delete(user.getId());
                userRepository.deleteByEmail(user.getEmail());
            }
        }
    }

    private List<UserRepresentation> getUnverifiedUsers() {
        List<UserRepresentation> users = keycloak.realm(realm).users().list();
        return users.stream()
                .filter(user -> Boolean.FALSE.equals(user.isEmailVerified()))
                .toList();
    }

    private boolean isUnverifiedForLongTime(UserRepresentation user) {
        // 인증을 안한지 2일 초과한 경우
        return Duration.between(
                Instant.ofEpochMilli(user.getCreatedTimestamp()),
                Instant.now()
        ).toDays() > 2;
    }
}
