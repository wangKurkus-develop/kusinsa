package com.kurkus.kusinsa.exception.order;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class OrderException extends RuntimeCommonException {

    public OrderException(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
