package com.kurkus.kusinsa.exception;

import org.springframework.http.HttpStatus;

public class SessionLoginException extends RuntimeException{

    private HttpStatus httpStatus;
    public SessionLoginException(String msg, HttpStatus httpStatus){
        super(msg);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
