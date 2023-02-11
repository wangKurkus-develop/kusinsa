package com.kurkus.kusinsa.events.notification;


import lombok.Getter;

@Getter
public class GroupEvent {

    private Long productId;
    private String productName;

    public GroupEvent(Long productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
