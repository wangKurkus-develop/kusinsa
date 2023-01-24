package com.kurkus.kusinsa;


import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kurkus.kusinsa.dao.CountDao;
import com.kurkus.kusinsa.dao.RecentDao;
import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.service.ProductService;
import com.kurkus.kusinsa.service.order.OrderHistoryService;
import oracle.sql.TIMESTAMP;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    CountDao countDao;

    @Autowired
    RedisTemplate<String, Object> redisSetTemplate;

//    @BeforeEach
//    void setUp(){
//        redisSetTemplate.opsForZSet().add("click_product",2,0);
//    }

    @Test
    public void 동시에_100명이_클릭() throws Exception {
        // given
        int threadCount = 1000;

        ExecutorService executorService = Executors.newFixedThreadPool(40);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    countDao.findCount(2L);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }




}
