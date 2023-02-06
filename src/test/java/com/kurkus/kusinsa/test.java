package com.kurkus.kusinsa;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.service.notification.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
