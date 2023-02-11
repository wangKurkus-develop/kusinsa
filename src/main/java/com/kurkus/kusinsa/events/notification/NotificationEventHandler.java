package com.kurkus.kusinsa.events.notification;


import javax.transaction.Transactional;

import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.entity.order.OrderHistory;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.service.notification.NotificationService;
import com.kurkus.kusinsa.service.notification.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventHandler {

    private final NotificationService notificationService;


    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void notifyDeliveryComplete(DeliveryStatusEvent event) {
        notificationService.notifyDelivery(event.getOrderHistoryId());
    }

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void notifyGroup(GroupEvent event) {
        notificationService.notifyGroup(event.getProductId(), event.getProductName());
    }
}
