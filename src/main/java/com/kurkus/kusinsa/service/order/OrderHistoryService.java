package com.kurkus.kusinsa.service.order;


import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {


    private final ProductRepository productRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    // produt에서 getById에서 앞에서 valid체크가일어났으니까 이때 lock을 걸어야하나
    @Transactional
    public void save(Order order, User user, OrderProductRequest validProduct){


    }
    // decrease에서 lock을 걸어야하는것

}
