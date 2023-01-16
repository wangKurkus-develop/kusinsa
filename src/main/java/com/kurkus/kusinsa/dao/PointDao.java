package com.kurkus.kusinsa.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@Repository
@RequiredArgsConstructor
public class RedisPointDao {

    private final RedisTemplate<String, String> redisPointTemplate;

}
