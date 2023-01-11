package com.kurkus.kusinsa.exception.product;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class ProductException extends RuntimeCommonException {

    public ProductException(String msg, HttpStatus httpStatus){
        super(msg, httpStatus);
    }
}
