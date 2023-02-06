package com.kurkus.kusinsa.service.point;


import com.kurkus.kusinsa.dto.request.point.PointCreateRequest;
import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.dto.response.point.PointResponse;
import com.kurkus.kusinsa.enums.PointType;
import org.springframework.data.domain.Page;

public interface PointService {

    void save(Long userId, PointCreateRequest request);

    long findPointSum(Long userId);

    Page<PointResponse> searchCondition(PointSearchCondition condition, int page);


}
