package com.kurkus.kusinsa.exception.category;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.exception.CustomNotFoundException;
import com.kurkus.kusinsa.exception.RuntimeCommonException;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends CustomNotFoundException {

    public CategoryNotFoundException(){
        super(NOT_FOUND_CATEGORY);
    }
}
