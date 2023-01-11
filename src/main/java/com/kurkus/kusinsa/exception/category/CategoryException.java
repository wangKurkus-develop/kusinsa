package com.kurkus.kusinsa.exception.category;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class CategoryException extends RuntimeCommonException {

    public CategoryException(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
}
