package com.kurkus.kusinsa.exception.brand;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class BrandException extends RuntimeCommonException {


    public BrandException(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
