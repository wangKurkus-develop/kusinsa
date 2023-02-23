package com.kurkus.kusinsa.repository.product;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.exception.product.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    default Product getById(Long id){
        return findById(id).orElseThrow(()-> new ProductNotFoundException());
    }

    @Query("select p from Product p join fetch p.brand join fetch p.category where p.id = :id")
    Optional<Product> findByIdWithAll(@Param("id") Long id);

    default Product getByIdWithAll(Long id){
        return findByIdWithAll(id).orElseThrow(() -> new ProductNotFoundException());
    }

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id and p.deleted=false ")
    Product findByIdWithPessimisticLock(@Param("id") Long id);


    // ORDER BY FIELD(id,2,3,1); QueryDSL로 변경
    @Query(value = "select p from Product p join fetch p.brand where p.id in :list")
    List<Product> findAllWithBrandByList(@Param("list") List<Long> list);


    @Query(value = "select p from Product p where p.id in :list")
    List<Product> findAllByList(@Param("list") List<Long> list);

}
