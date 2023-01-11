package com.kurkus.kusinsa.exception.handler;

import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.controller.BrandController;
import com.kurkus.kusinsa.exception.brand.BrandException;
import com.kurkus.kusinsa.exception.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {BrandController.class})
@Slf4j
public class BrandExceptionHandler {

    @ExceptionHandler(BrandException.class)
    public ResponseEntity<String> handleBrandException(BrandException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }
}
