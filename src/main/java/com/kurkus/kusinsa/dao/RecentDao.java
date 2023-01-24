package com.kurkus.kusinsa.dao;


import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RecentDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String PREFIX = "recent_click:";

    public void recentSave(Long userId, String productId){
        redisSetTemplate.expire(PREFIX+userId, 6, TimeUnit.HOURS);
        redisSetTemplate.opsForZSet().add(PREFIX+userId, productId,System.currentTimeMillis());
    }

    public Set<Object> findAllRecent(Long userId){
        return redisSetTemplate.opsForSet().members(PREFIX + userId);
    }
}
