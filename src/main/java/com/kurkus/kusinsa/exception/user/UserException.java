package com.kurkus.kusinsa.exception.user;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class UserException extends RuntimeCommonException {

    public UserException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
