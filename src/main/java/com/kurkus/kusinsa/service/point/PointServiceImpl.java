package com.kurkus.kusinsa.service.point;

import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.*;

import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.PointType;
import com.kurkus.kusinsa.repository.PointRepository;
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

    /**
     * division에 default값 냅두고 동적쿼리로 if문으로 처리가능할듯 나중에 QueryDSL로 리펙토링하기
     */
    @Transactional(readOnly = true)
    @Override
    public Page<PointResponse> findAll(Long userId, PointType division, int page) {
        if(page<0){
            page = 0;
        }
        if(division == PointType.ALL){
            return pointRepository.findAll(userId, PageRequest.of(page, POINT_SIZE)).
                    map(p -> PointResponse.from(p));
        } else{
            return pointRepository.findAllByDivision(userId, division, PageRequest.of(page, POINT_SIZE)).
                    map(p -> PointResponse.from(p));
        }
    }


    /**
     * @param userId
     * @return 유저가 가진 포인트 합 주기
     * v2 : review삭제시 deleted로 바꿔주어야한다 그러기위해선 외래키나 컬럼으로로 review id를 가져야할지도
     */
    @Override
    @Transactional(readOnly = true)
    public long findPointSum(Long userId) {
        return pointRepository.findPointSum(userId);
    }






}
