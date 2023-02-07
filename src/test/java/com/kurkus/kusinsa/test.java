package com.kurkus.kusinsa;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.PRODUCT_SIZE;

import com.kurkus.kusinsa.dto.request.notification.NotificationCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupRepository;
import com.kurkus.kusinsa.service.notification.NotificationService;
import com.kurkus.kusinsa.service.point.PointService;
import com.kurkus.kusinsa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
public class test {

    @Autowired
    PointService pointService;

//        productService.searchCondition(ProductSearchCondition.builder().build(),
//                PageRequest.of(5, PRODUCT_SIZE, Sort.by("createdAt").descending()));


    @Test
    public void 커버링_후_인덱스() throws Exception {
        // given
        // 전
        pointService.searchCondition(new PointSearchCondition(PointType.ALL, 18L), 1);
    }









}
