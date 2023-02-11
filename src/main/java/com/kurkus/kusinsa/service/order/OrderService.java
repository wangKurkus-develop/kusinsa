package com.kurkus.kusinsa.service.order;

import java.util.List;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;

import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.order.Order;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.enums.ProductType;
import com.kurkus.kusinsa.events.order.OrderHistorySavedEvent;
import com.kurkus.kusinsa.events.point.PointOrderSavedEvent;
import com.kurkus.kusinsa.exception.order.OrderException;
import com.kurkus.kusinsa.repository.OrderRepository;
import com.kurkus.kusinsa.repository.product.ProductRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    /**
     * v1 : 동시성 처리
     * v2 : 포인트 지급
     * v3 : 알림서비스
     * v4 : 분산락을 활용할것 고려 포인트지급, 알림서비스 카프카로 빼기
     */
    @Transactional
    public void save(Long userId, OrderCreateRequest request) {
        List<OrderProductRequest> list = request.getOrderProductRequestList();
        for(int i=0; i< list.size(); i++){
            decrease(list.get(i).getProductId(), list.get(i).getQuantity());
        }
        Order saveOrder = orderRepository.save(request.toOrder(userRepository.getById(userId)));
        publisher.publishEvent(new PointOrderSavedEvent(userId, request.getTotalObtainPoint(),
                request.getTotalUsedPoint(),saveOrder.getId()));
        publisher.publishEvent(new OrderHistorySavedEvent(saveOrder, request.getOrderProductRequestList()));
    }

    @Transactional
    public void decrease(Long productId, int quantity)  {
        Product product = productRepository.findByIdWithPessimisticLock(productId);
        validStock(product, quantity);
        validStatus(product);
        product.decrease(quantity);
        productRepository.flush(); // 한개씩 진행하고싶기때문에 flush를 통해 쓰기지연을 안사용 db에빠르게 업데이트해서 다른사람이 주문못하게만듬
    }

    private void validStock(Product product, int quantity){
        if(product.getStock() - quantity < 0){
            throw new OrderException(product.getName()+" "+ NOT_ENOUGH_QUANTITY, HttpStatus.BAD_REQUEST);
        }
    }

    private void validStatus(Product product){
        if(product.getStatus() != ProductType.SALE){
            throw new OrderException(product.getName()+" "+ NOT_SALE, HttpStatus.BAD_REQUEST);
        }
    }
}
