package com.hometail.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hometail.userservice.dto.response.RestResponseDto;

@RequestMapping("/user-service")
@RestController
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("X-Authorization-Id") String accountId) {

        return ResponseEntity.ok()
                .body(RestResponseDto.builder()
                        .httpStatus(HttpStatus.OK)
                        .data(accountId)
                        .build());
    }
}
