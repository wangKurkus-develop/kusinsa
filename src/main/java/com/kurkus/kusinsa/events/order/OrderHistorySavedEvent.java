package com.kurkus.kusinsa.events.order;


import java.util.List;

import com.kurkus.kusinsa.dto.request.order.OrderProductRequest;
import com.kurkus.kusinsa.entity.order.Order;
import lombok.Getter;

@Getter
public class OrderHistorySavedEvent {

    private Order order;
    private List<OrderProductRequest> orderProductRequestList;

    public OrderHistorySavedEvent(Order order, List<OrderProductRequest> orderProductRequestList){
        this.order = order;
        this.orderProductRequestList = orderProductRequestList;
    }

}
