package com.ssamba.petsi.user_service.global.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpEntityUtil {
    public static <T> HttpEntity<T> createHttpEntity(MediaType mediaType, T body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(body, headers);
    }

    public static <T> HttpEntity<T> createHttpEntity(MediaType mediaType, T body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new HttpEntity<>(body, headers);
    }
}
