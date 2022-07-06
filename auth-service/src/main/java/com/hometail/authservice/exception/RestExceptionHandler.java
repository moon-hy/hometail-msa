package com.hometail.authservice.exception;

import com.hometail.authservice.dto.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> bindError(BindException exception) {

        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .exception(exception).build());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> accountServiceError(InvalidRequestException exception) {

        return ResponseEntity.badRequest()
                .body(ErrorResponseDto.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .exception(exception).build());
    }
}
