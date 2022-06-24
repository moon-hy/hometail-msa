package com.hometail.authservice.controller;

import com.hometail.authservice.domain.Account;
import com.hometail.authservice.dto.LoginRequestDto;
import com.hometail.authservice.dto.RestResponse;
import com.hometail.authservice.dto.SignupRequestDto;
import com.hometail.authservice.service.AuthService;
import com.hometail.authservice.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/accounts")
    public ResponseEntity<RestResponse> signup(@Valid SignupRequestDto dto) {

        return new ResponseEntity<>(RestResponse.builder()
                .status(200)
                .data(authService.addAccount(dto.toEntity(passwordEncoder)))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@Valid LoginRequestDto dto) {

        return new ResponseEntity<>(RestResponse.builder()
                .status(200)
                .data(authService.login(dto.toEntity()))
                .build(), HttpStatus.OK);
    }
}
