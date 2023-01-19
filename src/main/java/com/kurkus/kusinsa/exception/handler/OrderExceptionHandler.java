package com.kurkus.kusinsa.exception.handler;

import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.controller.OrderController;
import com.kurkus.kusinsa.exception.order.OrderException;
import com.kurkus.kusinsa.exception.point.PointException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {OrderController.class})
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<String> orderException(OrderException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
