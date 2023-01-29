package com.kurkus.kusinsa.aop;

import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.service.user.SessionLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CountAspect {

    private final RecentDao recentDao;
    private final SessionLoginService sessionLoginService;
    private final RankDao rankDao;

    // 주문완료시에만 주문한 상품 count
    @AfterReturning("execution(* com.kurkus.kusinsa.service.order.OrderHistoryService.save(..))")
    public void orderCount(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        OrderProductRequest request = (OrderProductRequest) args[1];
        System.out.println("request.getProductId() = " + request.getProductId());
        rankDao.orderCount(request.getProductId());
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
        rankDao.clickCount(productId);
    }

    // 검색한거 (검색기능생성시에 만들기)

}
