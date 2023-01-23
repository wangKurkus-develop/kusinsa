package com.kurkus.kusinsa.dao;


import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LikesDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String PREFIX = "like:";

    /**
     * 좋아요한 개수를 count를 위해서 기존에 존재하는경우 : 0, 처음저장인경우 : 1
     */
    public void likeProduct(Long userId, Long productId) {
        Long add = redisSetTemplate.opsForSet().add(PREFIX + productId, userId.toString());
        if (add == 0) {
            redisSetTemplate.opsForSet().remove(PREFIX + productId, userId.toString());
        }
    }

    public Long getLikes(Long productId) {
        return redisSetTemplate.opsForSet().size(PREFIX + productId);
    }


}
