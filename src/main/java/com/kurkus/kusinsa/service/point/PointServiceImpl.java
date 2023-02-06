package com.kurkus.kusinsa.service.point;

import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.repository.point.PointRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
public class PointServiceImpl implements PointService{

    private final UserRepository userRepository;
    private final PointRepository pointRepository;


    @Override
    @Transactional
    public void save(Long userId, PointCreateRequest request) {
        User user = userRepository.getById(userId);
        pointRepository.save(request.toPoint(user));
    }


    @Override
    @Transactional(readOnly = true)
    public long findPointSum(Long userId) {
        return pointRepository.findPointSum(userId);
    }

    @Override
    public Page<PointResponse> searchCondition(PointSearchCondition condition, int page) {
        return pointRepository.searchPageCondition(condition, PageRequest.of(page, POINT_SIZE))
                .map(p -> PointResponse.from(p));
    }


}
