package com.kurkus.kusinsa.exception.handler;


import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.controller.BrandController;
import com.kurkus.kusinsa.exception.category.CategoryException;
import com.kurkus.kusinsa.exception.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {BrandController.class})
@Slf4j
public class CategoryExceptionHandler {

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<String> handleCategoryException(CategoryException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

}
