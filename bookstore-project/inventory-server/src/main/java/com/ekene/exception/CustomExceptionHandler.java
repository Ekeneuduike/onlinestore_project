package com.ekene.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<Object> responseEntity(CustomException e){
        ClassException classException = ClassException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .massage("something went wrong")
                .throwable(e)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();
        return new ResponseEntity<>(classException,HttpStatus.BAD_REQUEST);
    }

}
