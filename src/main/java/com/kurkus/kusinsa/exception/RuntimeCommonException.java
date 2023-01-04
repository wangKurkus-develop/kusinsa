package com.kurkus.kusinsa.exception;

import org.springframework.http.HttpStatus;

public class RuntimeCommonException extends RuntimeException {

    private HttpStatus httpStatus;

    public RuntimeCommonException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
