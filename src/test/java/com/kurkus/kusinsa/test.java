package com.kurkus.kusinsa;


import java.util.*;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.documents.ClickRank;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.repository.mongo.ClickRankRepository;
import com.kurkus.kusinsa.service.RankService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    RankDao rankDao;

    @Autowired
    RedisTemplate<String, Object> redisSetTemplate;

    @Autowired
    RankService rankService;

    @Autowired
    ClickRankRepository test;

    @Autowired
    LikesDao likesDao;

    @Autowired
    ProductRepository productRepository;
    
    @Test
    public void aaa(){
        likesDao.getLikes();
    }





}
