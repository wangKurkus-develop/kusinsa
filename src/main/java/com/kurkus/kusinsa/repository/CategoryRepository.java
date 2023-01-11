package com.kurkus.kusinsa.repository;

import com.kurkus.kusinsa.entity.Category;
import com.kurkus.kusinsa.exception.category.CategoryNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {


//    이게 존재안하면 아래는 어차피 예외 터지겠네 리펙토링할때 참고하자
//    @Query("select c from Category c where c.id = :id and c.bool = 1")
//    Optional<Category> get(Long id);


    default Category getById(Long id){
        return findById(id).orElseThrow(() -> new CategoryNotFoundException());
    }

    // 존재안하거나 아니면 false이면 삭제된상태니까
}
