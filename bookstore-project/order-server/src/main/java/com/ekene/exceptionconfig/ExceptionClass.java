package com.ekene.exceptionconfig;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;



public record ExceptionClass(String massage, ZonedDateTime time, HttpStatus httpStatus) {
}
