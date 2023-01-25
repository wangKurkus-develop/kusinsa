package com.kurkus.kusinsa;


import java.util.*;

import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class test {

    @Autowired
    RankDao rankDao;

    @Autowired
    RedisTemplate<String, Object> redisSetTemplate;

    @Autowired
    RankService rankService;
    
    @Test
    public void before채우기(){
        // 이전에 20등까지있는거임 // 구 버전 순위
        List<OrderRankResponse> orderRankResponses = rankService.orderRankTop10();
        for(OrderRankResponse o : orderRankResponses){
            System.out.println("o.getName() = " + o.getName());
            System.out.println("o.getId() = " + o.getId());
            System.out.println("o.getRank() = " + o.getRank());
        }


    }





}
