package com.kurkus.kusinsa.repository;

import java.util.List;

import com.kurkus.kusinsa.entity.Brand;
import com.kurkus.kusinsa.exception.brand.BrandNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    boolean existsByName(String name);

    default Brand getById(Long id){
        return findById(id).orElseThrow(()-> new BrandNotFoundException());
    }



    List<Brand> findAllByOrderByNameAsc();
}
