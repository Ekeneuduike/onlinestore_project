package com.ekene.exception;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
    public CustomException(String massage,Throwable cause) {
        super(massage,cause);
    }
}
