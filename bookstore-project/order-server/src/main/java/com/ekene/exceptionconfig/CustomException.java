package com.ekene.exceptionconfig;

public class CustomException extends IllegalStateException{
    public CustomException(String massage){
        super(massage);
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
