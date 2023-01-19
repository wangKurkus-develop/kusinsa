package com.kurkus.kusinsa.service.order;


import java.util.List;
import java.util.stream.Collectors;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
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

    @Transactional
    public void save(Order order, OrderProductRequest request){
        Product product = productRepository.getById(request.getProductId());
        OrderHistory orderHistory = request.toOrderHistory(order, product);
        orderHistoryRepository.save(orderHistory);
    }

}
