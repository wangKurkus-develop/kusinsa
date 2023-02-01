package com.kurkus.kusinsa.service.order;

import java.util.ArrayList;
import java.util.List;


import com.kurkus.kusinsa.dto.request.order.OrderCreateRequest;
import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.order.Order;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.ProductType;
import com.kurkus.kusinsa.repository.OrderRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Spy
    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    ApplicationEventPublisher publisher;

    @Test
    public void save() {
        // given
        Long productId = 5L;
        Long userId = 18L;

        List<OrderProductRequest> requestList = new ArrayList<>();
        OrderProductRequest orderProduct = OrderProductRequest.builder()
                .productId(productId)
                .obtainPoint(500)
                .quantity(2)
                .price(3000L)
                .build();
        requestList.add(orderProduct);

        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .orderPrice(10000L)
                .totalObtainPoint(500)
                .totalUsedPoint(5000)
                .orderProductRequestList(requestList)
                .deliveryAddress("배송지2")
                .build();

        given(productRepository.findByIdWithPessimisticLock(productId)).willReturn(Product.builder().
                stock(10).status(ProductType.SALE).build());
        given(userRepository.getById(anyLong())).willReturn(User.builder().id(userId).build());
        given(orderRepository.save(any())).willReturn(Order.builder().totalObtainPoint(10).totalUsedPoint(10).build());
        // when
        orderService.save(userId, orderCreateRequest);
        // then
        then(orderRepository).should(times(1)).save(any());
        then(orderService).should(times(1)).decrease(anyLong(), anyInt());
    }

    @Test
    public void decrease() {
        // given
        Long productId = 10L;
        int quantity = 10;
        given(productRepository.findByIdWithPessimisticLock(any())).
                willReturn(Product.builder().stock(20).status(ProductType.SALE).build());
        // when
        orderService.decrease(productId, quantity);
        // then
        then(productRepository).should(times(1)).flush();
    }



}