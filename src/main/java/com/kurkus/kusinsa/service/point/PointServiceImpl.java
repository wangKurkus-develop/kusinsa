package com.kurkus.kusinsa.service.point;

import static com.kurkus.kusinsa.enums.PointType.OBTAIN;
import static com.kurkus.kusinsa.enums.PointType.USED;
import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;
import static com.kurkus.kusinsa.utils.constants.PointMessages.*;
import static com.kurkus.kusinsa.utils.constants.PointMessages.ORDER_OBTAIN_CONTENT;

import com.kurkus.kusinsa.dao.PointDao;
import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.events.point.PointLoginSavedEvent;
import com.kurkus.kusinsa.events.point.PointOrderSavedEvent;
import com.kurkus.kusinsa.repository.point.PointRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionalProxy;


@RequiredArgsConstructor
@Service
@Slf4j
public class PointServiceImpl implements PointService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;
    private final PointDao pointDao;

    @Override
    @Transactional
    public void save(Long userId, PointCreateRequest request) {
        User user = userRepository.getById(userId);
        pointRepository.save(request.toPoint(user));
    }

    @Override
    @Transactional
    public void loginPointSave(PointLoginSavedEvent event) {
        if (pointDao.checkTodayLoginPoint(event.getUserId()) == 1) {
            PointCreateRequest request = new PointCreateRequest(LOGIN_POINT, LOGIN_POINT_CONTENT, OBTAIN);
            save(event.getUserId(), request);
        }
    }

    // 적립된 포인트, 내용(상품 결제),
    // 사용 포인트가 0이면 획득한것만 저장, 아니면은 사용한것 저장 + 획득한것
    @Transactional
    public void orderPointSave(PointOrderSavedEvent event) {
        if (event.getUsedPoint() != 0) {
            save(event.getUserId(),
                    new PointCreateRequest(event.getUsedPoint(),
                            ORDER_USED_CONTENT + event.getOrderId(), USED));
        }
        save(event.getUserId(), new PointCreateRequest(event.getObtainPoint(),
                ORDER_OBTAIN_CONTENT + event.getOrderId(), OBTAIN
        ));

    }

    @Override
    @Transactional(readOnly = true)
    public long findPointSum(Long userId) {
        return pointRepository.findPointSum(userId);
    }

    @Override
    public Page<PointResponse> searchCondition(PointSearchCondition condition, int page) {
        if (page < 0) {
            page = 0;
        }
        return pointRepository.searchPageCondition(condition, PageRequest.of(page, POINT_SIZE))
                .map(p -> PointResponse.from(p));
    }


}
