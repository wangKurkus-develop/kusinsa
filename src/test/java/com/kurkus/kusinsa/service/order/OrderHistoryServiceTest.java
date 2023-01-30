package com.kurkus.kusinsa.service.order;

import static org.junit.jupiter.api.Assertions.*;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.dto.response.orderhistory.OrderHistoryResponse;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHistoryServiceTest {

    @InjectMocks
    OrderHistoryService orderHistoryService;

    @Mock
    ProductRepository productRepository;

    @Mock
    OrderHistoryRepository historyRepository;

    @Mock
    ApplicationEventPublisher publisher;


    @Test
    void save() {
        // given
        Order order = Order.builder().id(100L).build();
        OrderProductRequest request = new OrderProductRequest(16L, 12, 10000L, 100L);
        given(productRepository.getById(any())).willReturn(Product.builder().build());
        // when
        orderHistoryService.save(order, request);
        // then
        then(productRepository).should(times(1)).getById(any());
        then(historyRepository).should(times(1)).save(any());
    }

    @Test
    void findAllWithPage() {
        // given
        given(historyRepository.findAllWithPageDto(any(), any())).willReturn(Page.empty());
        // when
        orderHistoryService.findAllWithPage(15L, -1);
        // then
        then(historyRepository).should(times(1)).findAllWithPageDto(any(), any());
    }

    @Test
    void cancel() {
        // given
        Long userId = 16L;
        Long historyId = 15L;
        OrderHistory orderHistory = OrderHistory.builder().deliveryStatus(DeliveryStatus.DELIVERY_READY)
                .product(Product.builder().build())
                .quantity(123).id(historyId).user(User.builder().id(userId).build()).build();
        given(historyRepository.findByIdPessimisticLock(historyId)).willReturn(orderHistory);
        // when
        orderHistoryService.cancel(userId, historyId);
        // then
        then(historyRepository).should(times(1)).findByIdPessimisticLock(any());
    }

    @Test
    void updateDeliveryStatus() {
        // given
        Long orderHistoryId = 15L;
        DeliveryStatus deliveryStatus = DeliveryStatus.DELIVERY;
        given(historyRepository.findByIdPessimisticLock(orderHistoryId)).willReturn(OrderHistory.builder().build());
        // when
        orderHistoryService.updateDeliveryStatus(orderHistoryId, deliveryStatus);;
        // then
        then(historyRepository).should(times(1)).findByIdPessimisticLock(any());
    }
}