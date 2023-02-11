package com.kurkus.kusinsa;


import com.kurkus.kusinsa.dto.request.product.ProductSearchCondition;
import com.kurkus.kusinsa.entity.documents.NotificationMessage;
import com.kurkus.kusinsa.enums.notification.NotificationStatus;
import com.kurkus.kusinsa.repository.mongo.NotificationMessageRepository;
import com.kurkus.kusinsa.repository.notification.NotificationGroupUserRepository;
import com.kurkus.kusinsa.service.notification.NotificationService;
import com.kurkus.kusinsa.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
public class test {

    @Autowired
    NotificationGroupUserRepository test;

    @Autowired
    NotificationService notificationService;


    @Test
    @Transactional
    public void V4() {
        notificationService.notifyDelivery(1L);
    }

    @Test
    public void asd() throws Exception {
        // given
        test.findGroupUserEmail(1L);
        // when

        // then
    }


}
