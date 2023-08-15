package com.homevision.demo.exception;

public class CustomClientException extends RuntimeException {
    public CustomClientException(String message) {
        //TODO: Build out more detailed error handling depending on the requirements
        super(message);
    }
}