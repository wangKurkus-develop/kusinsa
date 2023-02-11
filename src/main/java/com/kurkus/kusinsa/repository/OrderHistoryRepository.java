package com.kurkus.kusinsa.repository;

import javax.persistence.LockModeType;

import com.kurkus.kusinsa.dto.response.orderhistory.OrderHistoryResponse;
import com.kurkus.kusinsa.entity.order.OrderHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {


    @Query(value = "select new com.kurkus.kusinsa.dto.response.orderhistory.OrderHistoryResponse(" +
            "h.id, p.id, p.thumbnailImagePath, p.name, b.id, b.name, " +
            "o.createdAt, o.id, h.price, h.quantity, h.orderStatus ,h.deliveryStatus) " +
            "from OrderHistory h " +
            "inner join Product p on h.product.id = p.id " +
            "inner join Order o on h.order.id = o.id " +
            "inner join Brand b on h.product.brand.id = b.id " +
            "where h.user.id = :userId order by h.createdAt desc",
            countQuery = "select count(h) from OrderHistory  h where h.user.id = :userId")
    Page<OrderHistoryResponse> findAllWithPageDto(@Param("userId") Long userId, Pageable pageable);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select h from OrderHistory h join fetch h.product join fetch h.user where h.id = :id")
    OrderHistory findByIdPessimisticLock(@Param("id") Long id);

    @Query(value = "select h from OrderHistory h join fetch h.user join fetch h.product where h.id = :id and h.deleted = false ")
    OrderHistory findByIdWithUserAndProduct(@Param("id") Long id);

}
