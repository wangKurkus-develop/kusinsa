package com.kurkus.kusinsa.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CountDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String ORDER_PREFIX = "order_product"; // 가장많이 주문된 상품
    private final String CLICK_PREFIX = "click_product"; // 가장많이 조회된 상품

    public void orderCount(Long productId) {
        redisSetTemplate.opsForZSet().incrementScore(ORDER_PREFIX, productId.toString(), 1);
    }

    public void findCount(Long productId) {
        redisSetTemplate.opsForZSet().incrementScore(CLICK_PREFIX, productId.toString(), 1);
    }

}
