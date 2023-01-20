package com.kurkus.kusinsa.service.order;


import java.util.List;
import java.util.stream.Collectors;

import static com.kurkus.kusinsa.utils.constants.ErrorMessages.*;
import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.HISTORY_SIZE;
import static com.kurkus.kusinsa.utils.constants.PageSizeConstants.POINT_SIZE;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.dto.response.orderhistory.OrderHistoryResponse;
import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.OrderHistory;
import com.kurkus.kusinsa.entity.Product;
import com.kurkus.kusinsa.entity.User;
import com.kurkus.kusinsa.enums.DeliveryStatus;
import com.kurkus.kusinsa.exception.order.OrderException;
import com.kurkus.kusinsa.repository.OrderHistoryRepository;
import com.kurkus.kusinsa.repository.ProductRepository;
import com.kurkus.kusinsa.utils.constants.ErrorMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryService {


    private final ProductRepository productRepository;
    private final OrderHistoryRepository historyRepository;

    @Transactional
    public void save(Order order, OrderProductRequest request){
        Product product = productRepository.getById(request.getProductId());
        OrderHistory orderHistory = request.toOrderHistory(order, product);
        historyRepository.save(orderHistory);
    }

    @Transactional(readOnly = true)
    public Page<OrderHistoryResponse> findAllWithPage(Long userId, int page) {
        if(page < 0){
            page = 0;
        }
        Page<OrderHistoryResponse> result = historyRepository.findAllWithPage(userId, PageRequest.of(page, HISTORY_SIZE));
        return result;
    }

    /**
     * 1. 모든 주문은 결제가이미된상태 라고 가정합니다
     * 2. 권한이 있어야 환불가능하다
     * 3. 주문 취소는 배송준비중일떄만 가능하다
     * 주문취소는 수량개수도 다시 돌려놓아야하고 SOLD OUT이면 상태를 변경시켜야함
     * 주문 취소는 Lock걸어야한다 주문취소할때 배송출발으로 바뀌면 안되기때문이다.
     * 이에따른 포인트도 회수해야함
     * 기록을 해야지 취소기록을 (이런거는 로그로해야하나보구나)
     */
    @Transactional
    public void cancel(Long userId, Long historyId) {
        OrderHistory history = historyRepository.findByIdPessimisticLock(historyId);
        if(history.getUser().getId() != userId){
            throw new OrderException(NOT_AUTHORITY, HttpStatus.FORBIDDEN);
        }
        if(history.getDeliveryStatus() != DeliveryStatus.DELIVERY_READY){
            throw new OrderException(FAIL_CANCEL_DELIVERY, HttpStatus.BAD_REQUEST);
        }

        history.refund();
        Product product = history.getProduct();
        product.increase(history.getQuantity());
    }

}
