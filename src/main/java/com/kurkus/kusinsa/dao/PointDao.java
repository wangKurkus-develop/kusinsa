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

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String PREFIX = "attendance_check:";

    /**
     * @return 기존에 존재하는경우 : 0, 처음저장인경우 : 1
     */
    public Long checkTodayLoginPoint(Long userId) {
        Long add = redisSetTemplate.opsForSet().add(PREFIX + LocalDate.now(), userId.toString());
        return add;
    }

}
