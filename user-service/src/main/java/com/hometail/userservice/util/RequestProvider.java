package com.hometail.userservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RequestProvider {

    private static final String GATEWAY_URI = "http://localhost:8000";
    private static final String AUTH_SERVICE = "/auth-service";

    public RequestEntity<?> createAuthServiceRequest(HttpMethod method, String uri, String authorization) {

        return RequestEntity
                .method(method, GATEWAY_URI.concat(AUTH_SERVICE).concat(uri))
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .build();
    }
}
