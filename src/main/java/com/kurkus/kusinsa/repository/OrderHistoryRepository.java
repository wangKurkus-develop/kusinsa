package com.kurkus.kusinsa.repository;

import javax.persistence.LockModeType;
import java.util.Optional;

import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.exception.order.OrderHistoryNotFoundException;
import com.kurkus.kusinsa.exception.user.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {


    @Query("select h from OrderHistory h join fetch h.order join fetch h.product join fetch h.product.brand " +
            "where h.user.id = :userId order by h.createdAt desc")
    Page<OrderHistory> findAllWithPage(@Param("userId") Long userId, Pageable page);

//    default User getById(Long id){
//        return findById(id).orElseThrow(()-> new UserNotFoundException());
//    }

    // fetch
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select h from OrderHistory h join fetch h.order where h.id = :id")
    Optional<OrderHistory> customFindById(@Param("id") Long id);

    default OrderHistory getById(Long id){
        return customFindById(id).orElseThrow(() -> new OrderHistoryNotFoundException());
    }
}
