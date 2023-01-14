package com.kurkus.kusinsa.service.point;

import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.repository.PointRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import com.kurkus.kusinsa.utils.constants.PageSizeConstants;
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


    /**
     * event listener 사용하자
     * 로그인시에만 주는거 따로만들어야하나??
     */
    @Override
    @Transactional
    public void save(String userId, PointCreateRequest request) {
        User user = userRepository.getByEmail(userId);
        pointRepository.save(request.toPoint(user));
    }

    /**
     * division에 default값 냅두고 동적쿼리로 if문으로 처리가능할듯 나중에 QueryDSL로 리펙토링하기
     */
    @Transactional(readOnly = true)
    @Override
    public Page<PointResponse> findAll(String userId, PointType division, int page) {
        if(division == PointType.ALL){
            return pointRepository.findAll(userId, PageRequest.of(page, POINT_SIZE)).
                    map(p -> PointResponse.from(p));
        } else{
            return pointRepository.findAllByDivision(userId, division, PageRequest.of(page, POINT_SIZE)).
                    map(p -> PointResponse.from(p));
        }
    }



}
