package com.challenge.game.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.challenge.game.exception.InvalidInputException;
import com.challenge.game.rest.response.ErrorResponse;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = { InvalidInputException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorResponse(status, exception.getMessage()), status);
    }
}
