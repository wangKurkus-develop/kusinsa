package com.kurkus.kusinsa.dao;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LikesDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String PRODUCT_PREFIX = "like_product:";
    private final String USER_PREFIX = "like_user:";
    private final String PRODUCT_LIKES = "product_likes";

    public void likeProduct(Long userId, Long productId) {
        redisSetTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                try {
                    redisSetTemplate.multi();
                    redisSetTemplate.opsForSet().add(PRODUCT_PREFIX + productId, userId.toString());
                    redisSetTemplate.opsForSet().add(USER_PREFIX + userId, productId.toString());
                    redisSetTemplate.opsForZSet().add(PRODUCT_LIKES, productId.toString(), System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                    redisSetTemplate.discard();
                }
                return redisSetTemplate.exec();
            }
        });
    }

    public void disLikeProduct(Long userId, Long productId) {
        redisSetTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                try {
                    redisSetTemplate.multi();
                    redisSetTemplate.opsForSet().remove(PRODUCT_PREFIX + productId, userId.toString());
                    redisSetTemplate.opsForSet().remove(USER_PREFIX + userId, productId.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return redisSetTemplate.exec();
            }
        });
    }

    public List<Long> getLikes(){
        Set<Object> set = redisSetTemplate.opsForZSet().range(PRODUCT_LIKES, 0, 9);
        redisSetTemplate.opsForZSet().removeRange(PRODUCT_LIKES, 0, 9);
        return set.stream().map(o -> new Long(o.toString())).collect(Collectors.toList());
    }

    public List<Long> getLikeProducts(Long userId){
        Set<Object> likeProductId = redisSetTemplate.opsForSet().members(USER_PREFIX + userId);
        return likeProductId.stream().map(o -> new Long(o.toString())).collect(Collectors.toList());
    }

    public Long getLikeQuantity(String productId){
        return redisSetTemplate.opsForSet().size(PRODUCT_PREFIX+productId);
    }



}
