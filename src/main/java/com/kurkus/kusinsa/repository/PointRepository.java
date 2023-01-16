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
            "where p.user.id = :userId and p.division = :division and p.deleted= false" +
            " order by p.createdAt desc",
    countQuery = "select count(p) from Point p inner join p.user u on p.user.id = u.id " +
            "where p.user.id = :userId and p.division = :division and p.deleted= false")
    Page<Point> findAllByDivision(@Param("userId") Long userId, @Param("division") PointType division,
                                  Pageable pageable);

    @Query(value = "select p "+
            "from Point p inner join p.user u on p.user.id = u.id " +
            "where p.user.id = :userId and p.deleted = false order by p.createdAt desc",
    countQuery = "select count(p) from Point p inner join p.user u on p.user.id = u.id" +
            " where p.user.id = :userId and p.deleted = false ")
    Page<Point> findAll(@Param("userId") Long userId, Pageable pageable);


    @Query(value = "select sum(if(p.division ='OBTAIN', p.score, -p.score)) from Point p " +
            "where p.user_id = :userId and p.deleted=false", nativeQuery = true)
    long findPointSum(@Param("userId") Long userId);


}
