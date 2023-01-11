package com.kurkus.kusinsa.exception.handler;

import javax.servlet.http.HttpServletRequest;

import com.kurkus.kusinsa.exception.CustomNotFoundException;
import com.kurkus.kusinsa.exception.SessionLoginException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.NonUniqueResultException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SessionLoginException.class)
    public ResponseEntity<String> handleSessionLoginException(SessionLoginException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    // valid 예외 처리하기
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.info("Http Method : {}, URI : {}, msg : {}, status : {}",
                request.getMethod(), request.getRequestURI(), msg, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> handleCustomNotFoundException(CustomNotFoundException ex, HttpServletRequest request) {
        log.info("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), ex.getHttpStatus());
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    // 에러인경우 Optional.ifPresent를 사용했을때 중복으로 들어가지는경우가있었다 이런경우 find를 할때 이미들어가있는데
    // 중복체크를안하고 이루어지기때문에 오류가생겨서 ifPresent를 사용하지않게되었다.
    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<String> handleNonUniqueResultException(NonUniqueResultException ex, HttpServletRequest request) {
        log.error("Http Method : {},  URI : {}, msg : {}, status : {}", request.getMethod(), request.getRequestURI(),
                ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }



}
