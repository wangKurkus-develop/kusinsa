package com.kurkus.kusinsa.repository;

import com.kurkus.kusinsa.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
