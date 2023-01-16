package com.kurkus.kusinsa.events.point;

import javax.transaction.Transactional;

import static com.kurkus.kusinsa.enums.PointType.*;

import com.kurkus.kusinsa.dao.PointDao;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.service.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * login에서하는것은 SRP를 준수하지못함 포인트지급과 관련된것이기때문에 포인트지급에서해야한다
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void pointSave(PointLoginSavedEvent event) {
        if(pointDao.checkTodayLoginPoint(event.getUserId()) == 1){
            PointCreateRequest request = new PointCreateRequest(event.getScore(), event.getContent(), OBTAIN);
            pointService.save(event.getUserId(), request);
            log.info("{} 포인트 저장 완료", event.getUserId());
        }
    }
}
