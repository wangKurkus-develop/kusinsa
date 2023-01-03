package com.kurkus.kusinsa.exception;

import org.springframework.http.HttpStatus;

public class SessionLoginException extends RuntimeCommonException {


    public SessionLoginException(String msg, HttpStatus httpStatus){
        super(msg, httpStatus);
    }
}
