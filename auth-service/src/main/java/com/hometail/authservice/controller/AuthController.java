package com.hometail.authservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometail.authservice.domain.Account;
import com.hometail.authservice.dto.RestResponse;
import com.hometail.authservice.dto.SignupDto;
import com.hometail.authservice.service.AuthService;
import com.hometail.authservice.utils.CustomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/accounts")
    public ResponseEntity<RestResponse> signup(@RequestBody SignupDto dto) throws MethodArgumentNotValidException {

        CustomValidator validator = new CustomValidator();
        validator.validSignupDto(dto);
        Account account = authService.addAccount(dto.toEntity(passwordEncoder));
        return new ResponseEntity<RestResponse>(RestResponse.builder()
                .code("200")
                .data(account)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity login() {

        return ResponseEntity.ok().build();
    }
}
