package com.kurkus.kusinsa.dao;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PointDao {

    private final RedisTemplate<String, Object> redisPointTemplate;
    private final String PREFIX = "attendance_check:";

    public Long checkTodayLoginPoint(Long userId){
        Long add = redisPointTemplate.opsForSet().add(PREFIX+LocalDate.now(),  userId.toString());
        return add;
    }

}
