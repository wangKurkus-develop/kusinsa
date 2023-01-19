package com.kurkus.kusinsa.repository;

import com.kurkus.kusinsa.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

}
