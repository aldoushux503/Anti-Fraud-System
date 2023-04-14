package com.example.antifraudsystem.controller;


import jakarta.validation.ValidationException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class ValidateParametersController {

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

}
