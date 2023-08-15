package com.homevision.demo.exception;

public class CustomServerException extends RuntimeException {
    public CustomServerException(String message) {
        //TODO: Build out more detailed error handling depending on the requirements
        super(message);
    }
}