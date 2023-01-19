package com.kurkus.kusinsa.events.order;


import javax.transaction.Transactional;

import java.util.List;

import static com.kurkus.kusinsa.enums.PointType.OBTAIN;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.events.point.PointLoginSavedEvent;
import com.kurkus.kusinsa.service.order.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandler {

    private final OrderHistoryService orderHistoryService;


    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void historySave(OrderHistorySavedEvent event) {
        List<OrderProductRequest> list = event.getOrderProductRequestList();
        for(int i=0; i<list.size(); i++){
            OrderProductRequest request = list.get(i);
            orderHistoryService.save(event.getOrder(), request);
        }
    }
}
