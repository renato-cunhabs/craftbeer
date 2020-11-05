package com.beerhouse.controller;

import com.beerhouse.exception.ResourceNotFoundException;
import com.beerhouse.model.ApiErrorMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> emptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return createResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, formatMessageMethodArgumentNotValidException(exception));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException exception){
        return createResponseEntity(NOT_FOUND, exception.getMessage());
    }

    private String formatMessageMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream()
                .map(FieldError::getField)
                .collect(joining(","));

        String fieldsMessages = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(joining(","));
        return String.format("%s %s", fields, fieldsMessages);
    }

    private ResponseEntity<Object> createResponseEntity(HttpStatus status, String message) {
        ApiErrorMessage errorMessage = ApiErrorMessage.builder()
                .timeStamp(new Date().getTime())
                .status(status)
                .error(status.value())
                .message(message)
                .build();
        return new ResponseEntity<>(errorMessage, status);
    }

}
