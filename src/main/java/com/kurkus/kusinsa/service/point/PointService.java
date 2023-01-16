package com.kurkus.kusinsa.service.point;


import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.enums.PointType;
import org.springframework.data.domain.Page;

/**
 * Point정책은 매번 달라질수있기때문에 인터페이스를 활용
 */
public interface PointService {

    void save(Long userId, PointCreateRequest request);

    Page<PointResponse> findAll(Long userId, PointType division, int page);

    long findPointSum(Long userId);


}
