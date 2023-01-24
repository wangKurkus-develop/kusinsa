package com.kurkus.kusinsa.aop;

import javax.servlet.http.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kurkus.kusinsa.dao.CountDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.service.SessionLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CountAspect {

    private final RecentDao recentDao;
    private final SessionLoginService sessionLoginService;
    private final CountDao countDao;

    // 주문완료시에만 주문한 상품 count
    @AfterReturning("execution(* com.kurkus.kusinsa.service.order.OrderHistoryService.save(..))")
    public void orderCount(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        OrderProductRequest request = (OrderProductRequest) args[1];
        System.out.println("request.getProductId() = " + request.getProductId());
        countDao.orderCount(request.getProductId());
    }

    // 조회 많이한 상품 count + 유저가 최신에본상품
    @AfterReturning("execution(* com.kurkus.kusinsa.service.ProductService.findById(..))")
    public void findCount(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long productId = (long) args[0];
        Long userId = sessionLoginService.getSessionUserId();
        if(userId != null){
            recentDao.recentSave(userId, productId);
        }
        countDao.findCount(productId);
    }

    // 검색한거 (검색기능생성시에 만들기)

}
