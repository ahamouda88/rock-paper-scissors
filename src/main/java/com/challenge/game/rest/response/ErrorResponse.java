package com.challenge.game.rest.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final String errorMessage;
}
