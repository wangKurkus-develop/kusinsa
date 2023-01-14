package com.kurkus.kusinsa.repository;

import com.kurkus.kusinsa.entity.Point;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.PointType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointRepository extends JpaRepository<Point, Long> {



    @Query(value = "select p  " +
            "from Point p inner join p.user u on p.user.id = u.id " +
            "where p.user.email = :userId and p.division = :division and p.deleted= false" +
            " order by p.createdAt desc",
    countQuery = "select count(p) from Point p inner join p.user u on p.user.id = u.id " +
            "where p.user.email = :userId and p.division = :division and p.deleted= false")
    Page<Point> findAllByDivision(@Param("userId") String userId, @Param("division") PointType division,
                                  Pageable pageable);

    @Query(value = "select p "+
            "from Point p inner join p.user u on p.user.id = u.id " +
            "where p.user.email = :userId and p.deleted = false order by p.createdAt desc",
    countQuery = "select count(p) from Point p inner join p.user u on p.user.id = u.id" +
            " where p.user.email = :userId and p.deleted = false ")
    Page<Point> findAll(@Param("userId") String userId, Pageable pageable);
}
