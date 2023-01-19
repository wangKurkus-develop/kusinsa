package com.kurkus.kusinsa.service.order;

import java.util.ArrayList;
import java.util.List;

import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.ProductType;
import com.kurkus.kusinsa.repository.OrderRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
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
    private final OrderHistoryService orderHistoryService;
    private final ProductRepository productRepository;

    /**
     * v1 : 동시성 처리
     * v2 : 포인트 지급
     * v3 : 알림서비스
     */
    // 하나의 트랜잭션으로 묶어야한다 왜냐하면 주문은 생성이됬는데 주문기록이 안남으면 안되기때문이다.
    @Transactional
    public void save(Long userId, OrderCreateRequest request) {
        User user = userRepository.getById(userId);
        Order order = request.toOrder(user);
        Order saveOrder = orderRepository.save(order);
        //
        List<OrderProductRequest> list = request.getOrderProductRequestList();
        for(int i=0; i< list.size(); i++){
            decrease(list.get(i).getProductId(), list.get(i).getQuantity()); // 실패시?? 롤백이되나 테스트해봐야함
            orderHistoryService.save(saveOrder, user, list.get(i)); // 이벤트로하기 기록하는게 실수가나면 재시도해야지
        }
        log.info("주문완료");
    }


    private void validStock(Product product, int quantity){
        if(product.getStock() - quantity < 0){
            throw new RuntimeException(product.getName()+"수량이 모자릅니다.");
        }
    }

    private void validStatus(Product product){
        if(product.getStatus() != ProductType.SALE){
            throw new RuntimeException(product.getName()+"판매중인 상품이 아닙니다");
        }
    }


    /**
     * Transactional new로 한다면
     * decrease하나의 메소드만 lock이 걸리는것이고
     * decrease
     */
    @Transactional
    public void decrease(Long productId, int quantity){
        Product product = productRepository.findByIdWithPessimisticLock(productId);
        validStock(product, quantity);
        validStatus(product);
        product.decrease(quantity);
    }
}
