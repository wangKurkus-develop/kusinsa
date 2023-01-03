package com.kurkus.kusinsa.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeCommonException {

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
