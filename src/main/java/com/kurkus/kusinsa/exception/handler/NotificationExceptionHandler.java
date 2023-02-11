package com.kurkus.kusinsa.exception.handler;


import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.exception.notification.NotificationGroupException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class NotificationExceptionHandler {

    @ExceptionHandler(NotificationGroupException.class)
    public ResponseEntity<String> handleNotificationException(NotificationGroupException ex, HttpServletRequest request){
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

}
