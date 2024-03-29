package com.kurkus.kusinsa.dao;


import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class NotificationDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String KEY = "notification";


    public boolean createUniqueKey(Long productId){
        // 처음인경우 true, 이미있으면 false
        String uniqueKey = productId.toString() + LocalDateTime.now();
        return redisSetTemplate.opsForHash().putIfAbsent(KEY, productId.toString(), uniqueKey);
    }

    public String getUniqueKey(Long productId){
        return redisSetTemplate.opsForHash().get(KEY, productId.toString()).toString();
    }

    public void deleteUniqueKey(Long productId){
        redisSetTemplate.opsForHash().delete(KEY, productId.toString());
    }
}
