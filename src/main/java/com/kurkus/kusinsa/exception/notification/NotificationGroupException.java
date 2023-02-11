package com.kurkus.kusinsa.exception.notification;

import com.kurkus.kusinsa.exception.RuntimeCommonException;
import org.springframework.http.HttpStatus;

public class NotificationGroupException extends RuntimeCommonException {
    public NotificationGroupException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
