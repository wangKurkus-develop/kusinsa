package com.kurkus.kusinsa.events.notification;


import com.kurkus.kusinsa.enums.DeliveryStatus;
import lombok.Getter;

@Getter
public class DeliveryStatusEvent {

    private Long orderHistoryId;
    private DeliveryStatus status;

    public DeliveryStatusEvent(Long orderHistoryId, DeliveryStatus status) {
        this.orderHistoryId = orderHistoryId;
        this.status = status;
    }
}
