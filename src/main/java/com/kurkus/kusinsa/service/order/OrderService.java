package com.kurkus.kusinsa.service;

import java.util.List;

import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.repository.OrderRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderHistory orderHistory;

    /**
     * 동시성 처리
     * 로그 처리
     */
    // 1차적 valid SALE인지, 수량가능한지 체크
    // 하나의 트랜잭션으로 묶어야한다 왜냐하면 주문은 생성이됬는데 주문기록이 안남으면 안되기때문이다.
    @Transactional
    public void save(Long userId, OrderCreateRequest request) {
        // 수량체크할때 락을걸어야겠네  
        validProduct(request.getOrderProductRequestList());
        User user = userRepository.getById(userId);
        Order order = request.toOrder(user);
        orderRepository.save(order);
        log.info("주문 생성완료");
        // 주문기록



        log.info("주문기록 생성완료");
    }

    private boolean validProduct(List<OrderProductRequest> productRequestList){
        // 1차 품절인지 체크


        // 2차 요청한 수량보다 존재하는지 체크
    }
}
