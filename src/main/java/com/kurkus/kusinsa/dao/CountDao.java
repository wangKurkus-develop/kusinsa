package com.kurkus.kusinsa.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String 

    public void orderCount(Long userId, Long productId){

    }
}
