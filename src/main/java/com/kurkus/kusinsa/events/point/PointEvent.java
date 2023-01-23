package com.kurkus.kusinsa.events.point;

import com.kurkus.kusinsa.enums.PointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointEvent {

    private Long userId;
    private String content;
    private long point;
    private PointType type;
}
