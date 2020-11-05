package com.beerhouse.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiErrorMessage {
    private Long timeStamp;
    private HttpStatus status;
    private int error;
    private String message;
}
