package com.hometail.userservice.service;

import com.hometail.userservice.dto.ProfileResponseDto;
import com.hometail.userservice.dto.response.ProfileRestResponseDto;
import com.hometail.userservice.dto.response.RestResponseDto;
import com.hometail.userservice.util.RequestProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final RequestProvider requestProvider;
    private final RestTemplate restTemplate;

    public RestResponseDto<?> getAccountInfo(String authorization) {

        RequestEntity<?> request = requestProvider.createAuthServiceRequest(HttpMethod.GET, "/account", authorization);
        ResponseEntity<ProfileRestResponseDto> response = restTemplate.exchange(request, ProfileRestResponseDto.class);

        return response.getBody();
    }
}
