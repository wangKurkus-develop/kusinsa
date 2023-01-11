package com.kurkus.kusinsa.exception.user;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.NOT_FOUND_USER;

import com.kurkus.kusinsa.exception.CustomNotFoundException;
import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomNotFoundException {

    public UserNotFoundException(){
        super(NOT_FOUND_USER);
    }
}
