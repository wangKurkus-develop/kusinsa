package com.kurkus.kusinsa.exception.brand;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.exception.CustomNotFoundException;
import com.kurkus.kusinsa.exception.RuntimeCommonException;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.springframework.http.HttpStatus;

public class BrandNotFoundException extends CustomNotFoundException {

    public BrandNotFoundException(){
        super(NOT_FOUND_BRAND);
    }
}
