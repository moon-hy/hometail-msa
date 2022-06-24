package com.hometail.authservice.exception;

import com.hometail.authservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ValidationException;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validationError(BindException exception, WebRequest request) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT_VALUE)
                .details(exception.getBindingResult().getFieldErrors().stream().map(o -> o.getField()).collect(Collectors.joining(",")))
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> methodNotAllowedError(HttpRequestMethodNotSupportedException exception, WebRequest request) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(ErrorCode.METHOD_NOT_ALLOWED)
                .details(request.getDescription(false))
                .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> noHandlerFoundError(NoHandlerFoundException exception, WebRequest request) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(ErrorCode.NOT_FOUND)
                .details(request.getDescription(false))
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationError(ValidationException exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT_VALUE)
                .details(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> remainError(Exception exception) {

        return new ResponseEntity<>(ErrorResponse.builder()
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
