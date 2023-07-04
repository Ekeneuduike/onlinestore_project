package com.ekene.exceptionconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ExceptionClass> responseEntity(CustomException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionClass exceptionClass = new ExceptionClass(
                "opps something went wrong pls try again later",
                ZonedDateTime.now(ZoneId.of("Z")),
                badRequest
        );
        return new ResponseEntity<>(exceptionClass,badRequest);
    }
   
}
