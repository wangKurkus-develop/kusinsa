package com.kurkus.kusinsa.events.point;

import javax.transaction.Transactional;

import static com.kurkus.kusinsa.enums.PointType.*;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.service.point.PointService;
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
    public void pointSave(PointSavedEvent event) {
        PointCreateRequest request = new PointCreateRequest(event.getScore(), event.getContent(), OBTAIN);
        pointService.save(event.getUserId(), request);
        log.info("{} 포인트 저장 완료", event.getUserId());
    }
}
