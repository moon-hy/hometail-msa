package com.hometail.authservice.controller;

import com.hometail.authservice.dto.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    private String VALIDATION_ERROR = "유효성 검증에 실패했습니다.";

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<RestResponse> validationError(MethodArgumentNotValidException exception) {
        RestResponse restResponse = RestResponse.builder()
                .code("400")
                .message(exception.getBindingResult().getAllErrors()
                        .stream()
                        .map(o -> o.getDefaultMessage())
                        .collect(Collectors.joining("\n")))
                .build();
        return new ResponseEntity<RestResponse>(restResponse, HttpStatus.BAD_REQUEST);
    }
}
