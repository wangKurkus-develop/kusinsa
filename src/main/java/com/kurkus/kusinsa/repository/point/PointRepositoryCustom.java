package com.kurkus.kusinsa.repository.point;


import java.util.List;

import com.kurkus.kusinsa.dto.request.point.PointSearchCondition;
import com.kurkus.kusinsa.entity.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface PointRepositoryCustom {

    Page<Point> searchPageCondition(PointSearchCondition condition, Pageable pageable);

}
