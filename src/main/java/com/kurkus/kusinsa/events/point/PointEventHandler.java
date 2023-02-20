package com.kurkus.kusinsa.events.point;

import javax.transaction.Transactional;

import static com.kurkus.kusinsa.enums.PointType.*;
import static com.kurkus.kusinsa.utils.constants.PointMessages.*;

import com.kurkus.kusinsa.dao.PointDao;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.service.point.PointService;
import com.kurkus.kusinsa.utils.constants.PointMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointEventHandler {

    private final PointService pointService;
    
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void save(PointEvent event){
        pointService.save(event.getUserId(),
                new PointCreateRequest(event.getPoint(), event.getContent(), event.getType()));
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void loginPointSave(PointLoginSavedEvent event) {
        pointService.loginPointSave(event);
    }


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void orderPointSave(PointOrderSavedEvent event) {
        pointService.orderPointSave(event);
    }
}
