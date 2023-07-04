package com.ekene.exception;

public class CustomException extends RuntimeException {
    public CustomException(String massage){
        super(massage);
    }
    public  CustomException(String massage, Throwable throwable){
        super(massage,throwable);
    }
}
