package com.kurkus.kusinsa.exception;

import org.springframework.http.HttpStatus;

public class CustomNotFoundException extends RuntimeCommonException{

    public CustomNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
