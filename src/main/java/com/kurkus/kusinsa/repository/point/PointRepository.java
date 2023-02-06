package com.kurkus.kusinsa.repository.point;

import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.PointType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point, Long>, PointRepositoryCustom {

    @Query(value = "select sum(if(p.division ='OBTAIN', p.score, -p.score)) from Point p " +
            "where p.user_id = :userId and p.deleted = false", nativeQuery = true)
    long findPointSum(@Param("userId") Long userId);




}
