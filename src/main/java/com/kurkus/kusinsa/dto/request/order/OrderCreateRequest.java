package com.kurkus.kusinsa.dto.request.order;

import java.util.List;

import com.kurkus.kusinsa.entity.Order;
import com.kurkus.kusinsa.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 이게 일괄주문시 한것이고 결제했다는거에 주문기록으로 남기면되는거고
// 이게 주문기록 테이블에 따로 들어가고  주문은 서비스 save를 여러번 아니다 이상태로 주문기록에 상품들이 들어가니까
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateRequest {

    // 선택한 product
    private List<OrderProductRequest> orderProductRequestList;
    // 총가격
    private Long orderPrice;
    // 총 사용포인트 (포인트는 상품에관련없이 사용가능)
    private long totalUsedPoint;
    // 총 적립 포인트
    private long totalObtainPoint;
    // 주소
    private String deliveryAddress;

    public Order toOrder(User user){
        return Order.builder()
                .user(user)
                .price(orderPrice)
                .deliveryAddress(deliveryAddress)
                .totalUsedPoint(totalUsedPoint)
                .totalObtainPoint(totalObtainPoint)
                .build();
    }



}
