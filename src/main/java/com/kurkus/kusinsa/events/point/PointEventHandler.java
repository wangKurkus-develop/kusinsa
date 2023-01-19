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
    private final PointDao pointDao;

    /**
     * 로그인 포인트 중복체크하기
     */
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void loginPointSave(PointLoginSavedEvent event) {
        log.info("PointLoginSavedEvent Thread ID : {}", Thread.currentThread().getId());
        if(pointDao.checkTodayLoginPoint(event.getUserId()) == 1){
            PointCreateRequest request = new PointCreateRequest(LOGIN_POINT, LOGIN_POINT_CONTENT, OBTAIN);
            pointService.save(event.getUserId(), request);
            log.info("{} 포인트 저장 완료", event.getUserId());
        }
    }

    // 적립된 포인트, 내용(상품 결제),
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void orderPointSave(PointOrderSavedEvent event) {
        if(event.getUsedPoint() != 0){
            pointService.save(event.getUserId(),
                    new PointCreateRequest(event.getUsedPoint(),
                            ORDER_USED_CONTENT+event.getOrderId(), USED));
            log.info(ORDER_USED_CONTENT+event.getOrderId());
        }
        pointService.save(event.getUserId(), new PointCreateRequest(event.getObtainPoint(),
                ORDER_OBTAIN_CONTENT+event.getOrderId(), OBTAIN
                ));
        log.info(ORDER_OBTAIN_CONTENT+event.getOrderId());
    }

}
