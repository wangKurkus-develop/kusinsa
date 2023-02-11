package com.kurkus.kusinsa.exception.handler;


import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.controller.user.PointController;
import com.kurkus.kusinsa.exception.point.PointException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {PointController.class})
@Slf4j
public class PointExceptionHandler {

    @ExceptionHandler(PointException.class)
    public ResponseEntity<String> handlePointException(PointException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
