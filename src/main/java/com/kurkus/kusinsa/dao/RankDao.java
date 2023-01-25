package com.kurkus.kusinsa.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RankDao {

    private final RedisTemplate<String, Object> redisSetTemplate;
    private final String ORDER_KEY = "order_product"; // 가장많이 주문된 상품
    private final String CLICK_KEY = "click_product"; // 가장많이 조회된 상품
    private final String BEFORE_CLICK_KEY = "before_click_product"; // 이전 랭킹 hash

    private final ProductRepository productRepository;

    public void orderCount(Long productId) {
        redisSetTemplate.opsForZSet().incrementScore(ORDER_KEY, productId.toString(), 1);
    }

    public void clickCount(Long productId) {
        redisSetTemplate.opsForZSet().incrementScore(CLICK_KEY, productId.toString(), 1);
    }

    // 애는 그냥 10개를 가지고오면됨
    public List<Long> orderRankTop10(){
        Set<Object> set = redisSetTemplate.opsForZSet().reverseRangeByScore(ORDER_KEY, 5, 1000, 0, 10);
        return set.stream().map(o -> new Long(o.toString())).collect(Collectors.toList());
    }

//    // top10 리스트
    public List<Long> clickRankTop10(){
        Set<Object> set = redisSetTemplate.opsForZSet().reverseRangeByScore(CLICK_KEY, 5, 3000, 0, 10);
        return set.stream().map(o -> new Long(o.toString())).collect(Collectors.toList());
    }






}
