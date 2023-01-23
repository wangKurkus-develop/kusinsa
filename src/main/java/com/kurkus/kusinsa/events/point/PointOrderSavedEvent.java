package com.kurkus.kusinsa.events.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class PointOrderSavedEvent {
    private Long userId;
    private long obtainPoint;
    private long usedPoint;
    private Long orderId;

}
