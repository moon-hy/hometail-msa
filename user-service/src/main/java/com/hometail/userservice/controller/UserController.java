package com.hometail.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hometail.userservice.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user-service")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authorization) {

        return ResponseEntity.ok()
                .body(profileService.getAccountInfo(authorization));
    }
}
