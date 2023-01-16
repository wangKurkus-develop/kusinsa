package com.kurkus.kusinsa.exception.point;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class PointException extends RuntimeCommonException {

    public PointException(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
