package com.kurkus.kusinsa;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.service.order.OrderHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class test {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderHistoryService orderHistoryService;

    @Autowired
    OrderHistoryRepository historyRepository;

    @Test
    public void joinfecth() throws Exception {
        // given


        // then
    }




    @Test
    @Transactional
    public void test() throws Exception {
        // given
        for (long i = 11; i <= 20; i++) {
            System.out.println(i + ": 시작 ");
            decrease(i);
            decrease(11L);
        }
    }

    @Test
    @Transactional
    public void decrease(Long id) throws Exception {
        // given 1번과 5번
        productRepository.findByIdWithPessimisticLock(id);
        Thread.sleep(1000);
    }
}
