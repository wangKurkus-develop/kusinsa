package com.kurkus.kusinsa;


import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kurkus.kusinsa.dao.LikesDao;
import com.kurkus.kusinsa.dao.RankDao;
import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.dto.response.rank.OrderRankResponse;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.documents.ClickRank;
import com.kurkus.kusinsa.enums.notification.NotificationGroupStatus;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.mongo.ClickRankRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.service.RankService;
import com.kurkus.kusinsa.service.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationGroupRepository groupRepository;



    @Test
    public void 동시성() throws Exception {
        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 18; i <= 20; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    System.out.println("finalI : "+finalI);
                    notificationService.save(new Long(finalI), new NotificationCreateRequest(18L));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }





}
